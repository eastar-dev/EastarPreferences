package dev.eastar.pref.annotation.generator

import dev.eastar.pref.annotation.generator.AnnotationConst.Companion.GENERATED_CLASS_TAIL_FIX
import java.util.*
import javax.lang.model.element.Element

/**
 * Custom Kotlin Class Builder which returns file content string
 * This is for learning purpose only.
 * Use KotlinPoet for production app
 * KotlinPoet can be found at https://github.com/square/kotlinpoet
 */
@ExperimentalStdlibApi
class ClassBuilderPreferences(environment: Element) {
    private val contentTemplate = """
package ${environment.enclosingElement}
import android.content.SharedPreferences

object ${environment.simpleName}$GENERATED_CLASS_TAIL_FIX {
    lateinit var preferences: SharedPreferences
${environment.enclosedElements
            .map { it.asType().toString().substring(2) to it.simpleName.substring(3) }
            .mapNotNull { funcTemplate[it.first]?.format(it.second.camel, it.second, it.second, it.second.camel.capitalize(Locale.ENGLISH), it.second) }
            .joinToString("\n")}
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

    @JvmStatic fun get%s(defValue: String = "") = preferences.getString("%s", defValue)!!
""",
                "java.util.Set<java.lang.String>" to """
    @JvmStatic var %s: Set<String>
        get() = preferences.getStringSet("%s", emptySet())!!
        set(value) = preferences.edit().putStringSet("%s", value).apply()

    @JvmStatic fun get%s(defValue: Set<String> = emptySet()) = preferences.getStringSet("%s", defValue)!!
"""
        )
    }

    private val String.camel: String
        get() =
            when {
                contains('_') ->
                    split('_').joinToString("") { it.toLowerCase(Locale.ENGLISH).capitalize(Locale.ENGLISH) }.decapitalize(Locale.ENGLISH)
                filterNot { it.isDigit() || it.isUpperCase() }.count() <= 0 ->
                    toLowerCase(Locale.ENGLISH)
                else ->
                    decapitalize(Locale.ENGLISH)
            }
}


