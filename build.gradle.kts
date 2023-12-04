import org.gradle.api.tasks.testing.logging.TestExceptionFormat

val junit4Version by extra("4.13.2")

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
    }
}

plugins {
    id("java")
}

allprojects {
    repositories {
        mavenCentral()
        flatDir {
            dirs("${rootDir}/libs")
        }
    }
}

subprojects {
    apply(plugin = "java")

    version = "1.0"
    group = "code"

    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    dependencies {
        implementation("org.jetbrains:annotations:16.0.2")
        testImplementation(group = "junit", name = "junit", version = junit4Version)
    }

    tasks {
        compileJava {
            options.encoding = "UTF-8"
        }
        compileTestJava {
            options.encoding = "UTF-8"
        }
        test {
            jvmArgs = listOf(
                "-Dfile.encoding=UTF8",
                "-Dline.separator=\n", "-ea",
                // https://stackoverflow.com/a/54280095/9980245
                "-Dhttps.protocols=TLSv1,TLSv1.1,TLSv1.2"
            )
            testLogging.showStandardStreams = true
            testLogging.exceptionFormat = TestExceptionFormat.FULL
        }
    }
}