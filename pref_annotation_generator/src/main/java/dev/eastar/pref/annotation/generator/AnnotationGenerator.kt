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
@SupportedOptions(AnnotationGenerator.KAPT_KOTLIN_GENERATED_OPTION_NAME)
public class AnnotationGenerator : AbstractProcessor() {
    companion object {
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    private lateinit var kaptKotlinGeneratedDir: String

    override fun init(processingEnvironment: ProcessingEnvironment?) {
        super.init(processingEnvironment)
        Log.processingEnvironment = processingEnv
        kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]!!
        processingEnv.options


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
            parse(it)

            it.appendText("\n<!-- hello -->")
        }

    }

    private fun parse(androidManifestFile: File) {
        val doc: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(androidManifestFile)
        val documentElement = doc.documentElement
        documentElement.normalize()
        val applicationPackage = documentElement.getAttribute("package")
        var minSdkVersion = -1
        var maxSdkVersion = -1
        var targetSdkVersion = -1
        val sdkNodes = documentElement.getElementsByTagName("uses-sdk")
        if (sdkNodes.length > 0) {
            val sdkNode = sdkNodes.item(0)
            minSdkVersion = extractAttributeIntValue(sdkNode, "android:minSdkVersion", -1)
            maxSdkVersion = extractAttributeIntValue(sdkNode, "android:maxSdkVersion", -1)
            targetSdkVersion = extractAttributeIntValue(sdkNode, "android:targetSdkVersion", -1)
        }

        val applicationNodes = documentElement.getElementsByTagName("application")
        var applicationClassQualifiedName: String? = null
        var applicationDebuggableMode = false
        if (applicationNodes.length > 0) {
            val applicationNode = applicationNodes.item(0)
            val nameAttribute = applicationNode.attributes.getNamedItem("android:name")
            val debuggableAttribute = applicationNode.attributes.getNamedItem("android:debuggable")
        }
        val activityNodes = documentElement.getElementsByTagName("activity")

        val serviceNodes = documentElement.getElementsByTagName("service")

        val receiverNodes = documentElement.getElementsByTagName("receiver")

        val providerNodes = documentElement.getElementsByTagName("provider")

        val metaDataNodes = documentElement.getElementsByTagName("meta-data")
        val usesPermissionNodes = documentElement.getElementsByTagName("uses-permission")
        val permissionQualifiedNames: MutableList<String> = ArrayList()
    }

    private fun extractAttributeIntValue(node: Node, attribute: String, defaultValue: Int): Int {
        try {
            val attributes = node.attributes
            if (attributes.length > 0) {
                val attributeNode = attributes.getNamedItem(attribute)
                if (attributeNode != null) {
                    return attributeNode.nodeValue.toInt()
                }
            }
        } catch (ignored: NumberFormatException) { // we assume the manifest is well-formed
        }
        return defaultValue
    }

    private fun extractComponentNames(applicationPackage: String, componentNodes: NodeList): List<String>? {
        val componentQualifiedNames: MutableList<String> = ArrayList()
        for (i in 0 until componentNodes.length) {
            val activityNode = componentNodes.item(i)
            val nameAttribute = activityNode.attributes.getNamedItem("android:name")
        }
        return componentQualifiedNames
    }


    private fun generateInitializerClass(roundEnvironment: Set<Element>) {
        Log.w("PrefInitializer")

        val className = "PrefInitializer"
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