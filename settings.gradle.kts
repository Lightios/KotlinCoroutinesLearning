//plugins {
//    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
//}

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "KotlinCoroutinesLearning"
include("project1")
include("project2")
include("project3")
include("project4")
include("project5")
include("project6")