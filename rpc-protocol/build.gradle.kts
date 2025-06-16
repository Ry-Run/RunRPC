plugins {
    id("java-common-conventions")
    alias(libs.plugins.protobuf.plugin)
}

dependencies {
    "implementation"(libs.protobuf.core)
}

/*
* 配置 protobuf 插件
* Protobuf 插件假定 .proto 与 Java 源文件相同，均位于源集（sourceSets）中（如 main/test）
* 一个源集的所有 .proto 文件会被一次性编译，生成的 java 文件又作为该源集的 Java 编译任务的输入
* 该插件会为每个源集（sourceSet）新增名为 proto 的源码块（与 java 块同级），目录结构镜像：src/main/java ↔ src/main/proto
* 默认情况下，它会自动包含 src/$sourceSetName/proto 目录下的所有 *.proto 文件。
* 可按照配置 Java 源码的相同方式自定义此设置。
* */
sourceSets {
    main {  // 主源集
        proto {  // Protobuf 配置块
            // 下面三个 srcDir 会被一起编译
            srcDir 'src/main/proto' // 默认目录
            srcDir 'src/main/protobuf'         // 添加目录1
            srcDir 'src/main/protocolbuffers'  // 添加目录2

            // 默认使用 .proto 后缀，不要使用它以外的扩展名，原因：系统仅能通过扩展名识别文件类型
            include '**/*.protodevel'          // 包含非标准扩展名文件，危险操作！
        }
        java {  // Java 源码块
            ...   // 标准 Java 配置
        }
    }

    test {   // 测试源集
        proto {
            // 在默认目录 'src/test/proto' 基础上添加
            srcDir 'src/test/protocolbuffers'  // 测试专用 Proto 目录
        }
    }
}
