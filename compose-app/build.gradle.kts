plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)
    alias(libs.plugins.bookpin.compose)
    alias(libs.plugins.bookpin.ktlint)
    alias(libs.plugins.bookpin.kotlin.serialization)
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
            implementation(projects.feature.auth)
            implementation(projects.feature.home)
            implementation(projects.feature.search)
            implementation(projects.feature.bookmark)
            implementation(projects.feature.settings)
            implementation(projects.designsystem)
            implementation(projects.core.common)
            implementation(projects.model)
            implementation(projects.core.data)
            implementation(projects.domain)
            implementation(projects.core.dataApi)
            implementation(projects.core.dataRemote)
            implementation(projects.core.dataLocal)

            implementation(libs.jetbrains.navigation3.ui)
            implementation(libs.kermit)
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
