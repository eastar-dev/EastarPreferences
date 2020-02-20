package dev.eastar.pref.annotation.generator

import dev.eastar.pref.annotation.Pref
import dev.eastar.pref.annotation.generator.AnnotationConst.Companion.CLASS_TAIL
import dev.eastar.pref.annotation.generator.AnnotationConst.Companion.GENERATED_INITIALIZER_CLASS
import dev.eastar.pref.annotation.generator.AnnotationConst.Companion.PACKAGE_NAME
import dev.eastar.pref.annotation.util.Log
import javax.lang.model.element.Element

/**
 * Custom Kotlin Class Builder which returns file content string
 * This is for learning purpose only.
 * Use KotlinPoet for production app
 * KotlinPoet can be found at https://github.com/square/kotlinpoet
 */
class ClassBuilderInitializer(environments: Set<Element>) {
    private var preferences: String

    init {
        Log.w("Generate Initializer Class : [$GENERATED_INITIALIZER_CLASS]")
        preferences = environments.joinToString("\n") {
            //if(!it.simpleName.endsWith(CLASS_TAIL))
            //    return@joinToString ""

            val className = it.simpleName.removeSuffix(CLASS_TAIL)

            val ann = it.getAnnotation(Pref::class.java)

            var packageName = it.enclosingElement.toString()
            if (packageName == "unnamed package")
                packageName = "unnamed"


            val preferenceName = when {
                ann.defaultSharedPreferences ->
                    """androidx.preference.PreferenceManager.getDefaultSharedPreferences(context!!)"""
                ann.value.isNotBlank() ->
                    """context?.getSharedPreferences("${ann.value}", Context.MODE_PRIVATE)!!"""
                else ->
                    """context?.getSharedPreferences("$it", Context.MODE_PRIVATE)!!"""
            }

            """        $packageName.$className.preferences = $preferenceName"""
        }
        //Log.w(preferences)
    }

    private val contentTemplate = """
package $PACKAGE_NAME

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.preference.PreferenceManager

class $GENERATED_INITIALIZER_CLASS : ContentProvider() {
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

    fun getContent() = contentTemplate
}
