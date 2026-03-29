plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)
    alias(libs.plugins.bookpin.compose)
    alias(libs.plugins.bookpin.ktlint)
}

compose {
    resources {
        publicResClass = true
        packageOfResClass = "bookpin.settings.generated.resources"
    }
}

kotlin {
    androidLibrary {
        namespace = "com.phase.bookpin.settings"
        compileSdk = libs.versions.compileSdk
            .get()
            .toInt()
        minSdk = libs.versions.minSdk
            .get()
            .toInt()
        androidResources.enable = true
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Settings"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.designsystem)
            implementation(projects.core.common)
            implementation(projects.model)
            implementation(projects.domain)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.coil.compose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
