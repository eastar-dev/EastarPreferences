package dev.eastar.pref.annotation.generator

import javax.lang.model.element.Element

/**
 * Custom Kotlin Class Builder which returns file content string
 * This is for learning purpose only.
 * Use KotlinPoet for production app
 * KotlinPoet can be found at https://github.com/square/kotlinpoet
 */
class InitializerClassBuilder(environments: Set<Element>) {
    private val contentTemplate = """
package dev.eastar.sharedpreferences

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.preference.PreferenceManager

class Initializer : ContentProvider() {
    override fun onCreate(): Boolean {
${environments.map { it.simpleName }.joinToString("\n") {
        """        ${it}Impl.preferences =  context?.getSharedPreferences("$it", Context.MODE_PRIVATE)!!"""
    }}
        return true
    }

    override fun getType(uri: Uri): String? = null
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? = null
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int = 0
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0
}
"""

    fun getContent() = contentTemplate
}
