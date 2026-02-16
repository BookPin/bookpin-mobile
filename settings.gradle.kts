rootProject.name = "BookPin"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven { url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

include(":compose-app")
include(":android-app")
include(":designsystem")
include(":domain")
include(":model")
include(":feature:auth")
include(":feature:home")
include(":feature:search")
include(":feature:bookmark")
include(":feature:settings")
include(":core:common")
include(":core:data")
include(":core:data-remote")
include(":core:data-auth")
include(":core:data-api")
include(":core:data-local")
