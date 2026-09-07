plugins {
    id("com.android.application") version "7.0.4" apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("com.github.recloudstream:gradle:master-SNAPSHOT")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

fun Project.cloudstream(configuration: com.lagradost.cloudstream3.gradle.CloudstreamExtension.() -> Unit) = extensions.getByName<com.lagradost.cloudstream3.gradle.CloudstreamExtension>("cloudstream").configuration()

fun Project.android(configuration: com.android.build.gradle.BaseExtension.() -> Unit) = extensions.getByName<com.android.build.gradle.BaseExtension>("android").configuration()

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "org.jetbrains.kotlin.android")
    apply(plugin = "com.lagradost.cloudstream3.gradle")

    cloudstream {
        setRepo(System.getenv("GITHUB_REPOSITORY") ?: "https://github.com/user/repo")
    }

    android {
        compileSdkVersion(30)

        defaultConfig {
            minSdk = 21
            targetSdk = 30
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs = freeCompilerArgs +                         "-Xno-call-assertions" +                         "-Xno-param-assertions" +                         "-Xno-receiver-assertions"
            }
        }
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib:1.7.10")
        implementation("com.github.Blatzar:NiceHttp:0.3.2")
        implementation("org.jsoup:jsoup:1.13.1")
    }
}
