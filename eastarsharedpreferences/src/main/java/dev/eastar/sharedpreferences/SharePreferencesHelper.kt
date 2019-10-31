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
@file:Suppress("unused")

package dev.eastar.sharedpreferences

import android.content.SharedPreferences
import androidx.core.content.edit
import java.lang.reflect.Modifier

object PX {
    fun getPreferences(key: Enum<*>): SharedPreferences = key.javaClass.fields
            .filter {
                it.type == SharedPreferences::class.java
                        && it.modifiers and Modifier.STATIC == Modifier.STATIC
                        && it.get(null) != null
            }.map {
                it.get(null) as SharedPreferences
            }.first()

//    @JvmStatic
//    operator fun <T : Any?> equals(value: T?): Boolean {
//        return false
//    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: Enum<*>, defValue: T): T {
        val preferences = getPreferences(key)
        return if (preferences.all.containsKey(key.name))
            preferences.all[key.name] as T
        else
            defValue
    }

    inline operator fun <reified T> get(key: Enum<*>): T {
        val preferences = getPreferences(key)

        if (preferences.all.containsKey(key.name))
            return preferences.all[key.name] as T

        return when (T::class) {
            Boolean::class -> false as T
            Int::class -> 0 as T
            Float::class -> 0F as T
            Long::class -> 0L as T
            String::class -> "" as T
            Set::class -> emptySet<String>() as T
            else -> throw ClassNotFoundException("Unsupport type ${T::class.java.name}")
        }
    }

    operator fun <T : Any> set(key: Enum<*>, value: T) {
        val preferences = getPreferences(key)
        val key = key.name
        when (value) {
            is Boolean -> preferences.edit { putBoolean(key, value as Boolean) }
            is Int -> preferences.edit { putInt(key, value as Int) }
            is Float -> preferences.edit { putFloat(key, value as Float) }
            is Long -> preferences.edit { putLong(key, value as Long) }
            is String -> preferences.edit { putString(key, value as String) }
            is Set<*> -> preferences.edit { putStringSet(key, value as Set<String>) }
            else -> throw ClassNotFoundException("Unsupport type ${value.javaClass.name}")
        }
    }
}


