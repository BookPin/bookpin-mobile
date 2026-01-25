plugins {
    alias(libs.plugins.bookpin.android.application)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.bookpin.ktlint)
}

android {
    namespace = "com.phase.bookpin.androidapp"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(projects.composeApp)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.androidx.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
