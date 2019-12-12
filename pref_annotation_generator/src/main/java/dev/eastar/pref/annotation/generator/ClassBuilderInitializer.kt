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

import dev.eastar.pref.annotation.Pref
import javax.lang.model.element.Element

/**
 * Custom Kotlin Class Builder which returns file content string
 * This is for learning purpose only.
 * Use KotlinPoet for production app
 * KotlinPoet can be found at https://github.com/square/kotlinpoet
 */
class ClassBuilderInitializer(environments: Set<Element>) {

    private val preferences =
            environments.joinToString("\n") {
                val ann = it.getAnnotation(Pref::class.java)
                """        ${it.enclosingElement}.$GENERATED_CLASS_PRE_FIX${it.simpleName}.preferences = """ + when {
                    ann.defaultSharedPreferences ->
                        """androidx.preference.PreferenceManager.getDefaultSharedPreferences(context!!)"""
                    ann.value.isNotBlank() ->
                        """context?.getSharedPreferences("${ann.value}", Context.MODE_PRIVATE)!!"""
                    else ->
                        """context?.getSharedPreferences("$it", Context.MODE_PRIVATE)!!"""
                }
            }

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
package $PACKAGE_NAME

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri

class $GENERATED_INITIALIZER_CLASS : android.content.ContentProvider() {
    override fun onCreate(): Boolean {
$preferences
        return true
    }

    override fun getType(uri: Uri): String? = null
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? = null
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int = 0
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0
}
"""

    val provider get() = """        <provider
            android:name="$GENERATED_INITIALIZER_CLASS"
            android:authorities="dev.eastar.kapt.sharedpreferences.demo.preference"
            android:exported="false" />"""

    fun getContent() = contentTemplate
}
