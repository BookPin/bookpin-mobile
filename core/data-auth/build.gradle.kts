plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)
}

kotlin {
    compilerOptions.freeCompilerArgs.add("-Xexpect-actual-classes")

    androidLibrary {
        namespace = "com.phase.bookpin.data.auth"
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
            baseName = "DataAuth"
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.kakao.user)
            implementation(libs.koin.core)
        }
        commonMain.dependencies {
            implementation(projects.domain)
            implementation(projects.model)
            implementation(libs.kotlinx.coroutines)
            implementation(libs.kermit)
            implementation(libs.koin.core)
        }
        iosMain.dependencies {
            implementation(libs.koin.core)
        }
    }
}
