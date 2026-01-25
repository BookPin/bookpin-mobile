plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of("17"))
    }
}

dependencies {
    compileOnly(libs.kotlin.gradle)
    compileOnly(libs.android.build.gradle)
    compileOnly(libs.ktlint.gradle)
    compileOnly(libs.compose.gradle)
    compileOnly(libs.compose.compiler.gradle)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "com.phase.bookpin.application"
            implementationClass = "com.plugin.convention.AndroidApplicationPlugin"
        }
        register("ktlint") {
            id = "com.phase.bookpin.ktlint"
            implementationClass = "com.plugin.convention.KtLintPlugin"
        }
        register("kotlinSerialization") {
            id = "com.phase.bookpin.serialization"
            implementationClass = "com.plugin.convention.KotlinSerializationPlugin"
        }
        register("KmpCompose") {
            id = "com.phase.bookpin.compose"
            implementationClass = "com.plugin.convention.KmpComposePlugin"
        }
    }
}
