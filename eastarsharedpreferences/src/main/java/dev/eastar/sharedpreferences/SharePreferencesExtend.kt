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

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

inline fun <reified T> IPref.get(defValue: T?): T? {
    val key = (this as? Enum<*>)?.name
    //Log.w("get", key, "defValue:$defValue", T::class)
    return if (preferences.all.containsKey(key))
        preferences.all[key] as T
    else
        defValue
}

inline fun <reified T> IPref.get(): T? {
    val key = (this as? Enum<*>)?.name
    //Log.w("get", key, "defValue:empty", T::class)

    if (preferences.all.containsKey(key))
        return preferences.all[key] as T

    return when (T::class) {
        Boolean::class -> false as T
        Int::class -> 0 as T
        Float::class -> 0F as T
        Long::class -> 0L as T
        String::class -> "" as T
        Set::class -> emptySet<String>() as T
        else -> null
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T> IPref.put(value: T?) {
    val key = (this as? Enum<*>)?.name
    //Log.e("put", key, value, T::class)
    when (T::class) {
        Boolean::class -> preferences.edit { putBoolean(key, value as Boolean) }
        Int::class -> preferences.edit { putInt(key, value as Int) }
        Float::class -> preferences.edit { putFloat(key, value as Float) }
        Long::class -> preferences.edit { putLong(key, value as Long) }
        String::class -> preferences.edit { putString(key, value as String) }
        Set::class -> preferences.edit { putStringSet(key, value as Set<String>) }
        else -> Unit
    }
}

fun IPref.contains(): Boolean = preferences.contains((this as Enum<*>).name)
fun IPref.remove() = preferences.edit { remove((this as Enum<*>).name) }
fun IPref.clear() = preferences.edit { clear() }
fun IPref.getAll(): MutableMap<String, *> = preferences.all
//TODO : Check it
fun IPref.registerOnSharedPreferenceChangeListener(callback: SharedPreferences.OnSharedPreferenceChangeListener) = preferences.registerOnSharedPreferenceChangeListener(callback)
fun IPref.unregisterOnSharedPreferenceChangeListener(callback: SharedPreferences.OnSharedPreferenceChangeListener) = preferences.unregisterOnSharedPreferenceChangeListener(callback)
fun IPref.registerOnSharedPreferenceChangeListener(callback: (SharedPreferences, String) -> Unit) = preferences.registerOnSharedPreferenceChangeListener(callback)
fun IPref.unregisterOnSharedPreferenceChangeListener(callback: (SharedPreferences, String) -> Unit) = preferences.unregisterOnSharedPreferenceChangeListener(callback)

interface IPref {
    val preferences: SharedPreferences
}

class PrefDelegate : IPref {

    override val preferences: SharedPreferences

    constructor(preferences: SharedPreferences) {
        this.preferences = preferences
    }

    constructor(context: Context) {
        this.preferences = context.getSharedPreferences(javaClass.name, Context.MODE_PRIVATE)
    }
}