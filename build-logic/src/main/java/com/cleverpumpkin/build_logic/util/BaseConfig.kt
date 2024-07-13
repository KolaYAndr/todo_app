package com.cleverpumpkin.build_logic.util

import com.android.build.gradle.BaseExtension

fun BaseExtension.baseAndroidConfig() {
    setCompileSdkVersion(CompileConsts.COMPILE_SDK)
    defaultConfig {
        minSdk = CompileConsts.MIN_SDK

        versionCode = CompileConsts.VERSION_CODE
        versionName = CompileConsts.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = CompileConsts.COMPILE_JDK_VERSION
        targetCompatibility = CompileConsts.COMPILE_JDK_VERSION
    }
    kotlinOptions {
        jvmTarget = CompileConsts.KOTLIN_JVM_TARGET
    }
}
