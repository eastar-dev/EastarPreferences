plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    namespace = "dev.eastar.pref.androidmanifest"
    compileSdk = 36

    defaultConfig {
        minSdk = 19
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_1_8)
        }
    }

    lint {
        abortOnError = false
        targetSdk = 35
    }
}

dependencies {
    api("androidx.preference:preference-ktx:1.2.1")
    api(project(":pref_annotation_internal"))
}
