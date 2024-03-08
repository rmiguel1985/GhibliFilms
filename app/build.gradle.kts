import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import io.gitlab.arturbosch.detekt.Detekt
import java.util.Locale

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
    alias(libs.plugins.coveralls)
    jacoco
}

android {
    namespace = "com.example.ghiblifilms"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ghiblifilms"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable = true
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true

            val key: String = gradleLocalProperties(rootDir).getProperty("YOUTUBE_API_KEY") ?: ""
            buildConfigField("String", "YOUTUBE_API_KEY", "\"$key\"")
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
    kotlinOptions {
        jvmTarget = "19"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    packaging {
        jniLibs { useLegacyPackaging = true }
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/LICENSE"
            excludes += "META-INF/LICENSE.txt"
            excludes += "META-INF/license.txt"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/NOTICE"
            excludes += "META-INF/NOTICE.txt"
            excludes += "META-INF/notice.txt"
            excludes += "META-INF/ASL2.0"
            excludes += "META-INF/*.kotlin_module"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
    tasks.withType<Test> {
        useJUnitPlatform() // Make all tests use JUnit 5
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.viewmodel)
    implementation(libs.startup)

    // UI
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.compose.navigation)

    // Youtube Player
    implementation(libs.youtube)

    // Coil
    implementation(libs.coil)

    // Koin
    implementation(libs.koin.core)
    implementation(libs.koin.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.interceptor)
    implementation(libs.serialization)
    implementation(libs.retrofit.serialization)

    //Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Debug
    implementation(libs.timber)
    debugImplementation(libs.leak.canary)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    // Test
    testImplementation(platform(libs.junit))
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testImplementation(libs.mockk.unit.test.android)
    testImplementation(libs.mockk.unit.test.agent)
    testImplementation(libs.coroutines.core.test)
    testImplementation(libs.arch.core.test)
    testImplementation(libs.turbine)
    testImplementation(libs.mock.web.server)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    androidTestImplementation(libs.mockk.unit.test.android)
    androidTestImplementation(libs.mockk.unit.test.agent)
    androidTestImplementation(libs.koin.android.test)
    androidTestImplementation(libs.mock.web.server)
    androidTestImplementation(libs.coroutines.core.test)
    androidTestImplementation(libs.arch.core.test)
}

detekt {
    config.setFrom("$rootDir/default-detekt-config.yml")
    //config = files("$rootDir/default-detekt-config.yml")
    autoCorrect = false
}

tasks.withType<Detekt>().configureEach {
    reports {
        html.required.set(true)
        html.outputLocation.set(file("${buildDir}/reports/detekt/detekt-report.html"))
        xml.required.set(false)
        txt.required.set(false)
        sarif.required.set(false)
        md.required.set(false)
    }
}

coveralls {
    jacocoReportPath = "${buildDir}/reports/jacoco/jacocoTestReport/jacocoTestReport.xml"
}

val exclusions = listOf(
    "**/R.class",
    "**/R\$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "android/**/*.*",
    "**/*$[0-9].*",
    "**/*Test*.*",
    "**/*Module*.*",
    "**/theme/**",
    "**/*Helper*.*",
    "**/*Service*.*",
    "**/*Model*.*",
    "**/*Mapper*.*",
    "**/*lambda*.*",
    "**/navigation/**",
    "**/common/**",
    "**/model/**",
    "**/entities/**",
    "**/MainAppBar*.*",
    "**/ComposableSingletons*.*"
)

tasks.withType(Test::class) {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

android {
    applicationVariants.all(closureOf<com.android.build.gradle.internal.api.BaseVariantImpl> {
        val variant = this@closureOf.name.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }

        val unitTests = "test${variant}UnitTest"
        val androidTests = "connected${variant}AndroidTest"

        tasks.register<JacocoReport>("Jacoco${variant}CodeCoverage") {
            dependsOn(listOf(unitTests, androidTests))
            group = "Reporting"
            description = "Execute ui and unit tests, generate and combine Jacoco coverage report"
            reports {
                xml.required.set(true)
                html.required.set(true)
            }
            sourceDirectories.setFrom(layout.projectDirectory.dir("src/main"))
            classDirectories.setFrom(files(
                fileTree(layout.buildDirectory.dir("intermediates/javac/")) {
                    exclude(exclusions)
                },
                fileTree(layout.buildDirectory.dir("tmp/kotlin-classes/")) {
                    exclude(exclusions)
                }
            ))
            executionData.setFrom(files(
                fileTree(layout.buildDirectory) { include(listOf("**/*.exec", "**/*.ec")) }
            ))
        }
    })
}