plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.havila.tv"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.havila.tv"
        minSdk = 23
        targetSdk = 36
        versionCode = 2
        versionName = "1.1"
    }

    buildFeatures { compose = true }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions { jvmTarget = "17" }

    buildTypes {
        release { isMinifyEnabled = false }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.activity:activity-compose:1.11.0")
    implementation("androidx.compose.ui:ui:1.9.3")
    implementation("androidx.compose.foundation:foundation:1.9.3")
    implementation("androidx.compose.material3:material3:1.4.0")
    implementation("androidx.tv:tv-material:1.0.0")
    implementation("androidx.media3:media3-exoplayer:1.8.0")
    implementation("androidx.media3:media3-ui:1.8.0")
    implementation("io.coil-kt:coil-compose:2.7.0")
}
