@file:Suppress("SimplifyBooleanWithConstants")

package dev.eastar.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("SharedPreferences 테스트")
class SharePreferencesExtendTest {

    companion object {
        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            Pref.preferences = context.getSharedPreferences(Pref::class.java.name, Context.MODE_PRIVATE)
        }
    }

    @DisplayName("값이 없을때 테스트")
    @Test
    fun testEmptyValue() {
        assertTrue(Pref.TEST_EMPTY_BOOLEAN.get<Boolean>() == false)
        assertTrue(Pref.TEST_EMPTY_INT.get<Int>() == 0)
        assertTrue(Pref.TEST_EMPTY_FLOAT.get<Float>() == 0F)
        assertTrue(Pref.TEST_EMPTY_LONG.get<Long>() == 0L)
        assertTrue(Pref.TEST_EMPTY_STRING.get<String>() == "")
        assertTrue(Pref.TEST_EMPTY_SET.get<Set<String>>() == emptySet<String>())
    }

    @DisplayName("값이 없을때 기본값 테스트")
    @Test
    fun testDefaultValue() {
        assertTrue(Pref.TEST_EMPTY_BOOLEAN.get(true) == true)
        assertTrue(Pref.TEST_EMPTY_INT.get(123) == 123)
        assertTrue(Pref.TEST_EMPTY_FLOAT.get(1.23F) == 1.23F)
        assertTrue(Pref.TEST_EMPTY_LONG.get(Long.MAX_VALUE) == Long.MAX_VALUE)
        assertTrue(Pref.TEST_EMPTY_STRING.get("enum define shared preferences") == "enum define shared preferences")
        assertTrue(Pref.TEST_EMPTY_SET.get(setOf("enum", "define", "shared", "preferences")) == setOf("enum", "define", "shared", "preferences"))
    }

    @DisplayName("값 넣은 후 불러오기 테스트")
    @Test
    fun testPutAndGetValue() {
        Pref.TEST_BOOLEAN.put(true)
        assertTrue(Pref.TEST_BOOLEAN.get<Boolean>() == true)
        assertTrue(Pref.TEST_BOOLEAN.get(false) == true)

        Pref.TEST_INT.put(123)
        assertTrue(Pref.TEST_INT.get<Int>() == 123)
        assertTrue(Pref.TEST_INT.get(111) == 123)

        Pref.TEST_FLOAT.put(1.23F)
        assertTrue(Pref.TEST_FLOAT.get<Float>() == 1.23F)
        assertTrue(Pref.TEST_FLOAT.get(-1.1F) == 1.23F)

        Pref.TEST_LONG.put(Long.MAX_VALUE)
        assertTrue(Pref.TEST_LONG.get(Long.MAX_VALUE) == Long.MAX_VALUE)

        Pref.TEST_STRING.run {
            val source = "enum define shared preferences"
            put(source)

            assertThat(get<String>(), equalTo(source))
            assertThat(get("not $source"), equalTo(source))
        }

        Pref.TEST_SET.put(setOf("enum", "define", "shared", "preferences"))
        assertTrue(Pref.TEST_SET.get<Set<String>>() == setOf("enum", "define", "shared", "preferences"))
        assertTrue(Pref.TEST_SET.get(setOf("not", "enum", "define", "shared", "preferences")) == setOf("enum", "define", "shared", "preferences"))
    }

    @Suppress("UNUSED_VARIABLE")
    @DisplayName("값 넣은 후 다른 타입으로 불러오기 오류 테스트")
    @Test
    fun testClassCastException() {
        assertThrows(ClassCastException::class.java) {
            Pref.TEST_THROW_BOOLEAN2NUMBER.put(true)
            val result: Int = Pref.TEST_THROW_BOOLEAN2NUMBER.get()!!
        }

        assertThrows(ClassCastException::class.java) {
            Pref.TEST_THROW_INT2LONG.put(1)
            val result: Long = Pref.TEST_THROW_INT2LONG.get()!!
        }

        assertThrows(ClassCastException::class.java) {
            Pref.TEST_THROW_INT2STRING.put(1)
            val result: String = Pref.TEST_THROW_INT2STRING.get()!!
        }
    }
}

enum class Pref : SharePreferencesExtend {
    TEST_EMPTY_BOOLEAN,
    TEST_EMPTY_INT,
    TEST_EMPTY_FLOAT,
    TEST_EMPTY_LONG,
    TEST_EMPTY_STRING,

    TEST_EMPTY_SET,
    TEST_BOOLEAN,
    TEST_INT,
    TEST_FLOAT,
    TEST_LONG,
    TEST_STRING,
    TEST_SET,

    TEST_THROW_BOOLEAN2NUMBER,
    TEST_THROW_INT2LONG,
    TEST_THROW_INT2STRING;

    companion object {
        lateinit var preferences: SharedPreferences
    }
}

