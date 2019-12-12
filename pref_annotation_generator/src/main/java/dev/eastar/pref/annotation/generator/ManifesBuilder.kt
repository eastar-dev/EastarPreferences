package dev.eastar.pref.annotation.generator

import dev.eastar.pref.annotation.util.Log
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory

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

val provider
    get() = """        <provider
            android:name="$GENERATED_INITIALIZER_CLASS"
            android:authorities="dev.eastar.kapt.sharedpreferences.demo.preference"
            android:exported="false" />"""

private val contentTemplate = """"""
fun getContent() = contentTemplate
