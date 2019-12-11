package dev.eastar.kapt.sharedpreferences.demo

import android.content.Context
import androidx.core.content.edit
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.security.SecureRandom

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SharePreferencesHelperTest {
    @Before
    fun beforeAll() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        PrefTestSample.preferences = context.getSharedPreferences(SecureRandom.getInstanceStrong().nextInt().toString(), Context.MODE_PRIVATE)
        PrefTestSample.preferences.edit(true) { clear() }
    }

    @Test
    fun testDefaultValue1() {
        //@formatter:off
        assertThat(PrefTestSample.tEST_EMPTY_BOOLEAN, Matchers.equalTo(false))
        assertThat(PrefTestSample.tEST_EMPTY_INT    , Matchers.equalTo(-1))
        assertThat(PrefTestSample.tEST_EMPTY_FLOAT  , Matchers.equalTo(-1F))
        assertThat(PrefTestSample.tEST_EMPTY_LONG   , Matchers.equalTo(-1L))
        assertThat(PrefTestSample.tEST_EMPTY_STRING , Matchers.equalTo(""))
        assertThat(PrefTestSample.tEST_EMPTY_SET    , Matchers.equalTo(emptySet()))
        //@formatter:on
    }

    @Test
    fun testEmptyValue2() {
        //@formatter:off
        val emptyBoolean : Boolean     = PrefTestSample.tEST_EMPTY_BOOLEAN
        val emptyInt     : Int         = PrefTestSample.tEST_EMPTY_INT
        val emptyFloat   : Float       = PrefTestSample.tEST_EMPTY_FLOAT
        val emptyLong    : Long        = PrefTestSample.tEST_EMPTY_LONG
        val emptyString  : String      = PrefTestSample.tEST_EMPTY_STRING
        val emptySet     : Set<String> = PrefTestSample.tEST_EMPTY_SET
        assertTrue(false               == emptyBoolean)
        assertTrue(-1                  == emptyInt    )
        assertTrue(-1F                 == emptyFloat  )
        assertTrue(-1L                 == emptyLong   )
        assertTrue(""                  == emptyString )
        assertTrue(emptySet<String>()  == emptySet    )
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
}


