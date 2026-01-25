package com.plugin.convention

import com.plugin.convention.configure.androidApplication
import com.plugin.convention.configure.configureKotlinAndroid
import com.plugin.convention.configure.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidApplicationPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
            }

            androidApplication {
                configureKotlinAndroid(this)

                namespace?.let {
                    namespace = it
                }

                defaultConfig {
                    versionCode = libs.findVersion("versionCode").get().requiredVersion.toInt()
                    versionName = libs.findVersion("versionName").get().requiredVersion

                    targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
                }

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }

                buildTypes {
                    debug {
                        isMinifyEnabled = false
                        isShrinkResources = false

                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }

                    release {
                        isMinifyEnabled = true
                        isShrinkResources = true

                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }
            }
        }
    }
}