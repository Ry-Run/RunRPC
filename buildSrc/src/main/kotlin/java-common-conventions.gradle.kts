import pers.lr.runrpc.libs

plugins {
    id("com.diffplug.spotless")
    id("java-library")
}

repositories {
    mavenCentral()
}

// 子项目公共依赖
dependencies {
    "implementation"(libs.findLibrary("kotlin.reflect").get())
    "implementation"(libs.findLibrary("kotlin.test.junit5").get())
    "implementation"(libs.findLibrary("junit.jupiter").get())
    "implementation"(libs.findLibrary("junit.platform.launcher").get())
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

spotless {
    kotlin {
        target("**/*.kt")
        ktlint() // 使用 Ktlint 规则
        trimTrailingWhitespace()
        endWithNewline()
    }

    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }

    format("misc") {
        target("*.md", ".gitignore", "**/*.yml", "**/*.yaml")
        trimTrailingWhitespace()
        endWithNewline()
    }
}
