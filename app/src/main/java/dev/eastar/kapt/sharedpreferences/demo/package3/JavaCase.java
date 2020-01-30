package dev.eastar.kapt.sharedpreferences.demo.package3;

import dev.eastar.pref.annotation.Pref;

@Pref
class TestJavaClass {
    boolean package3Class3Value1 = false;
    int Package3Class3Value2 = 10;
}

@Pref
interface TestJavaInterface {
    boolean package3Class1Value1 = false;
    int Package3Class1Value2 = 10;
}