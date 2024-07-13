package com.cleverpumpkin.build_logic.task

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.Variant
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.create

class TgPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val androidComponents =
            target.extensions.findByType(AndroidComponentsExtension::class.java)
                ?: throw GradleException("Android plugin required")

        val ext = target.extensions.create("telegramReporter", TelegramExtension::class)
        val api = TgApi(HttpClient(OkHttp))

        androidComponents.onVariants { variant: Variant ->
            val artifacts = variant.artifacts.get(SingleArtifact.APK)
            val name = variant.name.replaceFirstChar { it.uppercase() }

            val validateTaskProvider = target.tasks.register(
                "validateApkSizeFor$name",
                ValidateSizeTask::class.java,
                api,
            )
            validateTaskProvider.configure {
                token.set(ext.token)
                chatId.set(ext.chatId)
                apkDir.set(artifacts)
                maxApkSize.set(ext.maxApkSize)
            }

            target.tasks.register(
                "analyzeApkFor$name",
                AnalyzeApkTask::class.java,
                api,
            ).configure {
                dependsOn("create${name}ApkListingFileRedirect")
                token.set(ext.token)
                chatId.set(ext.chatId)
                apkDir.set(artifacts)
                projectDir.set(project.projectDir)
            }

            target.tasks.register(
                "reportTelegramApkFor$name",
                TgTask::class.java,
                api,
            ).configure {
                val sizeStr: Property<String> = project.objects.property(String::class.java)
                sizeStr.set("")
                if (ext.validationEnabled.get() == true) {
                    dependsOn(validateTaskProvider)
                    sizeStr.set(validateTaskProvider.flatMap { it.size })
                }
                this.sizeStr.set(sizeStr)
                apkDir.set(artifacts)
                token.set(ext.token)
                chatId.set(ext.chatId)

                if (ext.analysisEnabled.get() == true) {
                    finalizedBy("analyzeApkFor$name")
                }
            }
        }
    }
}

interface TelegramExtension {
    val chatId: Property<String>
    val token: Property<String>
    val maxApkSize: Property<Int>
    val validationEnabled: Property<Boolean>
    val analysisEnabled: Property<Boolean>
}
