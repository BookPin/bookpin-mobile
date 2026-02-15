import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.BOOLEAN
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)
    alias(libs.plugins.build.konfig)
}

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use { load(it) }
    }
}

buildkonfig {
    packageName = "com.phase.bookpin.data.remote"

    defaultConfigs {
        buildConfigField(BOOLEAN, "IS_DEBUG", "true")
        buildConfigField(STRING, "BASE_URL", localProperties.getProperty("BASE_URL", "https://api.bookpin.com/"))
        buildConfigField(STRING, "AMAZON_S3", "s3.ap-northeast-2.amazonaws.com/")
    }

    defaultConfigs("release") {
        buildConfigField(BOOLEAN, "IS_DEBUG", "false")
    }
}

kotlin {
    compilerOptions.freeCompilerArgs.add("-Xexpect-actual-classes")

    androidLibrary {
        namespace = "com.phase.bookpin.data.remote"
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
            baseName = "DataRemote"
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.ktor.client.core)
        }
        commonMain.dependencies {
            implementation(projects.model)
            implementation(projects.dataApi)
            implementation(libs.kotlinx.coroutines)
            implementation(libs.kermit)
            implementation(libs.bundles.ktor.shared)
            implementation(libs.kotlin.serialization.json)
            implementation(libs.koin.core)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}
