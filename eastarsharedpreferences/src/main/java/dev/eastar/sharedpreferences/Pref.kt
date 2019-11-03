package dev.eastar.sharedpreferences

import android.content.SharedPreferences

enum class Pref {
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