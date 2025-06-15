plugins {
    id("java-common-conventions")
}

dependencies {
    "implementation"(libs.protostuff.core)
    "implementation"(project(":rpc-common"))
}