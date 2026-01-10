plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.sqldelight)
    id("org.jetbrains.kotlinx.kover") version "0.9.4"
}

kotlin {
    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":domain"))
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.kotlinx.coroutines.core)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.annotations.common)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.turbine)
            implementation(libs.assertk)
        }

        androidMain.dependencies {
            implementation(libs.sqldelight.android.driver)
        }

        iosMain.dependencies {
            implementation(libs.sqldelight.native.driver)
        }

        androidUnitTest.dependencies {
            implementation(libs.kotlin.test.junit)
            implementation(libs.sqldelight.sqlite.driver)
            implementation(libs.mockk)
            implementation(libs.turbine)
        }
    }
}

android {
    namespace = "com.flacinc.data"
    compileSdk = 36
    defaultConfig {
        minSdk = 28
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.flacinc.todok_mp.data.database.sql")
        }
    }
}