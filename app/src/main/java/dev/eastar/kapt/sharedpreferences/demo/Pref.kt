/*
 * Copyright 2019 eastar Jeong
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
package dev.eastar.kapt.sharedpreferences.demo

import android.content.SharedPreferences
import dev.eastar.pref.annotation.Pref

@Pref
interface TestSampleSharedPreferences {
    val TEST_EMPTY_BOOLEAN: Boolean
    val TEST_EMPTY_INT: Int
    val TEST_EMPTY_FLOAT: Float
    val TEST_EMPTY_LONG: Long
    val TEST_EMPTY_STRING: String
    val TEST_EMPTY_SET: Set<String>

    val TEST_BOOLEAN: Boolean
    val TEST_INT: Int
    val TEST_FLOAT: Float
    val TEST_LONG: Long
    val TEST_STRING: String
    val TEST_SET: Set<String>
}

@Pref
interface TestSample2SharedPreferences {
    var testBoolean: Boolean
    var testInt: Int
    var testFloat: Float
    var testLong: Long
    var testString: String
    var testSet: Set<String>
}

@Pref
data class TestSample3SharedPreferences (
    var testString: String? = "test_default"
)


//object TestSample2 : TestSample2SharedPreferences{
//    override var testBoolean: Boolean
//        get() = TODO("Not yet implemented")
//        set(value) {}
//    override var testInt: Int
//        get() = TODO("Not yet implemented")
//        set(value) {}
//    override var testFloat: Float
//        get() = TODO("Not yet implemented")
//        set(value) {}
//    override var testLong: Long
//        get() = TODO("Not yet implemented")
//        set(value) {}
//    override var testString: String
//        get() = TODO("Not yet implemented")
//        set(value) {}
//    override var testSet: Set<String>
//        get() = TODO("Not yet implemented")
//        set(value) {}
//}