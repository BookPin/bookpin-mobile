plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)
    alias(libs.plugins.bookpin.compose)
    alias(libs.plugins.bookpin.ktlint)
}

kotlin {
    androidLibrary {
        namespace = "com.phase.bookpin"
        compileSdk = libs.versions.compileSdk
            .get()
            .toInt()
        minSdk = libs.versions.minSdk
            .get()
            .toInt()
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.auth)
            implementation(projects.designsystem)
            implementation(projects.common)
            implementation(projects.model)
            implementation(projects.data)
            implementation(projects.domain)
            implementation(projects.dataApi)
            implementation(projects.dataRemote)
            implementation(projects.dataLocal)
            implementation(projects.dataAuth)

            implementation(libs.kermit)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
