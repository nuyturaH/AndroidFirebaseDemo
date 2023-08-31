object Libs {

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.6.1"
        const val coreKtx = "androidx.core:core-ktx:1.10.1"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.4"


        object Test {
            const val junit = "androidx.test.ext:junit:1.1.5"
            const val espressoCore = "androidx.test.espresso:espresso-core:3.5.1"
        }
    }

    object Material {
        const val material = "com.google.android.material:material:1.9.0"
    }

    object Dagger {
        const val daggerVersion = "2.44.2"
        const val hiltAndroid = "com.google.dagger:hilt-android:$daggerVersion"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$daggerVersion"
    }


    object Test {
        const val junit = "junit:junit:4.13.2"
    }
}