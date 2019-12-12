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

import java.util.*
import javax.lang.model.element.Element

/**
 * Custom Kotlin Class Builder which returns file content string
 * This is for learning purpose only.
 * Use KotlinPoet for production app
 * KotlinPoet can be found at https://github.com/square/kotlinpoet
 */
class ClassBuilderPreferences(environment: Element) {
    private val contentTemplate = """
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
package ${environment.enclosingElement}
import android.content.SharedPreferences

object $GENERATED_CLASS_PRE_FIX${environment.simpleName}{
    lateinit var preferences: SharedPreferences
${environment.enclosedElements
            .map { it.asType().toString().substring(2) to it.simpleName.substring(3) }
            .mapNotNull { funcTemplate[it.first]?.format(it.second.decapitalize(), it.second.decapitalize(), it.second.decapitalize(), it.second.capitalize(), it.second.decapitalize()) }
            .joinToString("\n")}
}
"""

    fun getContent() = contentTemplate

    companion object {

        private val funcTemplate = mapOf(
                "boolean" to """
    var %s: Boolean
        set(value) = preferences.edit().putBoolean("%s", value).apply()
        get() = preferences.getBoolean("%s", false)

    fun get%s(defValue: Boolean = false) = preferences.getBoolean("%s", defValue)
""",
                "int" to """
    var %s: Int
        set(value) = preferences.edit().putInt("%s", value).apply()
        get() = preferences.getInt("%s", -1)

    fun get%s(defValue: Int = -1) = preferences.getInt("%s", defValue)
""",
                "float" to """
    var %s: Float
        get() = preferences.getFloat("%s", -1F)
        set(value) = preferences.edit().putFloat("%s", value).apply()

    fun get%s(defValue: Float = -1F) = preferences.getFloat("%s", defValue)
""",
                "long" to """
    var %s: Long
        get() = preferences.getLong("%s", -1L)
        set(value) = preferences.edit().putLong("%s", value).apply()

    fun get%s(defValue: Long = -1L) = preferences.getLong("%s", defValue)
""",
                "java.lang.String" to """
    var %s: String
        get() = preferences.getString("%s", "")!!
        set(value) = preferences.edit().putString("%s", value).apply()

    fun get%s(defValue: String = "") = preferences.getString("%s", defValue)!!
""",
                "java.util.Set<java.lang.String>" to """
    var %s: Set<String>
        get() = preferences.getStringSet("%s", emptySet())!!
        set(value) = preferences.edit().putStringSet("%s", value).apply()

    fun get%s(defValue: Set<String> = emptySet()) = preferences.getStringSet("%s", defValue)!!
"""
        )
    }
}


