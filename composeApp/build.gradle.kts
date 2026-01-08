import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
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
            implementation(project(":domain"))
            implementation(project(":data"))
            implementation(project(":ui"))

            implementation(compose.runtime)
            implementation(compose.material3)

            implementation(libs.navigation.compose)
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
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.annotations.common)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.turbine)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.test)
            implementation(libs.assertk)

        }

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.kotlinx.coroutines.android)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
        }


        iosMain.dependencies {}

        androidUnitTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.test.junit)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.assertk)
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

dependencies {
    kover(project(":domain"))
    kover(project(":data"))
    kover(project(":ui"))
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
                    "com.flacinc.todok_mp.MainActivity",
                    "com.flacinc.todok_mp.MainApplication",
                    "com.flacinc.data.database.*",
                    "com.flacinc.ui.navigation.*",
                    "com.flacinc.ui.utils.*",
                    "com.flacinc.ui.theme.*",
                    "todok_mp.*.generated.resources.*",
                    "com.flacinc.data.di.DatabaseModuleKt",
                    "com.flacinc.todokmp.data.database.AppDatabase",
                    "com.flacinc.todok_mp.data.database.sql",
                    "com.flacinc.todok_mp.data.database.sql.data",
                    "com.flacinc.todokmp.data.database.sql"
                )
                packages(
                    "com.flacinc.todok_mp.di"
                )
                annotatedBy(
                    "androidx.compose.runtime.Composable"
                )
            }
        }
    }
}