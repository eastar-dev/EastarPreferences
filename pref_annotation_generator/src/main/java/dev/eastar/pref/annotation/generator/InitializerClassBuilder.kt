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
        //TestSampleImpl.preferences = PreferenceManager.getDefaultSharedPreferences(context)
        //TestSample2Impl.preferences =  context?.getSharedPreferences("NAME", Context.MODE_PRIVATE)!!
${environments.map {it.simpleName}.joinToString("\n") {"""        ${it}Impl.preferences =  context?.getSharedPreferences("$it", Context.MODE_PRIVATE)!!"""}}
        return true
    }

    override fun getType(uri: Uri): String? = TODO("not implemented")
    override fun insert(uri: Uri, values: ContentValues?): Uri? = TODO("not implemented")
    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? = TODO("not implemented")
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int = TODO("not implemented")
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = TODO("not implemented")
}
"""

    fun getContent() = contentTemplate
}
