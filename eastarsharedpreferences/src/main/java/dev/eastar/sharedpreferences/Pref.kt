/*
 * Copyright 2019 copyright eastar Jeong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.eastar.sharedpreferences

import android.content.SharedPreferences
import dev.eastar.pref.annotation.PrefAnnotation

@PrefAnnotation
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