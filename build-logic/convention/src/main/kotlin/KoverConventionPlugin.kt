import kotlinx.kover.gradle.plugin.dsl.KoverProjectExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class KoverConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlinx.kover")

            extensions.configure<KoverProjectExtension> {
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
        }
    }
}
