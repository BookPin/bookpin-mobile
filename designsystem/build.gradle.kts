plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)
    alias(libs.plugins.bookpin.compose)
}

compose {
    resources {
        publicResClass = true
        packageOfResClass = "bookpin.designsystem.generated.resources"
    }
}

kotlin {
    androidLibrary {
        namespace = "com.phase.bookpin.designsystem"
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
            baseName = "DesignSystemKit"
        }
    }
}
