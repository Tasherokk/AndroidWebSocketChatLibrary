plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])

                groupId = "io.github.tasherokk"
                artifactId = "androidwebsocketchatlibrary"
                version = "1.0.4"

                pom {
                    name.set("Android WebSocket Chat Library")
                    description.set("Simple WebSocket chat library using wss://echo.websocket.org.")
                    url.set("https://github.com/Tasherokk/AndroidWebSocketChatLibrary")
                }
            }
        }

        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/Tasherokk/AndroidWebSocketChatLibrary")
                credentials {
                    username = "Tasherokk"
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
    }
}

android {
    namespace = "com.example.androidwebsocketchatlibrary"
    compileSdk = 35

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.okhttp)
    implementation(libs.okhttp)
    implementation(libs.androidx.recyclerview)
    implementation(libs.material.v190)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}