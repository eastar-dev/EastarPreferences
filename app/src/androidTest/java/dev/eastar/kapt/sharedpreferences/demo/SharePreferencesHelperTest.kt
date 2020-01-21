package dev.eastar.kapt.sharedpreferences.demo

import android.content.Context
import androidx.core.content.edit
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matchers
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
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
        TestSamplePref.preferences = context.getSharedPreferences(SecureRandom.getInstanceStrong().nextInt().toString(), Context.MODE_PRIVATE)
        TestSamplePref.preferences.edit(true) { clear() }
    }

    @Test
    fun testDefaultValue1() {
        //@formatter:off
        assertThat(TestSamplePref.testEmptyBoolean , Matchers.equalTo(false))
        assertThat(TestSamplePref.testEmptyInt     , Matchers.equalTo(-1))
        assertThat(TestSamplePref.testEmptyFloat   , Matchers.equalTo(-1F))
        assertThat(TestSamplePref.testEmptyLong    , Matchers.equalTo(-1L))
        assertThat(TestSamplePref.testEmptyString  , Matchers.equalTo(""))
        assertThat(TestSamplePref.testEmptySet     , Matchers.equalTo(emptySet()))
        //@formatter:on
    }

    @Test
    fun testEmptyValue2() {
        //@formatter:off
        val emptyBoolean : Boolean     = TestSamplePref.testEmptyBoolean
        val emptyInt     : Int         = TestSamplePref.testEmptyInt
        val emptyFloat   : Float       = TestSamplePref.testEmptyFloat
        val emptyLong    : Long        = TestSamplePref.testEmptyLong
        val emptyString  : String      = TestSamplePref.testEmptyString
        val emptySet     : Set<String> = TestSamplePref.testEmptySet
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
        TestSamplePref.testBoolean = true
        TestSamplePref.testInt     = 123
        TestSamplePref.testFloat   = 1.23F
        TestSamplePref.testLong    = Long.MAX_VALUE
        TestSamplePref.testString  = "enum define shared preferences"
        TestSamplePref.testSet     = setOf("enum", "define", "shared", "preferences")

        assertTrue(true                                             == TestSamplePref.testBoolean)
        assertTrue(123                                              == TestSamplePref.testInt    )
        assertTrue(1.23F                                            == TestSamplePref.testFloat  )
        assertTrue(Long.MAX_VALUE                                   == TestSamplePref.testLong   )
        assertTrue("enum define shared preferences"                 == TestSamplePref.testString )
        assertTrue(setOf("enum", "define", "shared", "preferences") == TestSamplePref.testSet    )
    }
}


