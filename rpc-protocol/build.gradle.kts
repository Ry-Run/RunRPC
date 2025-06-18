plugins {
    id("java-common-conventions")
    alias(libs.plugins.protobuf.plugin)
}

dependencies {
    "implementation"(libs.protobuf.core)
}

// 插件提供 protobuf 块，提供了所有的配置选项
protobuf {
    // 插件会查找系统中的 protoc 的可执行文件，建议直接从存储库下载
    // 插件会自动检测操作系统和架构，下载匹配的二进制文件
    // 配置 protobuf 编译器: protoc
    protoc {
        artifact = libs.protoc.get().toString()
    }

    // 代码生成器配置
    generateProtoTasks {
        all().forEach {
            it.builtins {
                // 生成 Java 代码
                named("java") { option("lite") }
                // 生成 Kotlin 代码
//                named("kotlin") { option("lite") }
//                kotlin {}
            }
        }
    }
}
