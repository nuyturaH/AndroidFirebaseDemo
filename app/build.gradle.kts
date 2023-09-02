plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("androidx.navigation.safeargs.kotlin")
    kotlin("kapt")
}

android {
    namespace = "com.harutyun.androidfirebasedemo"
    compileSdk = Versions.Build.compileSdk

    defaultConfig {
        applicationId = "com.harutyun.androidfirebasedemo"
        minSdk = Versions.Build.minSdk
        targetSdk = Versions.Build.targetSdk
        versionCode = Versions.Build.versionCode
        versionName = Versions.Build.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(Libs.AndroidX.coreKtx)

    // UI
    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.constraintlayout)
    implementation(Libs.Material.material)

    // DI
    implementation(Libs.Dagger.hiltAndroid)
    kapt(Libs.Dagger.hiltAndroidCompiler)

    // Navigation
    implementation(Libs.AndroidX.Navigation.navigationUiKtx)
    implementation(Libs.AndroidX.Navigation.navigationFragmentKtx)

    // Lifecycle
    implementation(Libs.AndroidX.Lifecycle.lifecycleLivedataKtx)
    implementation(Libs.AndroidX.Lifecycle.lifecycleViewModelKtx)

    // Legacy
    implementation(Libs.AndroidX.Legacy.legacySupportV4)

    // Firebase
    implementation(platform(Libs.Firebase.firebaseBom))
    implementation(Libs.Firebase.firebaseAnalytics)
    implementation(Libs.Firebase.firebaseAuth)

    // Test
    testImplementation(Libs.Test.junit)
    androidTestImplementation(Libs.AndroidX.Test.junit)
    androidTestImplementation(Libs.AndroidX.Test.espressoCore)
}