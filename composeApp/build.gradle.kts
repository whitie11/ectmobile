import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)

//    kotlin("plugin.serialization")
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            // Koin support for Android
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

            implementation(libs.ktor.client.okhttp)
        }

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)


            implementation(libs.navigation.compose)
            implementation(libs.kotlinx.serialization)
            implementation(libs.ui.backhandler)

            implementation(libs.kotlinx.datetime)

            // Koin
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.composeVM)

            // ViewModel support in common code
            implementation(libs.androidx.lifecycle.viewmodel)

            implementation(libs.ktor.core)

            implementation(libs.ktor.serialization)
            implementation(libs.ktor.content.negotiation)

            implementation(libs.ktor.client.auth)
          


        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "net.whiteapps.ectmobileapp"
    compileSdkVersion(libs.versions.android.compileSdk.get().toInt())

    defaultConfig {
        applicationId = "net.whiteapps.ectmobileapp"
        minSdk = 35
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

