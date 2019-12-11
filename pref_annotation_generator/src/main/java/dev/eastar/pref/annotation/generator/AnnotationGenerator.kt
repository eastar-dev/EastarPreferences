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
import dev.eastar.pref.annotation.generator.ClassBuilderInitializer.Companion.PACKAGE_NAME
import dev.eastar.pref.annotation.generator.ClassBuilderPref.Companion.GENERATED_CLASS_PRE_FIX
import java.io.File
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

@SupportedSourceVersion(SourceVersion.RELEASE_8) // to support Java 8
@SupportedOptions(AnnotationGenerator.KAPT_KOTLIN_GENERATED_OPTION_NAME)
public class AnnotationGenerator : AbstractProcessor() {
    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    override fun init(processingEnvironment: ProcessingEnvironment?) {
        super.init(processingEnvironment)
        Log.processingEnvironment = processingEnv
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
        Log.w("0===========================================================")
        Log.w(set.toString())
        //roundEnvironment
        //        ?.getElementsAnnotatedWith(Pref::class.java)
        //        ?.forEach { Log.environmentTree(it) }
        manifest()
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

        Log.w("2===========================================================")
        return true
    }

    private fun manifest() {
        val manifests = runCatching {
            Log.w("AndroidManifest.xml >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
            val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
            var dir = File(kaptKotlinGeneratedDir)
            while (dir.name != "build") dir = dir.parentFile
            Log.w(dir.toString())
            Log.w("---------------------------------------------")
            val manifests = mutableSetOf<File>()
            findAndroidManifestFiles(dir, manifests)
            manifests
        }.onFailure {
            Log.w(it.message ?: it.javaClass.name)
            it.stackTrace.forEach { Log.w(it.toString()) }
        }.getOrNull()
        manifests?.forEach {
            Log.w("$it")
            it.
            it.appendText("\n<!-- hello -->")
        }
    }

    val provider = """        <provider
            android:name="dev.eastar.sharedpreferences.PrefInitializer"
            android:authorities="dev.eastar.kapt.sharedpreferences.demo.preference"
            android:exported="false" />"""

    private fun generatePrefClass(roundEnvironment: Element) {
        Log.w(roundEnvironment.toString())

        val className = "$GENERATED_CLASS_PRE_FIX${roundEnvironment.simpleName}"
        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        val file = File("$kaptKotlinGeneratedDir/${roundEnvironment.enclosingElement.toString().replace('.', '/')}", "$className.kt")
        file.parentFile.mkdirs()
        val fileContent = ClassBuilderPref(roundEnvironment).getContent()
        file.writeText(fileContent)
    }

    private fun generateInitializerClass(roundEnvironment: Set<Element>) {
        Log.w("PrefInitializer")

        val className = "PrefInitializer"
        val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        val file = File("$kaptKotlinGeneratedDir/${PACKAGE_NAME.replace('.', '/')}", "$className.kt")
        file.parentFile.mkdirs()
        val fileContent = ClassBuilderInitializer(roundEnvironment).getContent()
        file.writeText(fileContent)

        //val fileAndroidManifest = File(File(kaptKotlinGeneratedDir), "AndroidManifest.xml")
        //Log.w(fileAndroidManifest.parentFile.toString())
        //val result = fileAndroidManifest.parentFile.mkdirs()
        //Log.w(result.toString())
        //Log.w(ClassBuilderInitializer.androidManifest)
        //fileAndroidManifest.writeText(ClassBuilderInitializer.androidManifest)
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