plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id ("com.google.dagger.hilt.android")
    id ("kotlin-kapt")
    id ("kotlin-parcelize")
    id ("androidx.navigation.safeargs.kotlin")
    id ("com.google.gms.google-services")
}

android {
    namespace = "com.example.newbookshelf"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.newbookshelf"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", project.properties["BASE_URL"].toString())
        buildConfigField("String", "ALADIN_BASE_URL", project.properties["ALADIN_BASE_URL"].toString())
        buildConfigField("String", "TTB_KEY", project.properties["TTB_KEY"].toString())
        buildConfigField("String", "GOOGLE_CLIENT_ID", project.properties["GOOGLE_CLIENT_ID"].toString())
        buildConfigField("String", "NAVER_CLIENT_ID", project.properties["NAVER_CLIENT_ID"].toString())
        buildConfigField("String", "NAVER_CLIENT_SECRET", project.properties["NAVER_CLIENT_SECRET"].toString())
        buildConfigField("String", "KAKAO_NATIVE_KEY", project.properties["KAKAO_NATIVE_KEY"].toString())
        buildConfigField("String", "KAKAO_BASE_URL", project.properties["KAKAO_BASE_URL"].toString())
        buildConfigField("String", "KAKAO_REST_API_KEY", project.properties["KAKAO_REST_API_KEY"].toString())
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    androidTestImplementation(libs.androidx.core.testing)

    //Room
    val room_version = "2.6.1" // 최신 버전 사용
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt("androidx.room:room-compiler:$room_version")

    //Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.9.0")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.launchdarkly:okhttp-eventsource:4.1.0")

    //Hilt
    val hilt_version = "2.52"
    implementation("com.google.dagger:hilt-android:$hilt_version")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    kapt("com.google.dagger:hilt-compiler:$hilt_version")
    kapt("androidx.hilt:hilt-compiler:1.2.0")

    //Navigation
    val nav_version = "2.8.1"
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")

    //기본적인 Test 종속성
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
    testImplementation("org.robolectric:robolectric:4.4")

    //optional - Test helpers for LiveData
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    //optional - Test helpers for Lifecycle runtime
    testImplementation ("androidx.lifecycle:lifecycle-runtime-testing:2.8.7")
    testImplementation ("com.google.truth:truth:1.1.4")
    testImplementation ("com.google.truth.extensions:truth-java8-extension:1.1.3")

    //optional - Mockito
    testImplementation("org.mockito:mockito-core:3.12.4")

    //optional - MockWebServer
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    //AndroidTest
    androidTestImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.2")
    androidTestImplementation("com.google.truth:truth:1.1.4")
    androidTestImplementation("com.google.truth.extensions:truth-java8-extension:1.1.3")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    kapt("com.github.bumptech.glide:compiler:4.16.0")

    //GoogleMap
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    implementation("com.google.maps.android:android-maps-utils:3.8.2")
    implementation("com.google.maps:google-maps-services:2.2.0")

    //권한
    implementation("io.github.ParkSangGwon:tedpermission-normal:3.4.2")

    //Firebase + 구글로그인
    implementation(platform("com.google.firebase:firebase-bom:33.6.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("org.java-websocket:Java-WebSocket:1.5.7")
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("com.google.firebase:firebase-auth-ktx:23.1.0")

    //카카오 로그인
    implementation("com.kakao.sdk:v2-all:2.17.0")
    implementation("com.kakao.maps.open:android:2.6.0")

    //네이버 로그인
    implementation("com.navercorp.nid:oauth:5.10.0")

    //Lottie
    implementation("com.airbnb.android:lottie:6.5.2")

    //Socket
    implementation("io.socket:socket.io-client:2.1.0") {
        exclude(group = "org.json", module = "json")
    }

    //Paging 라이브러리
    implementation("androidx.paging:paging-runtime:3.2.0")
    implementation("androidx.paging:paging-common:3.2.0")
}