/*
 * Copyright 2019 copyright eastar Jeong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.eastar.pref.annotation.generator

import com.google.auto.service.AutoService
import dev.eastar.pref.annotation.Pref
import dev.eastar.pref.annotation.generator.ClassBuilderPref.Companion.GENERATED_CLASS_PRE_FIX
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

@AutoService(Processor::class) // For registering the service
@SupportedSourceVersion(SourceVersion.RELEASE_8) // to support Java 8
@SupportedOptions(AnnotationGenerator.KAPT_KOTLIN_GENERATED_OPTION_NAME)
public class AnnotationGenerator : AbstractProcessor() {
    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    override fun init(processingEnvironment: ProcessingEnvironment?) {
        super.init(processingEnvironment)
        processingEnvironment?.let { Log.processingEnvironment = it }

    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Pref::class.java.name)
    }

    override fun process(set: MutableSet<out TypeElement>?, roundEnvironment: RoundEnvironment?): Boolean {
        Log.w("===========================================================")
        roundEnvironment
                ?.getElementsAnnotatedWith(Pref::class.java)
                ?.forEach { Log.environmentTree(it) }
        Log.w("===========================================================")

        roundEnvironment
                ?.getElementsAnnotatedWith(Pref::class.java)
                ?.forEach {
                    generateImplClass(it)
                }

        roundEnvironment
                ?.getElementsAnnotatedWith(Pref::class.java)
                ?.let {
                    generateInitializerClass(it)
                }
        return true
    }

    private fun generateInitializerClass(roundEnvironment: Set<Element>) {
        if (roundEnvironment.isEmpty())
            return
        val className = "${GENERATED_CLASS_PRE_FIX}Initializer"
        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        val file = File("$kaptKotlinGeneratedDir/dev/eastar/sharedpreferences", "$className.kt")
        file.parentFile.mkdirs()
        val fileContent = ClassBuilderInitializer(roundEnvironment).getContent()
        file.writeText(fileContent)
    }

    private fun generateImplClass(roundEnvironment: Element) {
        val className = "$GENERATED_CLASS_PRE_FIX${roundEnvironment.simpleName}"
        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        val file = File("$kaptKotlinGeneratedDir/${roundEnvironment.enclosingElement.toString().replace('.', '/')}", "$className.kt")
        file.parentFile.mkdirs()
        val fileContent = ClassBuilderPref(roundEnvironment).getContent()
        file.writeText(fileContent)
    }
}
