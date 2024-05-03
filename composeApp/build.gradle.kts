import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

val bundleName = "com.xinkev.a3"
val bundleVersion = "1.0.0"
val buildNumber = 1
val appName = "a3"

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlinParcelise)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.ksp)
    alias(libs.plugins.buildconfig)
}

kotlin {
//    wasmJs {
//        moduleName = "a3"
//        browser {
//            commonWebpackConfig {
//                outputFileName = "composeApp.js"
//                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
//                    static = (static ?: mutableListOf()).apply {
//                        // Serve sources to debug inside browser
//                        add(project.projectDir.path)
//                    }
//                }
//            }
//        }
//        binaries.executable()
//    }
    
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    jvm("desktop")
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        val desktopMain by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
//        val wasmJsMain by getting

        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)

                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.coroutines.core)

                implementation(libs.bundles.voyager)

                implementation(libs.koin.core)
                implementation(libs.koin.compose)

                // KV storage
                implementation(libs.mpSettings.noarg)
                implementation(libs.mpSettings.coroutines)
            }
        }

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.kotlinx.coroutines.android)

            implementation(libs.sqldelight.android)

            implementation(libs.koin.android)
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)

            implementation(libs.kotlinx.coroutines.desktop)

            implementation(libs.sqldelight.jvm)
        }

//        wasmJsMain.dependencies {
//            implementation(libs.kotlinx.coroutines.core.wasm)
//        }

        iosMain.dependencies {
            implementation(libs.sqldelight.native)
        }

        all {
            dependencies {
                implementation(project.dependencies.platform(libs.koin.bom))
            }

            languageSettings {
                @OptIn(ExperimentalKotlinGradlePluginApi::class)
                compilerOptions{
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }
            }
        }
    }
}

android {
    namespace = bundleName
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = bundleName
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = buildNumber
        versionName = bundleVersion
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
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = bundleName
            packageVersion = bundleVersion
        }
    }
}

compose.experimental {
    web.application {}
}

sqldelight {
    databases {
        create("A3Database") {
            packageName.set("${bundleName}.sqldelight")
        }
    }
}

buildConfig {
    buildConfigField("appVersion", bundleVersion)
    buildConfigField("isDebug", true)
    buildConfigField("appName", appName)
    buildConfigField("dbName", "$appName.db")
}