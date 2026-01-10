plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.kover.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidLibraryConvention") {
            id = "todok.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("kmpLibraryConvention") {
            id = "todok.kmp.library"
            implementationClass = "KmpLibraryConventionPlugin"
        }
        register("koverConvention") {
            id = "todok.kover"
            implementationClass = "KoverConventionPlugin"
        }
    }
}
