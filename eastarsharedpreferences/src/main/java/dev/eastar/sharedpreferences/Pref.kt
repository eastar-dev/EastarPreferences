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
import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.preference.PreferenceManager
import dev.eastar.pref.annotation.Pref

@Pref
interface TestSample {
    val TEST_EMPTY_BOOLEAN: Boolean
    val TEST_EMPTY_INT: Int
    val TEST_EMPTY_FLOAT: Float
    val TEST_EMPTY_LONG: Long
    val TEST_EMPTY_STRING: String
    val TEST_EMPTY_SET: Set<String>

    val TEST_BOOLEAN: Boolean
    val TEST_INT: Int
    val TEST_FLOAT: Float
    val TEST_LONG: Long
    val TEST_STRING: String
    val TEST_SET: Set<String>
}

@Pref
interface TestSample2 {
    val TEST_BOOLEAN: Boolean
    val TEST_INT: Int
    val TEST_FLOAT: Float
    val TEST_LONG: Long
    val TEST_STRING: String
    val TEST_SET: Set<String>
}



class Initializer2 : ContentProvider() {
    override fun onCreate(): Boolean {
        PrefTestSample.preferences = PreferenceManager.getDefaultSharedPreferences(context)
        PrefTestSample2.preferences =  context?.getSharedPreferences("NAME", Context.MODE_PRIVATE)!!
        return true
    }

    override fun getType(uri: Uri): String? = TODO("not implemented")
    override fun insert(uri: Uri, values: ContentValues?): Uri? = TODO("not implemented")
    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? = TODO("not implemented")
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int = TODO("not implemented")
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = TODO("not implemented")
}

class Test {
    fun test() {
        val b = PrefTestSample.tEST_BOOLEAN

        PrefTestSample.tEST_BOOLEAN = true

        //Pref.getTestBoolean(false)
        //Pref.setTestBoolean(false)
    }
}

