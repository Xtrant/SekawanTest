plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.sekawantest"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sekawantest"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    //noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    //noinspection UseTomlInstead
    implementation("com.github.bumptech.glide:glide:4.16.0")
    //noinspection UseTomlInstead
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    //noinspection UseTomlInstead
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    //noinspection UseTomlInstead
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    //noinspection UseTomlInstead
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    //noinspection UseTomlInstead
    implementation("androidx.room:room-runtime:2.6.1")
    //noinspection UseTomlInstead
    ksp("androidx.room:room-compiler:2.6.1")
    //noinspection UseTomlInstead
    implementation("androidx.datastore:datastore-preferences:1.1.0")
    //noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    //noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    //noinspection UseTomlInstead
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    //noinspection UseTomlInstead
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    //noinspection UseTomlInstead
    androidTestImplementation("androidx.test:runner:1.5.2")
    //noinspection UseTomlInstead
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //noinspection UseTomlInstead
    androidTestImplementation("androidx.test:rules:1.5.0")
    //noinspection UseTomlInstead
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
}