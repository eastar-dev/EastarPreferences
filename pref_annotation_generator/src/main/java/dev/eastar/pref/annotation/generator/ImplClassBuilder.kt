package dev.eastar.pref.annotation.generator

import javax.lang.model.element.Element

/**
 * Custom Kotlin Class Builder which returns file content string
 * This is for learning purpose only.
 * Use KotlinPoet for production app
 * KotlinPoet can be found at https://github.com/square/kotlinpoet
 */
class ImplClassBuilder(className: String, packageName: String, environment: Element) {
    private val contentTemplate = """
package $packageName
import android.content.SharedPreferences

object $className : ${environment.simpleName}{
    lateinit var preferences: SharedPreferences
${environment.enclosedElements.map { it.asType().toString().substring(2) to it.simpleName.substring(3) }.map { funcTemplate[it.first]?.format(it.second, it.second, it.second, it.second, it.second) }.joinToString("\n")}
}
"""
    fun getContent() = contentTemplate
}

private val funcTemplate = mapOf(
        "boolean" to """
    override var %s: Boolean
        set(value) = preferences.edit().putBoolean("%s", value).apply()
        get() = preferences.getBoolean("%s", false)

    fun get%s(defValue: Boolean = false) = preferences.getBoolean("%s", defValue)
""",
        "int" to """
    override var %s: Int
        set(value) = preferences.edit().putInt("%s", value).apply()
        get() = preferences.getInt("%s", -1)

    fun get%s(defValue: Int = -1) = preferences.getInt("%s", defValue)
""",
        "float" to """
    override var %s: Float
        get() = preferences.getFloat("%s", -1F)
        set(value) = preferences.edit().putFloat("%s", value).apply()

    fun get%s(defValue: Float = -1F) = preferences.getFloat("%s", defValue)
""",
        "long" to """
    override var %s: Long
        get() = preferences.getLong("%s", -1L)
        set(value) = preferences.edit().putLong("%s", value).apply()

    fun get%s(defValue: Long = -1L) = preferences.getLong("%s", defValue)
""",
        "java.lang.String" to """
    override var %s: String
        get() = preferences.getString("%s", "")!!
        set(value) = preferences.edit().putString("%s", value).apply()

    fun get%s(defValue: String = "") = preferences.getString("%s", defValue)!!
""",
        "java.util.Set<java.lang.String>" to """
    override var %s: Set<String>
        get() = preferences.getStringSet("%s", emptySet())!!
        set(value) = preferences.edit().putStringSet("%s", value).apply()

    fun get%s(defValue: Set<String> = emptySet()) = preferences.getStringSet("%s", defValue)!!
"""
)
