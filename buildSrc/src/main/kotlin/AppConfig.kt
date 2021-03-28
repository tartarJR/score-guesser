import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.dsl.DependencyHandler

object AppConfig {
    const val compileSdk = 30
    const val minSdk = 26
    const val targetSdk = 30
    const val buildToolsVersion = "30.0.3"

    const val kotlinJVMTarget = "1.8"
    const val deSugaringLib = "com.android.tools:desugar_jdk_libs:1.1.0"
    val sourceCompatibility = JavaVersion.VERSION_1_8
    val targetCompatibility = JavaVersion.VERSION_1_8
}

fun DependencyHandler.javaApiDesugaring() {
    add("coreLibraryDesugaring", AppConfig.deSugaringLib)
}
