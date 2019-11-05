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
import dev.eastar.pref.annotation.PrefAnnotation
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@AutoService(Processor::class) // For registering the service
@SupportedSourceVersion(SourceVersion.RELEASE_8) // to support Java 8
@SupportedOptions(AnnotationGenerator.KAPT_KOTLIN_GENERATED_OPTION_NAME)
public class AnnotationGenerator : AbstractProcessor() {
    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
        const val GENERATED_CLASS_TAIL_FIX = "Pref"
    }

    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(PrefAnnotation::class.java.name)
    }

    override fun process(set: MutableSet<out TypeElement>?, roundEnvironment: RoundEnvironment?): Boolean {
        roundEnvironment?.getElementsAnnotatedWith(PrefAnnotation::class.java)
                ?.forEach {
                    val className = it.simpleName.toString()
                    val packageName = processingEnv.elementUtils.getPackageOf(it).toString()

                    generateClass(packageName, className)
                }
        return true
    }

    private fun generateClass(packageName: String, className: String) {
        val fileName = "$className$GENERATED_CLASS_TAIL_FIX"
        val fileContent = KotlinClassBuilder(fileName, packageName).getContent()

        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]

        processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "===========================================================")
        processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, fileContent)
        processingEnv.messager.printMessage(Diagnostic.Kind.WARNING, "===========================================================")


        val file = File(kaptKotlinGeneratedDir, "$fileName.kt")

        file.writeText(fileContent)
    }
}
