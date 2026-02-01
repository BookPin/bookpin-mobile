plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)
    alias(libs.plugins.bookpin.kotlin.serialization)
}

kotlin {
    androidLibrary {
        namespace = "com.phase.bookpin.data.api"
        compileSdk = libs.versions.compileSdk
            .get()
            .toInt()
        minSdk = libs.versions.minSdk
            .get()
            .toInt()
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "DataApi"
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines)
            implementation(projects.model)
            implementation(projects.domain)
            implementation(libs.kermit)
        }
    }
}
