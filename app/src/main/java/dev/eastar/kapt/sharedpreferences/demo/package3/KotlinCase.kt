package dev.eastar.kapt.sharedpreferences.demo.package3

import dev.eastar.pref.annotation.Pref

@Pref
interface TestKotlinInterfaceCompanionObject {
    companion object {
        const val package3Class2Value1 = false
        const val Package3Class2Value2 = 10
    }
}

@Pref
class TestKotlinClassCompanionObject {
    companion object {
        const val package3Class2Value1 = false
        const val Package3Class2Value2 = 10
    }
}

@Pref
data class TestKotlinDataClass(val package3Class2Value1: Boolean = false,
                               val Package3Class2Value2: Int = 10)

@Pref
class TestKotlinClass {
    val package3Class2Value1 = false
    val Package3Class2Value2 = 10
}

@Pref
data class TestKotlinDataClassVar(var package3Class2Value1: Boolean,
                                  var Package3Class2Value2: Int )

@Pref
class TestKotlinClassVar {
    @JvmField
    var testKotlinClassVarBoolean = true
    var testKotlinClassVarInt = Int.MAX_VALUE
    var testKotlinClassVarLong = Long.MAX_VALUE
    var testKotlinClassVarString = "I am string"
    var testKotlinClassVarStringSet = setOf<String>("I am string", "one", "tow", "three")
}

@Pref
interface TestKotlinInterface {
    val package3Class2Value1: Boolean
    val Package3Class2Value2: Int
}

@Pref
interface TestKotlinInterfaceVar {
    var package3Class2Value1: Boolean
    var Package3Class2Value2: Int
}
