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
    id("org.jetbrains.kotlinx.kover") version "0.9.4"

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
            linkerOpts("-lsqlite3")
        }
    }

    sourceSets {

        commonMain.dependencies {
            // Compose
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.navigation.compose)
            implementation(libs.compottie)
            implementation(libs.compottie.resources)
            implementation(libs.kotlinx.collections.immutable)
            implementation(libs.kotlinx.datetime)

            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.kotlinx.coroutines.core)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.core.coroutines)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            api(libs.koin.annotations)

            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.contentNegotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation(libs.kotlinx.serialization.json)

            implementation(libs.kotlinx.datetime)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.annotations.common)
            implementation(libs.kotlinx.coroutines.test)

            implementation(libs.turbine)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.test)
            implementation(libs.assertk)
            implementation(libs.ktor.client.mock)
            implementation(libs.ktor.client.mock)
            implementation(libs.sqldelight.sqlite.driver)


        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.kotlinx.coroutines.android)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

            implementation(libs.sqldelight.android.driver)

            implementation(libs.ktor.client.okhttp)
        }


        iosMain.dependencies {
            implementation(libs.sqldelight.native.driver)

            implementation(libs.ktor.client.darwin)
        }

        androidUnitTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.junit)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.assertk)
            implementation(libs.sqldelight.sqlite.driver)
            implementation(libs.mockk)
            implementation(libs.turbine)
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.koin.ksp.compiler)
    add("kspAndroid", libs.koin.ksp.compiler)
    add("kspIosArm64", libs.koin.ksp.compiler)
    add("kspIosSimulatorArm64", libs.koin.ksp.compiler)
}


sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.flacinc.todok_mp.data.database.sql")
        }
    }
}

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

kover {
    reports {
        filters {
            excludes {
                classes(
                    "*_Factory",
                    "*_HiltModules*",
                    "*Module",
                    "*Module\$*",
                    "*.BuildConfig",
                    "*.ComposableSingletons*",
                    "*TodokMpAppKt*",
                    "*TodokMpApp*",
                    "*.TodokMpAppKt",
                    "*.TodokMpAppKt\$*",
                    "com.flacinc.todok_mp.BuildKonfig",
                    "com.flacinc.todok_mp.ui.utils.*",
                    "com.flacinc.todok_mp.MainActivity",
                    "com.flacinc.todok_mp.MainApplication",
                    "com.flacinc.todok_mp.ui.navigation.*",
                    "com.flacinc.todok_mp.data.database.*",
                    "todok_mp.composeapp.generated.resources.*"
                )
                packages(
                    "com.flacinc.todok_mp.di",
                    "com.flacinc.todok_mp.ui.theme",
                )
                annotatedBy(
                    "androidx.compose.runtime.Composable"
                )
            }
        }
    }
}