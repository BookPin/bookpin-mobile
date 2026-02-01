plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)
}

kotlin {
    androidLibrary {
        namespace = "com.phase.bookpin.model"
        compileSdk = libs.versions.compileSdk
            .get()
            .toInt()
        minSdk = libs.versions.minSdk
            .get()
            .toInt()
        androidResources.enable = true
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Model"
        }
    }
}
