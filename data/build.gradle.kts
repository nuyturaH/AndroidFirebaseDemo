plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.harutyun.data"
    compileSdk = Versions.Build.compileSdk

    defaultConfig {
        minSdk = Versions.Build.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(project(":domain"))

    implementation(Libs.AndroidX.coreKtx)

    // Firebase
    implementation(platform(Libs.Firebase.firebaseBom))
    implementation(Libs.Firebase.firebaseAnalytics)
    implementation(Libs.Firebase.firebaseAuth)
    implementation(Libs.Firebase.firebaseFirestore)

    // Room
    annotationProcessor(Libs.AndroidX.Room.roomCompiler)
    implementation(Libs.AndroidX.Room.roomKtx)
    ksp(Libs.AndroidX.Room.roomCompiler)

    // Test
    androidTestImplementation(Libs.AndroidX.Test.junit)
    testImplementation(Libs.Test.junit)
}