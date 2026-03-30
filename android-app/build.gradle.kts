plugins {
    alias(libs.plugins.bookpin.android.application)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.bookpin.ktlint)
    alias(libs.plugins.googleServices)
    alias(libs.plugins.firebaseCrashlyticsPlugin)
}

android {
    namespace = "com.phase.bookpin.androidapp"

    buildFeatures {
        buildConfig = true
        compose = true
    }
}

dependencies {
    implementation(projects.composeApp)
    implementation(projects.core.dataLocal)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.splashscreen)
    implementation(libs.material)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.androidx.compose)

    implementation(libs.koin.core)
    implementation(libs.koin.android)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
