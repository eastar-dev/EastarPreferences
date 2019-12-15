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

import dev.eastar.pref.annotation.util.Log
import org.w3c.dom.Document
import java.io.File
import java.io.FileWriter
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class ManifestBuilder(private val kaptKotlinGeneratedDir: String) {
    init {
        process()
    }

    data class ManifestData(val file: File, val doc: Document)

    private fun process() {
        val manifestFiles = getManifestFiles()
        manifestFiles ?: return
        manifestFiles.map {
            addProviderNode(it)
        }.forEach {
            Log.w(it.doc.toText())
            it.doc.toFile(it.file)
        }
    }

    private fun getManifestFiles() = runCatching {
        var dir = File(kaptKotlinGeneratedDir)
        while (dir.name != "build") dir = dir.parentFile
        Log.w(dir.toString())
        Log.w("---------------------------------------------")
        val manifests = mutableSetOf<File>()
        findAndroidManifestFiles(dir, manifests)
        manifests
    }.getOrNull()

    private fun findAndroidManifestFiles(dir: File, list: MutableSet<File>) {
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

    private fun addProviderNode(manifestFile: File): ManifestData {
        val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(manifestFile)
        val documentElement = doc.documentElement
        val applicationPackage = documentElement.getAttribute("package")

        val applicationNodes = doc.documentElement.getElementsByTagName("application")
        val applicationNode = if (applicationNodes.length > 0)
            applicationNodes.item(0)
        else
            doc.documentElement.appendChild(doc.createElement("application"))

        val provider = doc.createElement("provider")
        provider.setAttribute("android:name", "$PACKAGE_NAME$GENERATED_INITIALIZER_CLASS")
        provider.setAttribute("android:authorities", "$applicationPackage.preference")
        provider.setAttribute("android:exported", "false")
        applicationNode.appendChild(provider)
        return ManifestData(manifestFile, doc)
    }

    fun Document.toText(): String = runCatching {
        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.METHOD, "xml")
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8")
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no")
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        // Turn the node into a string
        val writer = StringWriter()
        transformer.transform(DOMSource(this), StreamResult(writer))
        writer.toString()
    }.getOrDefault("")

    fun Document.toFile(file: File): String = runCatching {
        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.METHOD, "xml")
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8")
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no")
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")
        // Turn the node into a string
        val writer = FileWriter(file)
        transformer.transform(DOMSource(this), StreamResult(writer))
        writer.toString()
    }.getOrDefault("")

}
