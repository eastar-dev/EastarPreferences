package dev.eastar.kapt.sharedpreferences.demo.package3

import dev.eastar.pref.annotation.Pref

@Pref
interface TestPackage3Class2 {
    companion object {
        const val package3Class2Value1 = false
        const val Package3Class2Value2 = 10
    }
}

@Pref
class TestPackage3Class2_3 {
    companion object {
        const val package3Class2Value1 = false
        const val Package3Class2Value2 = 10
    }
}

@Pref
data class TestPackage3Class2_4(val package3Class2Value1: Boolean = false,
                                val Package3Class2Value2: Int = 10)

@Pref
class TestPackage3Class2_5 {
    val package3Class2Value1 = false
    val Package3Class2Value2 = 10
}

@Pref
interface TestPackage3Class2_2 {
    val package3Class2Value1: Boolean
    val Package3Class2Value2: Int
}