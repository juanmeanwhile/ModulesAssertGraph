plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.module.assertion)
}

android {
    namespace = "com.meanwhile.modulesassertgraph"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.meanwhile.modulesassertgraph"
        minSdk = 28
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    // Module regex
    val feature = ":feature:[a-z]*"
    val featureDomain = ":feature:.*:domain"
    val featureData = ":feature:.*:data"
    val ui = ":ui:[a-z]*"
    val notMappedModule = "(?!.*(:feature|:ui)).*"

    moduleGraphAssert {
        maxHeight = 4
        allowed = arrayOf(
            ":app -> .*",
            "$notMappedModule -> $feature",
            "$notMappedModule -> $ui",
            "$notMappedModule -> $notMappedModule",
            "$feature -> $featureDomain",
            "$feature -> $featureData",
            "$feature -> $notMappedModule",
            "$feature -> $ui",
            "$featureDomain -> $notMappedModule",
            "$featureData -> $notMappedModule",
            "$featureData -> $featureDomain",
            "$ui -> $ui",
            "$ui -> $notMappedModule",
            allowException(":feature:search", dependsOn = ":feature:exception"),
        )
        restricted = arrayOf(

        )
        configurations = setOf("api", "implementation") // Dependency configurations to look. ['api', 'implementation'] is the default
        assertOnAnyBuild = true // true value will run the assertions as part of any build without need to run the assert* tasks, false is default
    }
}

fun allowException(
    module: String,
    dependsOn: String,
): String =
    StringBuffer()
        .apply {
            append("(")
            append(module)
            append(").*")
            append(" -> ")
            append(dependsOn)
        }.toString()

dependencies {
    implementation(project(":analytics"))
    implementation(project(":feature:checkout"))
    implementation(project(":feature:search"))
    implementation(project(":ui:product"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
