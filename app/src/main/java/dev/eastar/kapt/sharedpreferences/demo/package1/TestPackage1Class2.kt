package dev.eastar.kapt.sharedpreferences.demo.package1

import dev.eastar.pref.annotation.Pref

@Pref(defaultSharedPreferences = true)
interface TestPackage1Class2 {

    operator var Package1Class2Value1: Boolean
    operator var Package1Class2Value2: Int

}

class T : TestPackage1Class2

val t : T
fun ss(){
    t.Package1Class2Value1= false
    val ss = t.Package1Class2Value1
}