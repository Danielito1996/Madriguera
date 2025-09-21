import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlinSerialization)
}

// Leer local.properties
val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(FileInputStream(localPropertiesFile))
    }
}

android {
    namespace = "com.elitec.madriguera"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.elitec.madriguera"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "APPWRITE_PROJECT_ID", "\"${localProperties.getProperty("APPWRITE_PROJECT_ID")}\"")
            buildConfigField("String", "APPWRITE_PUBLIC_ENDPOINT", "\"${localProperties.getProperty("APPWRITE_PUBLIC_ENDPOINT")}\"")
        }
        release {
            buildConfigField("String", "APPWRITE_PROJECT_ID", "\"${localProperties.getProperty("APPWRITE_PROJECT_ID")}\"")
            buildConfigField("String", "APPWRITE_PUBLIC_ENDPOINT", "\"${localProperties.getProperty("APPWRITE_PUBLIC_ENDPOINT")}\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true // Asegura que BuildConfig se genere
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
    implementation(libs.androidx.ui.text)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // Di
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.compose.navigation)
    // AppWrite
    implementation(libs.appwrite)
    // Ktor Client
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.logs)
    // Fluent UI
    implementation(libs.fluent.ui)
    // Iconos
    implementation(libs.androidx.icons.extended)
    // Logs
    implementation(libs.ktor.loggin.nappier)
    // Datetime
    implementation(libs.kotlinx.datetime)
    // lotties
    implementation(libs.lotties.compose)
    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    // Components
    implementation(libs.composables.components)
}