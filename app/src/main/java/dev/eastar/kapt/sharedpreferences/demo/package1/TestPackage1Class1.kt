package dev.eastar.kapt.sharedpreferences.demo.package1

import dev.eastar.pref.annotation.Pref

@Pref("NameTestPackage1Class1")
interface TestPackage1Class1 {
    var package1Class1Value1: Boolean
    var package1Class1Value2: Int
}