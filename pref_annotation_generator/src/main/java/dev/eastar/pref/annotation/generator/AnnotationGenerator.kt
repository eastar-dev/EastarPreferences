/*
 * Copyright 2019 eastar Jeong
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

import dev.eastar.pref.annotation.Pref
import dev.eastar.pref.annotation.generator.AnnotationConst.Companion.CLASS_TAIL
import dev.eastar.pref.annotation.generator.AnnotationConst.Companion.GENERATED_INITIALIZER_CLASS
import dev.eastar.pref.annotation.generator.AnnotationConst.Companion.KAPT_KOTLIN_GENERATED_OPTION_NAME
import dev.eastar.pref.annotation.generator.AnnotationConst.Companion.PACKAGE_NAME
import dev.eastar.pref.annotation.util.Log
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

@SupportedSourceVersion(SourceVersion.RELEASE_8) // to support Java 8
@SupportedOptions(KAPT_KOTLIN_GENERATED_OPTION_NAME)
public class AnnotationGenerator : AbstractProcessor() {
    private lateinit var kaptKotlinGeneratedDir: String

    override fun init(processingEnvironment: ProcessingEnvironment?) {
        super.init(processingEnvironment)
        Log.processingEnvironment = processingEnv
        kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]!!
        generateInitializerClass(emptySet())
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Pref::class.java.name)
    }

    override fun process(set: MutableSet<out TypeElement>?, roundEnvironment: RoundEnvironment?): Boolean {
        set?.firstOrNull() ?: return true
        //Log.w("0===========================================================")
        roundEnvironment
                ?.getElementsAnnotatedWith(Pref::class.java)
                ?.filterNot { it.simpleName.endsWith(CLASS_TAIL) }
                ?.forEach {
                    Log.w("${it.simpleName} !!@Pref annotation must suffix $CLASS_TAIL")
                }

        roundEnvironment
                ?.getElementsAnnotatedWith(Pref::class.java)
                ?.filter { it.simpleName.endsWith(CLASS_TAIL) }
                ?.forEach {
                    generatePrefClass(it)
                }
        //Log.w("1===========================================================")
        roundEnvironment
                ?.getElementsAnnotatedWith(Pref::class.java)
                ?.filter { it.simpleName.endsWith(CLASS_TAIL) }
                ?.toSet()
                ?.let {
                    generateInitializerClass(it)
                }
        //Log.w("2===========================================================")
        return true
    }

    private fun generateInitializerClass(roundEnvironment: Set<Element>) {
        val file = File("$kaptKotlinGeneratedDir/${PACKAGE_NAME.replace('.', '/')}", "$GENERATED_INITIALIZER_CLASS.kt")
        file.parentFile.mkdirs()
        val fileContent = ClassBuilderInitializer(roundEnvironment).getContent()
        file.writeText(fileContent)
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun generatePrefClass(roundEnvironment: Element) {
        val file = File("$kaptKotlinGeneratedDir/${roundEnvironment.enclosingElement.toString().replace('.', '/')}", "${roundEnvironment.simpleName.removeSuffix(CLASS_TAIL)}.kt")
        file.parentFile.mkdirs()
        val fileContent = ClassBuilderPreferences(roundEnvironment).getContent()
        file.writeText(fileContent)
    }
}
