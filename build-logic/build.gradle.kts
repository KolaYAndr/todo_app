plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins.register("telegram-reporter") {
        id = "telegram-reporter"
        implementationClass = "com.cleverpumpkin.build_logic.task.TgPlugin"
    }
}

dependencies {
    implementation(libs.agp)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.kotlin.coroutines.core)

    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.core)

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

}