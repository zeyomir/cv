private const val kotlinVersion = "1.3.72"

object BuildDependencies {
    private object Versions {
        const val kotlinGradlePlugin = kotlinVersion
        const val androidGradlePlugin = "4.0.1"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinGradlePlugin}"
}

object Plugins {
    val android = Android

    object Android {
        const val androidApplication = "com.android.application"
        const val kotlinAndroid = "kotlin-android"
        const val kotlinAndroidExtensions = "kotlin-android-extensions"
    }

    const val kotlin = "kotlin"
    const val kotlinKapt = "kotlin-kapt"
}

object Apps {
    const val compileSdk = 29
    const val minSdk = 23
    const val targetSdk = compileSdk
    const val versionCode = 1
    const val versionName = "1.0.0"
    const val buildTools = "30.0.0"
}

object Libs {
    private object Versions {
        const val kotlin = kotlinVersion
        const val desugaring = "1.0.10"
        const val timber = "4.7.1"

        const val androidFlexbox = "2.0.1"

        const val rxJava = "2.2.19"
        const val rxAndroid = "2.1.1"

        const val tatarkaBindingCollectionAdapter = "4.0.0"

        const val moshi = "1.9.2"
        const val glide = "4.11.0"

        const val emailIntentBuilder = "2.0.0"
    }

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val desugaring = "com.android.tools:desugar_jdk_libs:${Versions.desugaring}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val androidFlexbox = "com.google.android:flexbox:${Versions.androidFlexbox}"

    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"

    const val bindingCollectionAdapter = "me.tatarka.bindingcollectionadapter2:bindingcollectionadapter:${Versions.tatarkaBindingCollectionAdapter}"
    const val bindingCollectionAdapterRecyclerView = "me.tatarka.bindingcollectionadapter2:bindingcollectionadapter-recyclerview:${Versions.tatarkaBindingCollectionAdapter}"

    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    const val moshiCodeGen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"

    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"

    const val emailIntentBuilder = "de.cketti.mailto:email-intent-builder:${Versions.emailIntentBuilder}"

    val androidX = AndroidX

    object AndroidX {
        private object Versions {
            const val core = "1.2.0-rc01"
            const val appcompat = "1.2.0-alpha01"
            const val constraintLayout = "2.0.0-beta4"
            const val navigation = "2.3.0-alpha04"
            const val swipeRefreshLayout = "1.1.0"
        }

        const val core = "androidx.core:core-ktx:${Versions.core}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        const val navigationUI = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"
    }

    val dagger = Dagger

    object Dagger {
        private const val version = "2.27"

        const val core = "com.google.dagger:dagger:$version"
        const val compiler = "com.google.dagger:dagger-compiler:$version"
    }

    val okHttp = OkHttp

    object OkHttp {
        private object Versions {
            const val okHttp = "4.5.0"
            const val okHttpLoggingInterceptor = "4.5.0"
        }

        const val core = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLoggingInterceptor}"
    }

    val retrofit = Retrofit

    object Retrofit {
        private const val version = "2.8.1"

        const val core = "com.squareup.retrofit2:retrofit:$version"
        const val rxJavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:$version"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:$version"
        const val mock = "com.squareup.retrofit2:retrofit-mock:$version"
    }
}

object TestLibs {
    private object Versions {
        const val junit = "5.6.2"
        const val mockk = "1.9.3"
    }

    const val junit = "org.junit.jupiter:junit-jupiter:${Versions.junit}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
}
