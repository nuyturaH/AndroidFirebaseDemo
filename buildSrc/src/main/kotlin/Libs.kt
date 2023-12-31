object Libs {

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.6.1"
        const val coreKtx = "androidx.core:core-ktx:1.10.1"
        const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.4"

        object Lifecycle {
            private const val lifecycleVersion = "2.6.1"
            const val lifecycleLivedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
            const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
        }

        object Navigation {
            private const val navigationVersion = "2.7.1"
            const val navigationUiKtx = "androidx.navigation:navigation-ui-ktx:$navigationVersion"
            const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
            const val navigationSafeArgsGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion"
        }

        object Room {
            private const val roomVersion = "2.5.2"
            const val roomCompiler = "androidx.room:room-compiler:$roomVersion"
            const val roomKtx = "androidx.room:room-ktx:$roomVersion"
        }

        object Legacy {
            const val legacySupportV4 = "androidx.legacy:legacy-support-v4:1.0.0"
        }

        object Test {
            const val junit = "androidx.test.ext:junit:1.1.5"
            const val espressoCore = "androidx.test.espresso:espresso-core:3.5.1"
        }
    }

    object Material {
        const val material = "com.google.android.material:material:1.9.0"
    }

    object Dagger {
        const val daggerVersion = "2.48"
        const val hiltAndroid = "com.google.dagger:hilt-android:$daggerVersion"
        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$daggerVersion"
    }

    object Firebase {
        const val firebaseBom = "com.google.firebase:firebase-bom:32.2.3"
        const val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx"
        const val firebaseAuth = "com.google.firebase:firebase-auth-ktx"
        const val firebaseFirestore = "com.google.firebase:firebase-firestore-ktx"

    }


    object Test {
        const val junit = "junit:junit:4.13.2"
    }
}