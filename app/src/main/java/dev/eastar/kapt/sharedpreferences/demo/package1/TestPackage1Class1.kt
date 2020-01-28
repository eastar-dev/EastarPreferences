@file:Suppress("unused", "UNUSED_VARIABLE")

package dev.eastar.kapt.sharedpreferences.demo.package1

import dev.eastar.pref.annotation.Pref

@Pref("NameTestPackage1Class1")
interface TestPackage1Class1 {
    val package1Class1Value1: Boolean
    var package1Class1Value2: Int
}

//fun useSample() {
//    val package1Class1Value1 = TestPackage1Class1Pref.getPackage1Class1Value1(true)
//    TestPackage1Class1Pref.package1Class1Value1 = true
//    val package1Class1Value2 = TestPackage1Class1Pref.package1Class1Value1
//}
