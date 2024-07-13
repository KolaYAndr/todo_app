package com.cleverpumpkin.build_logic.util

import org.gradle.api.JavaVersion

object CompileConsts {
    const val COMPILE_SDK = 34
    const val TARGET_SDK = 34
    const val MIN_SDK = 26
    val COMPILE_JDK_VERSION = JavaVersion.VERSION_17
    const val KOTLIN_JVM_TARGET = "17"
    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0"

}