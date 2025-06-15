plugins {
    id("com.diffplug.spotless")
}

repositories {
    mavenCentral()
}

// 子项目公共依赖
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect") version "1.9.25"
    implementation("org.jetbrains.kotlin:kotlin-test-junit5") version "1.9.25"
    implementation("org.junit.jupiter:junit-jupiter") version "5.10.0"
    implementation("org.junit.platform:junit-platform-launcher") version "5.10.0"
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
