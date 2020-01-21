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
package dev.eastar.kapt.sharedpreferences.demo

import dev.eastar.pref.annotation.Pref

//@Pref
interface TestSample {
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

    var app_debug : Int
}

//@Pref
interface TestSample2 {
    val TEST_BOOLEAN: Boolean
    val TEST_INT: Int
    val TEST_FLOAT: Float
    val TEST_LONG: Long
    val TEST_STRING: String
    val TEST_SET: Set<String>
}