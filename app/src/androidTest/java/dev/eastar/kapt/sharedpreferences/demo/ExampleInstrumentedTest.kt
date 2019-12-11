package dev.eastar.kapt.sharedpreferences.demo

import android.content.Context
import androidx.core.content.edit
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
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
class ExampleInstrumentedTest {
    @Before
    fun before() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("dev.eastar.kapt.sharedpreferences.demo", appContext.packageName)

        PrefTestSample.preferences = appContext.getSharedPreferences(SecureRandom.getInstanceStrong().nextInt().toString(), Context.MODE_PRIVATE)
        PrefTestSample.preferences.edit(true) { clear() }
    }

    @Test
    fun useAppContext() {

        assertEquals(PrefTestSample.tEST_BOOLEAN, false)
        assertEquals(PrefTestSample.tEST_SET, emptySet<String>())
    }
}
