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
    implementation(libs.bundles.ui)
    implementation(libs.bundles.bd)
    implementation(libs.bundles.firebase)
    ksp(libs.bundles.ksp)
    testImplementation(libs.bundles.testImplementation)
    androidTestImplementation(libs.bundles.testAndroidImplementation)
}