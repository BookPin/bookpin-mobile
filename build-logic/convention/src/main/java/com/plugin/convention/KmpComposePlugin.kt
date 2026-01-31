package com.plugin.convention

import com.plugin.convention.configure.kotlinMultiPlatform
import com.plugin.convention.configure.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class KmpComposePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.compose")
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        kotlinMultiPlatform {
            sourceSets.apply {
                commonMain.dependencies {
                    implementation(libs.findLibrary("jetbrains-compose-runtime").get().get())
                    implementation(libs.findLibrary("jetbrains-compose-foundation").get().get())
                    implementation(libs.findLibrary("jetbrains-compose-ui").get().get())
                    implementation(libs.findLibrary("jetbrains-compose-animation").get().get())
                    implementation(libs.findLibrary("jetbrains-compose-component").get().get())
                    implementation(libs.findLibrary("jetbrains-compose-material3").get().get())
                }

                androidMain.dependencies {
                    implementation(
                        project.dependencies.platform(
                            libs.findLibrary("androidx-compose-bom").get().get()
                        )
                    )
                    implementation(libs.findLibrary("androidx-activity-compose").get().get())
                    implementation(libs.findLibrary("androidx-compose-lifecycle").get().get())
                    implementation(libs.findLibrary("androidx-compose-ui-tooling-preview").get().get())
                    implementation(libs.findLibrary("androidx-compose-ui-tooling").get().get())
                }
            }
        }
    }
}
