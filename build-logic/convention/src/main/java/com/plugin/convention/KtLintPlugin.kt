package com.plugin.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jlleitschuh.gradle.ktlint.KtlintExtension

class KtLintPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jlleitschuh.gradle.ktlint")
            }

            // additional EditorConfig : /.editorconfig
            extensions.configure<KtlintExtension> {
                android.set(true)
                verbose.set(true)
                outputToConsole.set(true)
                reporters {
                    reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
                }
            }
        }
    }
}
