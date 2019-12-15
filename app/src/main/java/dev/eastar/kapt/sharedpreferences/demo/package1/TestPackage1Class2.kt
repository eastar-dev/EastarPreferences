package dev.eastar.kapt.sharedpreferences.demo.package1

import dev.eastar.pref.annotation.Pref

@Pref(defaultSharedPreferences = true)
interface TestPackage1Class2 {
    val package1Class2Value1: Boolean
    val package1Class2Value2: Int
}