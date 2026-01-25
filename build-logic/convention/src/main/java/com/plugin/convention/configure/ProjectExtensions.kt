package com.plugin.convention.configure

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal val Project.compose: ComposeExtension
    get() = extensions["compose"] as ComposeExtension

fun Project.androidApplication(action: ApplicationExtension.() -> Unit) {
    extensions.configure(action)
}

internal fun Project.androidLibrary(action: LibraryExtension.() -> Unit) {
    extensions.configure(action)
}

internal fun Project.kotlinMultiPlatform(action: KotlinMultiplatformExtension.() -> Unit) {
    extensions.configure(action)
}