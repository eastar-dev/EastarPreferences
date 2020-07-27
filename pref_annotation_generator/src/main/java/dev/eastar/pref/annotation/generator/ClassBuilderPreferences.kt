package dev.eastar.pref.annotation.generator

import dev.eastar.pref.annotation.util.Log
import java.util.*
import javax.lang.model.element.Element
import javax.lang.model.element.VariableElement

/**
 * Custom Kotlin Class Builder which returns file content string
 * This is for learning purpose only.
 * Use KotlinPoet for production app
 * KotlinPoet can be found at https://github.com/square/kotlinpoet
 */
class ClassBuilderPreferences(element: Element) {
    private var keys: List<List<String>>

    init {
        Log.w("Generate Pref Class : [${element.simpleName.removeSuffix(AnnotationConst.CLASS_TAIL)}]")
        keys = element.enclosedElements
            .filter { it.kind.isField }
            .filterNot { it.simpleName.toString() == "Companion" }
            .map {
                listOf(
                    it.asType().toString(),
                    it.simpleName.toString(),
                    "${(it as? VariableElement)?.constantValue}"
                )
            }
        keys.forEach { Log.w(it.toString()) }
    }

    private val contentTemplate = """
package ${element.enclosingElement}
import android.content.SharedPreferences

object ${element.simpleName.removeSuffix(AnnotationConst.CLASS_TAIL)} {
    lateinit var preferences: SharedPreferences
${
        keys.mapNotNull {
            funcTemplate[it[0]]?.format(
                it[1].camel,
                it[1],
                it[1],
                it[1].camel.capitalize(Locale.ENGLISH),
                it[1]
            )
        }
            .joinToString("\n")
    }
}
"""

    fun getContent() = contentTemplate

    companion object {

        private val funcTemplate = mapOf(
            "boolean" to """
    @JvmStatic var %s: Boolean
        set(value) = preferences.edit().putBoolean("%s", value).apply()
        get() = preferences.getBoolean("%s", false)
    @JvmStatic fun get%s(defValue: Boolean = false) = preferences.getBoolean("%s", defValue)
""",
            "int" to """
    @JvmStatic var %s: Int
        set(value) = preferences.edit().putInt("%s", value).apply()
        get() = preferences.getInt("%s", -1)
    @JvmStatic fun get%s(defValue: Int = -1) = preferences.getInt("%s", defValue)
""",
            "float" to """
    @JvmStatic var %s: Float
        get() = preferences.getFloat("%s", -1F)
        set(value) = preferences.edit().putFloat("%s", value).apply()

    @JvmStatic fun get%s(defValue: Float = -1F) = preferences.getFloat("%s", defValue)
""",
            "long" to """
    @JvmStatic var %s: Long
        get() = preferences.getLong("%s", -1L)
        set(value) = preferences.edit().putLong("%s", value).apply()

    @JvmStatic fun get%s(defValue: Long = -1L) = preferences.getLong("%s", defValue)
""",
            "java.lang.String" to """
    @JvmStatic var %s: String
        get() = preferences.getString("%s", "")!!
        set(value) = preferences.edit().putString("%s", value).apply()

    @JvmStatic fun get%s(defValue: String? = "") = preferences.getString("%s", defValue)
""",
            "java.util.Set<java.lang.String>" to """
    @JvmStatic var %s: Set<String>
        get() = preferences.getStringSet("%s", emptySet())!!
        set(value) = preferences.edit().putStringSet("%s", value).apply()

    @JvmStatic fun get%s(defValue: Set<String>? = emptySet()) = preferences.getStringSet("%s", defValue)
"""
        )
    }

    private val String.camel: String
        get() =
            when {
                contains('_') ->
                    split('_').joinToString("") {
                        it.toLowerCase(Locale.ENGLISH).capitalize(Locale.ENGLISH)
                    }.decapitalize(Locale.ENGLISH)
                filterNot { it.isDigit() || it.isUpperCase() }.count() <= 0 ->
                    toLowerCase(Locale.ENGLISH)
                else ->
                    decapitalize(Locale.ENGLISH)
            }
}


