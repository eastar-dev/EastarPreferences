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

import dev.eastar.pref.annotation.Pref
import dev.eastar.pref.annotation.util.Log
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.xml.parsers.DocumentBuilderFactory

@SupportedSourceVersion(SourceVersion.RELEASE_8) // to support Java 8
@SupportedOptions(KAPT_KOTLIN_GENERATED_OPTION_NAME)
public class AnnotationGenerator : AbstractProcessor() {
    private lateinit var kaptKotlinGeneratedDir: String

    override fun init(processingEnvironment: ProcessingEnvironment?) {
        super.init(processingEnvironment)
        Log.processingEnvironment = processingEnv
        kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]!!
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Pref::class.java.name)
    }

    override fun process(set: MutableSet<out TypeElement>?, roundEnvironment: RoundEnvironment?): Boolean {
        set?.firstOrNull() ?: return true
        Log.w("0===========================================================")
        Log.w(set.toString())
        //roundEnvironment
        //        ?.getElementsAnnotatedWith(Pref::class.java)
        //        ?.forEach { Log.environmentTree(it) }
        Log.w("1===========================================================")
        roundEnvironment
                ?.getElementsAnnotatedWith(Pref::class.java)
                ?.forEach {
                    generatePrefClass(it)
                }

        roundEnvironment
                ?.getElementsAnnotatedWith(Pref::class.java)
                ?.let {
                    generateInitializerClass(it)
                }

        generateManifest(kaptKotlinGeneratedDir)

        Log.w("2===========================================================")
        return true
    }


    private fun generateInitializerClass(roundEnvironment: Set<Element>) {
        Log.w("$GENERATED_INITIALIZER_CLASS")
        val file = File("$kaptKotlinGeneratedDir/${PACKAGE_NAME.replace('.', '/')}", "$GENERATED_INITIALIZER_CLASS.kt")
        file.parentFile.mkdirs()
        val fileContent = ClassBuilderInitializer(roundEnvironment).getContent()
        file.writeText(fileContent)
    }

    private fun generatePrefClass(roundEnvironment: Element) {
        Log.w(roundEnvironment.toString())
        val className = "$GENERATED_CLASS_PRE_FIX${roundEnvironment.simpleName}"
        val file = File("$kaptKotlinGeneratedDir/${roundEnvironment.enclosingElement.toString().replace('.', '/')}", "$className.kt")
        file.parentFile.mkdirs()
        val fileContent = ClassBuilderPreferences(roundEnvironment).getContent()
        file.writeText(fileContent)
    }
}

fun findAndroidManifestFiles(dir: File, list: MutableSet<File>) {
    val files = dir.listFiles()
    files ?: return

    dir.listFiles()?.forEach { f ->
        if (f.isDirectory)
            findAndroidManifestFiles(f, list)
        else if (f.name == "AndroidManifest.xml") {
            list.add(f)
        }
    }
}