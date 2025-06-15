plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    // Spotless 插件依赖
    implementation("com.diffplug.spotless:spotless-plugin-gradle:6.25.0")
}
