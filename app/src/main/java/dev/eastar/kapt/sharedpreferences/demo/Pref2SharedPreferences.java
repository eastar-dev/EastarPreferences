package dev.eastar.kapt.sharedpreferences.demo;

import dev.eastar.pref.annotation.Pref;

@Pref
public interface Pref2SharedPreferences  {
    boolean TEST_BOOLEAN = true;
    int TEST_INT = 100;
    String TEST_STRING = "hello";
}
