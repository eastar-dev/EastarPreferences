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
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import javax.xml.xpath.*

//import javax.xml.parsers.DocumentBuilderFactory
//import javax.xml.transform.OutputKeys
//import javax.xml.transform.Transformer
//import javax.xml.transform.TransformerFactory
//import javax.xml.transform.dom.DOMSource
//import javax.xml.transform.stream.StreamResult

/**
 * Custom Kotlin Class Builder which returns file content string
 * This is for learning purpose only.
 * Use KotlinPoet for production app
 * KotlinPoet can be found at https://github.com/square/kotlinpoet
 */

internal fun generateManifest(kaptKotlinGeneratedDir: String) {
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

    manifests?.firstOrNull()?.parse()
    //manifests?.forEach {
    //    Log.w("$it")
    //    parse(it)
    //
    //    it.appendText("\n<!-- hello -->")
    //}

}

private fun File.parse() {
    val doc: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this)
    val documentElement = doc.documentElement
    documentElement.normalize()
    val applicationPackage = documentElement.getAttribute("package")
    Log.w(applicationPackage)

    val applicationNodes = documentElement.getElementsByTagName("application")
    if (applicationNodes.length > 0) {
        val applicationNode = applicationNodes.item(0)
        val nameAttribute = applicationNode.attributes.getNamedItem("android:name")
        val debuggableAttribute = applicationNode.attributes.getNamedItem("android:debuggable")
        Log.w("${applicationNode.toText()}")
    }

    val activityNodes = documentElement.getElementsByTagName("activity")
    val serviceNodes = documentElement.getElementsByTagName("service")
    val receiverNodes = documentElement.getElementsByTagName("receiver")
    val providerNodes = documentElement.getElementsByTagName("provider")
    val metaDataNodes = documentElement.getElementsByTagName("meta-data")
    val usesPermissionNodes = documentElement.getElementsByTagName("uses-permission")

}

fun Node.toText(omitXmlDeclaration: Boolean = true, prettyPrint: Boolean = true): String? {
    //requireNotNull(node) { "node is null." }
    return try { // Remove unwanted whitespaces
        val node = this
        node.normalize()
        val xpath: XPath = XPathFactory.newInstance().newXPath()
        val expr: XPathExpression = xpath.compile("//text()[normalize-space()='']")
        val nodeList: NodeList = expr.evaluate(node, XPathConstants.NODESET) as NodeList
        for (i in 0 until nodeList.length) {
            val nd: Node = nodeList.item(i)
            nd.parentNode.removeChild(nd)
        }
        // Create and setup transformer
        val transformer = TransformerFactory.newInstance().newTransformer()
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8")
        if (omitXmlDeclaration) {
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes")
        }
        if (prettyPrint) {
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4")
        }
        // Turn the node into a string
        val writer = StringWriter()
        transformer.transform(DOMSource(node), StreamResult(writer))
        writer.toString()
    } catch (e: TransformerException) {
        throw RuntimeException(e)
    } catch (e: XPathExpressionException) {
        throw RuntimeException(e)
    }
}

val provider
    get() = """        <provider
            android:name="$GENERATED_INITIALIZER_CLASS"
            android:authorities="dev.eastar.kapt.sharedpreferences.demo.preference"
            android:exported="false" />"""

private val contentTemplate = """"""
fun getContent() = contentTemplate
