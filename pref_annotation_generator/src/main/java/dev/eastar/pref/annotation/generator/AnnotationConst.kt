package dev.eastar.pref.annotation.generator

interface AnnotationConst {
    companion object {
        const val PACKAGE_NAME = "dev.eastar.sharedpreferences"
        const val CLASS_TAIL = "SharedPreferences"
        const val GENERATED_INITIALIZER_CLASS = "PrefInitializerProvider"
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }
}