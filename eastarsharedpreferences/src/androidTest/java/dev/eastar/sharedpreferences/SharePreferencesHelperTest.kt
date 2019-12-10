package dev.eastar.sharedpreferences

import android.content.Context
import androidx.core.content.edit
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import java.security.SecureRandom

class SharePreferencesHelperTest {
    companion object {
        @Before
        @JvmStatic
        fun beforeAll() {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            PrefTestSample.preferences = context.getSharedPreferences(SecureRandom.getInstanceStrong().nextInt().toString(), Context.MODE_PRIVATE)
            PrefTestSample.preferences.edit(true) { clear() }
        }
    }

    @Test
    fun testEmptyValue() {
        //@formatter:off
        assertTrue(PrefTestSample.tEST_EMPTY_BOOLEAN == false             )
        assertTrue(PrefTestSample.tEST_EMPTY_INT     == 0                 )
        assertTrue(PrefTestSample.tEST_EMPTY_FLOAT   == 0F                )
        assertTrue(PrefTestSample.tEST_EMPTY_LONG    == 0L                )
        assertTrue(PrefTestSample.tEST_EMPTY_STRING  == ""                )
        assertTrue(PrefTestSample.tEST_EMPTY_SET     == emptySet<String>())
        //@formatter:on
    }

    @Test
    fun testEmptyValueOperator() {
        //@formatter:off
        val emptyBoolean : Boolean     = PrefTestSample.tEST_EMPTY_BOOLEAN
        val emptyInt     : Int         = PrefTestSample.tEST_EMPTY_INT
        val emptyFloat   : Float       = PrefTestSample.tEST_EMPTY_FLOAT
        val emptyLong    : Long        = PrefTestSample.tEST_EMPTY_LONG
        val emptyString  : String      = PrefTestSample.tEST_EMPTY_STRING
        val emptySet     : Set<String> = PrefTestSample.tEST_EMPTY_SET
        assertTrue(false               == emptyBoolean)
        assertTrue(0                   == emptyInt    )
        assertTrue(0F                  == emptyFloat  )
        assertTrue(0L                  == emptyLong   )
        assertTrue(""                  == emptyString )
        assertTrue(emptySet<String>()  == emptySet    )
        //@formatter:on

    }

    @Test
    fun testDefaultValue() {
        //@formatter:off
        assertTrue(PrefTestSample.tEST_EMPTY_BOOLEAN == true                                             )
        assertTrue(PrefTestSample.tEST_EMPTY_INT     == 123                                              )
        assertTrue(PrefTestSample.tEST_EMPTY_FLOAT   == 1.23F                                            )
        assertTrue(PrefTestSample.tEST_EMPTY_LONG    == Long.MAX_VALUE                                   )
        assertTrue(PrefTestSample.tEST_EMPTY_STRING  == "enum define shared preferences"                 )
        assertTrue(PrefTestSample.tEST_EMPTY_SET     == setOf("enum", "define", "shared", "preferences") )
        //@formatter:on
    }

    @Test
    fun testPutAndGetValue() {
        //@formatter:off
        PrefTestSample.tEST_BOOLEAN = true
        PrefTestSample.tEST_INT     = 123
        PrefTestSample.tEST_FLOAT   = 1.23F
        PrefTestSample.tEST_LONG    = Long.MAX_VALUE
        PrefTestSample.tEST_STRING  = "enum define shared preferences"
        PrefTestSample.tEST_SET     = setOf("enum", "define", "shared", "preferences")

        assertTrue(true                                             == PrefTestSample.tEST_BOOLEAN)
        assertTrue(123                                              == PrefTestSample.tEST_INT    )
        assertTrue(1.23F                                            == PrefTestSample.tEST_FLOAT  )
        assertTrue(Long.MAX_VALUE                                   == PrefTestSample.tEST_LONG   )
        assertTrue("enum define shared preferences"                 == PrefTestSample.tEST_STRING )
        assertTrue(setOf("enum", "define", "shared", "preferences") == PrefTestSample.tEST_SET    )
    }

    @Test
    fun testPutAndGetValueNotDefault() {
        //@formatter:off
        PrefTestSample.tEST_BOOLEAN = true
        PrefTestSample.tEST_INT     = 123
        PrefTestSample.tEST_FLOAT   = 1.23F
        PrefTestSample.tEST_LONG    = Long.MAX_VALUE
        PrefTestSample.tEST_STRING  = "enum define shared preferences"
        PrefTestSample.tEST_SET     = setOf("enum", "define", "shared", "preferences")

        assertTrue(true                                             == PrefTestSample.tEST_BOOLEAN )
        assertTrue(123                                              == PrefTestSample.tEST_INT     )
        assertTrue(1.23F                                            == PrefTestSample.tEST_FLOAT   )
        assertTrue(Long.MAX_VALUE                                   == PrefTestSample.tEST_LONG    )
        assertTrue("enum define shared preferences"                 == PrefTestSample.tEST_STRING  )
        assertTrue(setOf("enum", "define", "shared", "preferences") == PrefTestSample.tEST_SET     )
    }
}


