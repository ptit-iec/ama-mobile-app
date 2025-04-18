plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "1.9.0"
}

kapt {
    correctErrorTypes = true
}
android {
    namespace = "com.iec.makeup"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.iec.makeup"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.play.services.location)
    implementation(libs.play.services.mlkit.document.scanner)
    implementation(libs.firebase.messaging.ktx)
    implementation(libs.androidx.constraintlayout.v221)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.coordinatorlayout)
    implementation(libs.androidx.cardview)
    implementation(libs.play.services.vision.common)
    implementation(libs.androidx.paging.common.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.androidx.constraintlayout.compose.android)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.accompanist.permissions)
    // Use this for both Android and JVM
    implementation(libs.qrcode.kotlin)
// Hilt for ViewModel
    implementation(libs.androidx.fragment.ktx)
    // Lifecycle ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx.v261)
    // Retrofit
    implementation(libs.retrofit.v290)
    implementation(libs.converter.gson.v290)

    // Hilt
    implementation(libs.hilt.android.v250)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.pusher.java.client)


    // Ktor, later implement for web-sockets
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.websockets)
    implementation(libs.ktor.client.logging)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Datastore
    implementation(libs.androidx.datastore.preferences)

    //Biometric
    implementation(libs.androidx.biometric)

    // Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    implementation(libs.androidx.material)

    // Gemini SDK
    implementation(libs.generativeai)
    // Room annotation processor
    implementation(libs.androidx.material.icons.extended)

    // Retrofit
    implementation(libs.retrofit2.retrofit)
    implementation(libs.retrofit2.converter.gson)
    // OkHttp (recommended)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // If using Kotlin Serialization, add the plugin
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.coil.kt.coil.compose)
    implementation(libs.socket.io.client)

    // Google SDK for authentication
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
//    implementation(libs.googleid)

    // Browser
    implementation(libs.androidx.browser)

}