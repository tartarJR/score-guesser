import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    object Kotlin {
        const val version = "1.4.21"

        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.5.0-beta02"
        const val appCompat = "androidx.appcompat:appcompat:1.2.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val materialComponents = "com.google.android.material:material:1.3.0"

        object Navigation {
            const val version = "2.3.4"

            const val fragment = "androidx.navigation:navigation-fragment-ktx:$version"
            const val ui = "androidx.navigation:navigation-ui-ktx:$version"
        }

        object LifeCycle {
            private const val version = "2.3.0"

            const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val lifecycleCompiler = "androidx.lifecycle:lifecycle-common-java8:$version"
            const val savedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$version"
        }
    }

    object External {
        object ReactiveX {
            const val rxJava = "io.reactivex.rxjava3:rxjava:3.0.11"
            const val rxAndroid = "io.reactivex.rxjava3:rxandroid:3.0.0"
            const val rxKotlin = "io.reactivex.rxjava3:rxkotlin:3.0.1"
        }

        object Dagger {
            private const val version = "2.33"

            const val daggerLibrary = "com.google.dagger:dagger:$version"
            const val daggerCompiler = "com.google.dagger:dagger-compiler:$version"
        }

        object AssistedInject {
            private const val version = "0.7.0"

            const val assistedInject =
                "com.squareup.inject:assisted-inject-annotations-dagger2:$version"
            const val assistedInjectCompiler =
                "com.squareup.inject:assisted-inject-processor-dagger2:$version"
        }

        object Retrofit {
            private const val version = "2.9.0"

            const val retrofit = "com.squareup.retrofit2:retrofit:$version"
            const val retrofitAdapter = "com.squareup.retrofit2:adapter-rxjava3:$version"
            const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:$version"
        }

        object Moshi {
            private const val version = "1.11.0"

            const val moshi = "com.squareup.moshi:moshi:$version"
            const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:$version"
        }

        object Timber {
            private const val version = "4.7.1"

            const val timber = "com.jakewharton.timber:timber:$version"
        }
    }
}

fun DependencyHandler.androidX() {
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.materialComponents)
}

fun DependencyHandler.navigation() {
    implementation(Dependencies.AndroidX.Navigation.fragment)
    implementation(Dependencies.AndroidX.Navigation.ui)
}

fun DependencyHandler.assistedInject() {
    implementation(Dependencies.External.AssistedInject.assistedInject)
    kapt(Dependencies.External.AssistedInject.assistedInjectCompiler)
}

fun DependencyHandler.dagger() {
    implementation(Dependencies.External.Dagger.daggerLibrary)
    kapt(Dependencies.External.Dagger.daggerCompiler)
}

fun DependencyHandler.rxJava() {
    implementation(Dependencies.External.ReactiveX.rxJava)
    implementation(Dependencies.External.ReactiveX.rxKotlin)
}

fun DependencyHandler.rxAndroid() {
    implementation(Dependencies.External.ReactiveX.rxAndroid)
}

fun DependencyHandler.networking() {
    implementation(Dependencies.External.Retrofit.retrofit)
    implementation(Dependencies.External.Retrofit.retrofitAdapter)
    implementation(Dependencies.External.Retrofit.retrofitMoshiConverter)
    implementation(Dependencies.External.Moshi.moshi)
}

fun DependencyHandler.lifecycle() {
    implementation(Dependencies.AndroidX.LifeCycle.lifecycleViewModel)
    implementation(Dependencies.AndroidX.LifeCycle.lifecycleLiveData)
    implementation(Dependencies.AndroidX.LifeCycle.lifecycleCompiler)
    implementation(Dependencies.AndroidX.LifeCycle.savedState)
}

private fun DependencyHandler.implementation(dependency: Dependency) {
    add("implementation", dependency)
}

private fun DependencyHandler.implementation(depName: String) {
    add("implementation", depName)
}

private fun DependencyHandler.kapt(depName: String) {
    add("kapt", depName)
}
