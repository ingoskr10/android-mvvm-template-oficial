plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.devtools)
}

android {
    namespace = "com.ingoskr.template_oficial"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ingoskr.template_oficial"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
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

    flavorDimensions += "environment"
    productFlavors {
        create("dev") {
            dimension = "environment"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            buildConfigField("String", "BASE_URL", "\"https://api.dev.com\"")
            buildConfigField("String", "API_KEY", "\"dev_api_key\"")
        }
        create("qa") {
            dimension = "environment"
            applicationIdSuffix = ".qa"
            versionNameSuffix = "-qa"
            buildConfigField("String", "BASE_URL", "\"https://api.test.com\"")
            buildConfigField("String", "API_KEY", "\"test_api_key\"")
        }
        create("prod") {
            dimension = "environment"
            buildConfigField("String", "BASE_URL", "\"https://api.production.com\"")
            buildConfigField("String", "API_KEY", "\"prod_api_key\"")
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
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.github.bumptech.glide)
    implementation(libs.androidx.multidex)
    implementation(libs.volley)
    implementation(libs.gson)
    implementation(libs.androidx.room.runtime)
    implementation(libs.firebase.crashlytics.buildtools)
    ksp(libs.androidx.room.compiler)
    implementation(libs.google.firebase.auth)
    implementation(libs.google.firebase.firestore)
    implementation(libs.google.android.gms)
    implementation(libs.insert.koin)
    implementation(libs.google.firebase.remote)
    implementation(libs.greenrobot.eventBus)
    implementation(libs.androidx.room)


    //testImplementation
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //mockk
    testImplementation(libs.mockk)
}