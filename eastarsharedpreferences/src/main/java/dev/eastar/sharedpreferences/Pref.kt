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
package dev.eastar.sharedpreferences

import android.content.ContentProvider
import android.content.ContentValues
import android.content.SharedPreferences
import android.database.Cursor
import android.net.Uri
import androidx.preference.PreferenceManager
import dev.eastar.pref.annotation.PrefAnnotation

@PrefAnnotation
interface PrefInterface {
    var preferences: SharedPreferences
    //@formatter:off
    var TEST_BOOLEAN       : Boolean
    var TEST_INT           : Int
    var TEST_FLOAT         : Float
    var TEST_LONG          : Long
    var TEST_STRING        : String
    var TEST_SET           : Set<String>

    //@formatter:on
}

class PreferenceInitializer : ContentProvider() {
    override fun onCreate(): Boolean {
        Pref.preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return true
    }

    override fun getType(uri: Uri): String? = TODO("not implemented")
    override fun insert(uri: Uri, values: ContentValues?): Uri? = TODO("not implemented")
    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? = TODO("not implemented")
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int = TODO("not implemented")
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = TODO("not implemented")
}

object Pref : PrefInterface {
    override lateinit var preferences: SharedPreferences
    override var TEST_BOOLEAN: Boolean
        get() = preferences.getBoolean("TEST_EMPTY_BOOLEAN", false)
        set(value) = preferences.edit().putBoolean("TEST_BOOLEAN      ", value).apply()
    override var TEST_INT: Int
        get() = preferences.getInt("TEST_EMPTY_INT    ", -1)
        set(value) = preferences.edit().putInt("TEST_INT          ", value).apply()
    override var TEST_FLOAT: Float
        get() = preferences.getFloat("TEST_EMPTY_FLOAT  ", -1F)
        set(value) = preferences.edit().putFloat("TEST_FLOAT        ", value).apply()
    override var TEST_LONG: Long
        get() = preferences.getLong("TEST_EMPTY_LONG   ", -1L)
        set(value) = preferences.edit().putLong("TEST_LONG         ", value).apply()
    override var TEST_STRING: String
        get() = preferences.getString("TEST_EMPTY_STRING ", "")!!
        set(value) = preferences.edit().putString("TEST_STRING       ", value).apply()
    override var TEST_SET: Set<String>
        get() = preferences.getStringSet("TEST_EMPTY_SET    ", emptySet())!!
        set(value) = preferences.edit().putStringSet("TEST_SET          ", value).apply()

    //@formatter:off
    fun setTestBoolean     (value   : Boolean                 ) = preferences.edit().putBoolean    ("TEST_BOOLEAN      ",    value).apply()
    fun setTestInt         (value   : Int                     ) = preferences.edit().putInt        ("TEST_INT          ",    value).apply()
    fun setTestFloat       (value   : Float                   ) = preferences.edit().putFloat      ("TEST_FLOAT        ",    value).apply()
    fun setTestLong        (value   : Long                    ) = preferences.edit().putLong       ("TEST_LONG         ",    value).apply()
    fun setTestString      (value   : String                  ) = preferences.edit().putString     ("TEST_STRING       ",    value).apply()
    fun setTestSet         (value   : Set<String>             ) = preferences.edit().putStringSet  ("TEST_SET          ",    value).apply()


    @JvmOverloads fun getTestBoolean     (defValue: Boolean     = false     ) = preferences.getBoolean    ("TEST_BOOLEAN      ", defValue)
    @JvmOverloads fun getTestInt         (defValue: Int         = -1        ) = preferences.getInt        ("TEST_INT          ", defValue)
    @JvmOverloads fun getTestFloat       (defValue: Float       = -1F       ) = preferences.getFloat      ("TEST_FLOAT        ", defValue)
    @JvmOverloads fun getTestLong        (defValue: Long        = -1L       ) = preferences.getLong       ("TEST_LONG         ", defValue)
    @JvmOverloads fun getTestString      (defValue: String      = ""        ) = preferences.getString     ("TEST_STRING       ", defValue)!!
    @JvmOverloads fun getTestSet         (defValue: Set<String> = emptySet()) = preferences.getStringSet  ("TEST_SET          ", defValue)!!

    //@formatter:on
}

class Test {
    fun test() {
        val b = Pref.TEST_BOOLEAN

        Pref.TEST_BOOLEAN = true

        Pref.getTestBoolean(false)
        Pref.setTestBoolean(false)
    }

}