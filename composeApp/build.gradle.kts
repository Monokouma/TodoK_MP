import org.gradle.declarative.dsl.schema.FqName.Empty.packageName
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.ksp)
    alias(libs.plugins.mokkery)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        // ========================================
        // COMMON MAIN - Code partagé
        // ========================================
        commonMain.dependencies {
            // Compose
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // Lifecycle / ViewModel / MVVM (JetBrains KMP)
            // Inclut collectAsStateWithLifecycle
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            // Coroutines
            implementation(libs.kotlinx.coroutines.core)

            // Koin - Dependency Injection
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.core.coroutines)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            api(libs.koin.annotations)

            // SQLDelight - Database
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines)

            // Ktor - Network (Clean Architecture - Data Layer)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.contentNegotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)

            // Kotlinx Serialization
            implementation(libs.kotlinx.serialization.json)

            // Kotlinx DateTime
            implementation(libs.kotlinx.datetime)
        }

        // ========================================
        // COMMON TEST - Tests partagés
        // ========================================
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.annotations.common)
            implementation(libs.kotlinx.coroutines.test)

            // Turbine - Flow testing
            implementation(libs.turbine)

            // Koin Test
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.test)

            // Ktor Mock Engine
            implementation(libs.ktor.client.mock)

            // Mokkery est activé via le plugin, pas besoin de dépendance explicite
        }

        // ========================================
        // ANDROID MAIN
        // ========================================
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            // Coroutines Android
            implementation(libs.kotlinx.coroutines.android)

            // Koin Android
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

            // SQLDelight Android Driver
            implementation(libs.sqldelight.android.driver)

            // Ktor Android Engine (OkHttp)
            implementation(libs.ktor.client.okhttp)
        }

        // ========================================
        // ANDROID UNIT TEST
        // ========================================
        val androidUnitTest by getting {
            dependencies {
                implementation(libs.junit)
                implementation(libs.kotlin.test.junit)
                implementation(libs.mockk)
                implementation(libs.mockk.agent)

                // Koin Test JUnit4
                implementation(project.dependencies.platform(libs.koin.bom))
                implementation(libs.koin.test.junit4)
            }
        }

        // ========================================
        // IOS MAIN
        // ========================================
        iosMain.dependencies {
            // SQLDelight iOS Driver
            implementation(libs.sqldelight.native.driver)

            // Ktor iOS Engine (Darwin)
            implementation(libs.ktor.client.darwin)
        }
    }
}

// ========================================
// KSP pour Koin Annotations
// ========================================
dependencies {
    add("kspCommonMainMetadata", libs.koin.ksp.compiler)
    add("kspAndroid", libs.koin.ksp.compiler)
    add("kspIosArm64", libs.koin.ksp.compiler)
    add("kspIosSimulatorArm64", libs.koin.ksp.compiler)
}

// ========================================
// SQLDELIGHT Configuration
// ========================================
sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.flacinc.todok_mp.database")
            generateAsync.set(true)
        }
    }
}

// ========================================
// ANDROID Configuration
// ========================================
android {
    namespace = "com.flacinc.todok_mp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.flacinc.todok_mp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}