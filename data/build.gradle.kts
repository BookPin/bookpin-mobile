plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)
}

kotlin {
    androidLibrary {
        namespace = "com.phase.bookpin.data"
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
            baseName = "Data"
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.model)
            implementation(projects.domain)
            implementation(projects.dataApi)
            implementation(libs.koin.core)
        }
    }
}
