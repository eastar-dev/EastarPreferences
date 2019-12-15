package dev.eastar.kapt.sharedpreferences.demo.package1

import dev.eastar.pref.annotation.Pref

@Pref(defaultSharedPreferences = true)
interface TestPackage1Class2 {
     var Package1Class2Value1: Boolean
     var Package1Class2Value2: Int

}
