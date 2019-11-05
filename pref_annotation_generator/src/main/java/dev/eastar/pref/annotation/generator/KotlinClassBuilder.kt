package dev.eastar.pref.annotation.generator

import javax.lang.model.element.Element
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.tools.Diagnostic

/**
 * Custom Kotlin Class Builder which returns file content string
 * This is for learning purpose only.
 * Use KotlinPoet for production app
 * KotlinPoet can be found at https://github.com/square/kotlinpoet
 */
class KotlinClassBuilder(className: String, packageName: String, environment: Element) {
    private val contentTemplate = """
package $packageName
import android.content.SharedPreferences

object $className : PrefInterface {
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

    fun getTestBoolean     (defValue: Boolean     = false     ) = preferences.getBoolean    ("TEST_BOOLEAN      ", defValue)
    fun getTestInt         (defValue: Int         = -1        ) = preferences.getInt        ("TEST_INT          ", defValue)
    fun getTestFloat       (defValue: Float       = -1F       ) = preferences.getFloat      ("TEST_FLOAT        ", defValue)
    fun getTestLong        (defValue: Long        = -1L       ) = preferences.getLong       ("TEST_LONG         ", defValue)
    fun getTestString      (defValue: String      = ""        ) = preferences.getString     ("TEST_STRING       ", defValue)!!
    fun getTestSet         (defValue: Set<String> = emptySet()) = preferences.getStringSet  ("TEST_SET          ", defValue)!!

    //@formatter:on
}
    """.trimIndent()

    fun getContent(): String {
        return contentTemplate
    }

    //fun typeConvert(type: TypeMirror ){
    //    when(type.kind){
    //        TypeKind.BOOLEAN ->
    //    }
    //}

}



/*
w: warning: ===========================================================
w: warning: dev.eastar.sharedpreferences.PrefInterface dev.eastar.sharedpreferences.PrefInterface
w: warning: getPreferences() ()android.content.SharedPreferences
w: warning: setPreferences(android.content.SharedPreferences) (android.content.SharedPreferences)void
w: warning: getTEST_BOOLEAN() ()boolean
w: warning: setTEST_BOOLEAN(boolean) (boolean)void
w: warning: getTEST_INT() ()int
w: warning: setTEST_INT(int) (int)void
w: warning: getTEST_FLOAT() ()float
w: warning: setTEST_FLOAT(float) (float)void
w: warning: getTEST_LONG() ()long
w: warning: setTEST_LONG(long) (long)void
w: warning: getTEST_STRING() ()java.lang.String
w: warning: setTEST_STRING(java.lang.String) (java.lang.String)void
w: warning: getTEST_SET() ()java.util.Set<java.lang.String>
w: warning: setTEST_SET(java.util.Set<java.lang.String>) (java.util.Set<java.lang.String>)void
w: warning: ===========================================================
 */





