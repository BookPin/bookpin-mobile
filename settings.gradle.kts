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
include(":auth")
include(":common")
include(":data")
include(":data-remote")
include(":domain")
include(":model")
include(":data-auth")
include(":data-api")
include(":data-local")
include(":home")
include(":search")
include(":bookdetail")
include(":settings")
