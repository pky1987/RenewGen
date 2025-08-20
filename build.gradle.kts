// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}

buildscript {
    // No repositories needed here as they are defined in settings.gradle.kts
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
