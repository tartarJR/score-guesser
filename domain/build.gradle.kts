plugins {
    kotlin("jvm")
    kotlin("kapt")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(":core"))

    implementation(Dependencies.Kotlin.stdlib)

    dagger()
    rxJava()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = AppConfig.kotlinJVMTarget
}
