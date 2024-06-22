plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "it.thomas.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "it.thomas.myapplication"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)

    /*
   Scaleable unit
    */
    val inituiVersion = "1.1.1"
    implementation("com.intuit.ssp:ssp-android:$inituiVersion") // font-size = sp
    implementation("com.intuit.sdp:sdp-android:$inituiVersion") //dp -> sdp

    /*
    Exoplayer
     */
    val mediaVersion = "1.0.1"
    implementation("androidx.media3:media3-exoplayer:$mediaVersion")
    implementation("androidx.media3:media3-ui:$mediaVersion")
    implementation("androidx.media3:media3-exoplayer-dash:$mediaVersion")

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}