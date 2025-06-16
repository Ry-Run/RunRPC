# Protobuf 工程实践

## 命名标准

标准文件格式

- 行长度保持为 80 个字符。
- 使用 2 个空格的缩进。
- 对字符串使用双引号。

命名规范：文件名小写+下换线连接：`lower_snake_case.proto`

文件结构

1. 许可证头（如果有）
2. 文件概述
3. Syntax
4. Package
5. Imports
6. 文件选项
7. 数据结构

标识符命名规范

- 驼峰，只包含字母和数字
- 支持多种风格...
- 不要以下划线做首字母或结尾

包名：以点分割，全小写

Message 名：TitleCase，首字母大写的驼峰

字段名：snake_case，对重复字段使用复数名称：`string song_name = 1;`

枚举名：TitleCase，列出的第一个值应为零值枚举，并且后缀为 `_UNSPECIFIED` 或 `_UNKNOWN`。此值可用作未知/默认值 并且应该与您期望的任何语义值不同 显式设置

不允许在两个同级枚举中使用相同的名称

```protobuf
enum CollectionType {
  COLLECTION_TYPE_UNSPECIFIED = 0;
  SET = 1;
  MAP = 2;
}

enum TennisVictoryType {
  TENNIS_VICTORY_TYPE_UNSPECIFIED = 0;
  GAME = 1;
  SET = 2; // 与上面的 set 重名，建议前面加上枚举名称 Tennis_Victory_Type_SET
}
```

service：服务名称和方法名称使用 TitleCase

```protobuf
service FooService {
  rpc GetSomething(GetSomethingRequest) returns (GetSomethingResponse);
  rpc ListSomething(ListSomethingRequest) returns (ListSomethingResponse);
}
```

有关更多与服务相关的指南，请参阅 [为每个方法创建唯一的 Protos](https://protobuf.dev/best-practices/api#unique-protos) 和 [不要在顶级请求或响应 Proto 中包含原始类型](https://protobuf.dev/programming-guides/api#dont-include-primitive-types) 在 API 最佳实践主题中，以及 [在单独的文件中定义消息类型](https://protobuf.dev/best-practices/dos-donts#separate-files) 在 Proto 最佳实践中。

---------

## 工程实践

### 编译工程 proto 文件

Protobuf Plugin 做了两个事：

- 组织 Protobuf 编译器命令，生成 java 文件
- 将生成的 java 源文件添加到 java 的编译单元，一起编译
  - 注意，需要生成非 Java/Kotlin 源文件，它们不会自动包含在编译中，需要将它们添加到特定语言的源文件中。查看 [默认输出](https://github.com/google/protobuf-gradle-plugin#default-outputs)

添加插件到工程

```kotlin
// libs.versions.toml
[versions]
protobuf-plugin = "0.9.5"

[plugins]
# 编译项目的 protobuf，插件用 Gradle 插件 ID，而不是 Maven 坐标（是依赖管理使用）
protobuf-plugin = { id = "com.google.protobuf", version.ref = "protobuf-plugin" }

// build.gradle.kts
plugins {
    // 引入插件
    alias(libs.plugins.protobuf.plugin)
}


```











## 参考文档

- [Language Guide ](https://protobuf.dev/programming-guides/proto3/)（v3.21+语法）
- [Encoding ](https://protobuf.dev/programming-guides/encoding/)（二进制序列化原理）
- [Best Practices ](https://protobuf.dev/programming-guides/style/?spm=a2ty_o01.29997173.0.0.4c39c921uSCLap)（命名规范与设计模式）
- [Protobuf Plugin for Gradle](https://plugins.gradle.org/plugin/com.google.protobuf)（Google 官方维护的 Gradle 插件）
- [Java 指南](https://protobuf.dev/reference/java/)
- [Kotlin 指南](https://protobuf.dev/reference/kotlin/)