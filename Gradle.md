[官网](https://docs.gradle.org/current/userguide/installation.html#installation)

## 1. 安装

如果项目根目录文件有执行 gradle 构建的脚本：`gradlew`、`gradlew.bat`，并且满足系统[先决条件](https://docs.gradle.org/current/userguide/installation.html#sec:prerequisites)(Java 8 以上)，则可以被 [Gradle Wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html#gradle_wrapper_reference) 直接识别，不需要安装 gradle。

```
Project root
├── gradle
│   └── wrapper 
├── gradlew         
├── gradlew.bat
└── ⋮
```

[升级 gradle 版本](https://docs.gradle.org/current/userguide/upgrading_version_8.html#upgrading_version_8)

可以在 IDE 中安装

确实需要安装gradle在机器上，先确保没有安装gradle：`gradle -v`

安装方式：

- 自动安装：包管理器
- 手动安装： [软件发布页](https://gradle.org/releases?_gl=1*vwfea9*_gcl_au*MTQ2NDQxODkyMi4xNzQ5NjMzNzgz*_ga*MTE5MDE4OTg4OS4xNzQ5NjMzNzg0*_ga_7W7NC6YNPT*czE3NDk2MzM3ODMkbzEkZzEkdDE3NDk2MzQ4OTEkajYwJGwwJGgw)

## 2. Gradle 基础

### 2.1 核心概念

工程

1. 单工程：只包含根工程
2. 多工程：包含一个根工程和多个子工程

构建脚本

1. 构建脚本指导Gradle去构建工程
2. 一个工程包含一个或多个构建脚本

依赖和依赖管理

1. 依赖管理是声明和解析工程所需额外资源的自动化技术
2. 每个工程通常包括一系列依赖，在构建工程时，Gradle会解析它们

任务

1. 任务是编译代码或运行测试用例的基本单元
2. 每个工程都包含一个或多个任务，它们定义在构建脚本或插件中

插件

1. 插件用于拓展gradle的能力
2. 工程的任务可以选择使用插件

#### 2.1.1 项目结构

```java
project
├── gradle   // 存储 wrapper 文件等                       
│   ├── libs.versions.toml  // 存放依赖的版本             
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew  // gradle wrapper 脚本，表明这是一个 gradle 工程                       
├── gradlew.bat // 同上，表明这是一个 gradle 工程                           
├── settings.gradle(.kts)   // 配置文件，定义 gradle 根工程和子工程        
├── subproject-a
│   ├── build.gradle(.kts)   //  子工程构建脚本        
│   └── src                  // 子工程源码       
└── subproject-b
    ├── build.gradle(.kts)   //  子工程构建脚本               
    └── src                  // 子工程源码        
```



#### 2.1.2 调用 gradle

当你在 IDE 中构建、清理、运行 app 时，会自动调用 gradle

通过自己选择的 IDE 的用户手册去学习更多如何使用和配置 Gradle。

只要安装了 gradle，可以通过 `gradle build` 调用 gradle。但是大多数工程不会安装它，而是选择 Gradle Wrapper

wrapper 是一个脚本，它调用了指定版本的 gradle，推荐通过 wrapper 执行 gradle：`./gradlew build`

### 2.2 wrapper 基础

推荐使用 wrapper 执行构建

![gradle basic 2](https://docs.gradle.org/current/userguide/img/gradle-basic-2.png)

wrapper 会调用指定版本的 Gradle，在需要它的时候会进行下载

![wrapper workflow](https://docs.gradle.org/current/userguide/img/wrapper-workflow.png)

项目根目录包含一个 `gradlew` 或 `gradlew.bat`，则 wrapper 是可用的，否则该项目可能不是一个 Gradle 工程，或者 wrapper 尚未安装好。

```
root
├── gradlew     // THE WRAPPER
├── gradlew.bat // THE WRAPPER
└── ...
```

wrapper 不是从互联网下载的，必须使用安装了 Gradle 的机器运行 `gradle wrapper` 来生成。

使用 wrapper 的好处

1. 自动下载和使用指定版本的 Gradle
2. 基于指定版本的 Gradle 标准化工程
3. 针对不同用户、环境、IDE 提供相同的 Gradle
4. 不用手动安装 Gradle，一样轻松运行 Gradle 构建

两种运行 Gradle 的方式：

1. 通过在机器安装 Gradle：运行 gradle 命令
2. 通过使用 Gradle Wrapper：运行 Gradle 工程的 `gradlew` 或`gradlew.bat` 脚本

推荐使用 wrapper 执行构建，wrapper 确保构建的执行是可靠的、可控制的、标准化的

1. 系统安装 Gradle的方式：`gradle build`
2. 使用 wrapper
   - linux 或 mac：`./gradlew build`
   - windows：`gradlew.bat build`

如果想在其他目录运行上面的买了，需要提供相对路径：`../gradlew build`

#### 2.2.1 理解 wrapper

1、2、3、4 都是 wrapper 文件的一部分，你不应该修改这些文件

```
.
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar  // 1.包含 wrapper 代码的小型 jar 包
│       └── gradle-wrapper.properties   // 2.wrapper 的配置文件（定义了下载 Gradle 的URL、使用 zip 还是 tar包等）
├── gradlew // 3.基于 unix 的脚本，是 gradle-wrapper.jar 的包装器
└── gradlew.bat // 4. 同上，但是基于 windows
```

使用以下命令更新 gradle 版本

- ```
  $ gradlew.bat --version
  $ gradlew.bat wrapper --gradle-version 7.2
  ```

- ```
  $ ./gradlew --version
  $ ./gradlew wrapper --gradle-version 7.2
  ```

### 2.3 命令行基础

#### 2.3.1 运行命令

在没有使用 IDE 的情况下，命令行接口是与 Gradle 交互的主要方式。你可以运行 task、检测 build、管理依赖和控制日志。

高度推荐使用 wrapper，推荐使用 `./gradlew` 或 `gradlew.bat 替代下面的实例中的 gradle。

gradle 的**命令结构**：`gradle [taskName...] [--option-name...]`

- 可以通过空格指定一个或多个任务 `gradle [taskName1 taskName2...] [--option-name...]`
- 例如：先运行 clean 任务，再运行 build 任务 `gradle clean build`

gradle 的**选项**：gradle 命令可以包含各种选项去调整任务的行为。

- 选项可以在任务名之前或之后。例如：`gradle [--option-name...] [taskName...]`

- 接收一个值的选项，使用等号连接更清晰：`gradle [...] --console=plain`

- 有些选项的是开关，拥有相反的形式：

  ```bash
  gradle build --build-cache
  gradle build --no-build-cache
  ```

- gradle 也提供了命令的简洁形式，下面两个是等价的

  ```bash
  gradle --help
  gradle -h
  ```

#### 2.3.2 执行任务

在 gradle 中，任务是特殊的工程，使用冒号作为工程的分隔符，可以清晰的指明你想要运行的任务，尤其是多工程的构建

- 在根工程中，运行 test 任务，可以这样：`gradle :test`
- 在子工程中，则需要指定全路径，需要这样：`gradle :subproject:test`
- 如果不使用冒号就运行任务，gradle 会在当前目录的工程上下文中执行这个任务：`gradle test`

#### 2.3.3 任务的选项

有一些任务有自己的特定选项，只需要加上 `--` 前缀，然后放在任务名后，就可以自定义选项：

```bash
gradle taskName --exampleOption=exampleValue
```

更多细节可以看 [Gradle 命令行接口参考](https://docs.gradle.org/current/userguide/command_line_interface.html#command_line_interface_reference)

### 2.4 配置文件基础

配置文件 `settings.gradle(.kts)` 是每个 gradle 工程的入口。

![gradle basic 3](https://docs.gradle.org/current/userguide/img/gradle-basic-3.png)

配置文件的主要目的是定义工程结构，通常是添加子工程到构建中。因此：

- 单工程构建，该配置是可选的
- 多工程构建，该配置是必需的，并且要声明所有子工程

#### 2.4.1 配置文件脚本

配置文件是 Groovy 编写的 `settings.gradle` 文件，或者 Kotlin 编写的 `settings.gradle.kts` 文件，所以它是一个脚本。

Gradle 只接受  [Groovy DSL](https://docs.gradle.org/current/dsl/index.html) 和 [Kotlin DSL](https://docs.gradle.org/current/kotlin-dsl/index.html) 这两种语言。

配置文件通常位于工程的根目录下，因为它定义了构建的结构，例如包含哪些工程。如果没有配置文件，gradle 默认该工程是单工程构建。

下面的例子：

```kotlin
rootProject.name = "root-project"   // 定义工程名

include("sub-project-a")            // 添加子工程 a、b、c
include("sub-project-b")
include("sub-project-c")
```

1. 定义工程名

   ```kotlin
   rootProject.name = "root-project" 
   ```

   如果只有这个配置，那么每个构建只有一个根工程

2. 添加子工程

   配置文件定义包含的子工程

   ```kotlin
   include("sub-project-a")
   include("sub-project-b")
   include("sub-project-c")
   ```

配置文件会在 build 脚本之前执行，把它放在正确的位置以启用或配置，例如：插件管理、包含的构建、版本目录等全局功能。

配置文件更多内容： [写配置文件](https://docs.gradle.org/current/userguide/writing_settings_files.html#writing_settings_files)

### 2.5 构建文件基础

通常一个构建脚本 `build.gradle(.kts)` 描述了构建配置、任务和插件。每一个 gradle 构建都包含至少一个构建脚本。

![gradle basic 4](https://docs.gradle.org/current/userguide/img/gradle-basic-4.png)

build 脚本是 Groovy 编写的 `build.gradle` 文件，或者 kotlin 编写的 `build.gradle.kts` 文件。和配置文件一样，只支持 [Groovy DSL](https://docs.gradle.org/current/dsl/index.html) 和 [Kotlin DSL](https://docs.gradle.org/current/kotlin-dsl/index.html) 这两种语言。

对于多工程构建，每个子工程通常在它自己的根目录中都有自己的 build 文件。

在 build 脚本里，你可以指定：

- 插件：拓展 task 功能的工具，例如：编译代码、运行测试或者打包工件。
- 依赖：外部库或项目中使用的工具。

build 脚本包含两个主要的依赖类型：

- gradle 和 build 脚本的依赖：gradle 自身和构建脚本逻辑所需要的插件和库
- 工程的依赖：你自己工程的源码编译和运行需要的库

```kotlin
plugins {   // 1
    // 使用 application 插件能够支持构建一个 java 的 CLI（命令行界面） 应用
    application
}
dependencies { // 2 
    // 使用 JUnit Jupiter 测试
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // 应用程序使用的依赖
    implementation(libs.guava)
}
application {   // 3
    // 定义应用程序的主类（入口）
    mainClass = "org.example.App"
}
```

1. 添加插件
2. 添加依赖
3. 使用约定属性

#### 2.5.1 添加插件

插件拓展了 gradle 的功能并且可以向工程提供 task。

添加插件到 build 中，被称为应用一个插件并启用额外功能

```kotlin
plugins {   
    // 应用 application 插件能够支持构建一个 java 的 CLI（命令行界面） 应用
    application
}
```

application 插件帮助创建一个可执行的 JVM 应用。

应用  [Application 插件](https://docs.gradle.org/current/userguide/application_plugin.html#application_plugin) 也包含了应用 java 插件。该 java 插件向工程添加了编译、测试和构建的能力。

#### 2.5.2 添加依赖

你的工程编译、运行和测试需要外部库。

下面的例子，在工程中使用了  `JUnit Jupiter` 库进行测试， 在主应用代码的代码中使用了 Google 的 `Guava` 库：

```kotlin
dependencies {  
    // 使用 JUnit Jupiter 测试
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // 应用程序使用 Guava
    implementation(libs.guava)
}
```

#### 2.5.3 使用约定属性

插件向工程中添加 task。它还向项目中添加属性和方法。

`application` 插件定义了打包和分发应用的任务，例如：名为 `run` 的 task

这个应用提供了声明 Java 应用程序主类的途径。

```kotlin
application {   
    // 定义该应用的主类
    mainClass = "org.example.App"
}
```

这个例子中，主类（程序启动的入口）是 `org.example.App`。

build 脚本在构建的配置阶段执行，并且它们是定义子工程构建逻辑的入口。此外，应用插件并配置约定属性，构建脚本可以：

- 声明依赖
- 配置任务
- 引用共享配置（来自版本目录或约定插件的配置）

学习更多  [写 Build 文件](https://docs.gradle.org/current/userguide/writing_build_scripts.html#writing_build_scripts)

### 2.6 依赖基础

gradle 自带依赖管理支持。

![gradle basic 7](https://docs.gradle.org/current/userguide/img/gradle-basic-7.png)

依赖管理是一种声明和解析工程需要的外部资源的自动化技术。

依赖包含支撑构建工程的 jar包、插件、库或者源码。它们被声明在构建 build 脚本中。

gradle 自动下载、缓存和解析这些依赖，帮助你避免手动管理它们。它也处理版本冲突并且支持灵活的版本声明。

#### 2.6.1 声明依赖

添加依赖通过在 `build.gradle(.kts)` 的 `dependencies {}` 块中指定一个依赖。

例如下面的 `build.gradle.kts` 添加了一个插件和两个依赖到工程中：

```kotlin
plugins {
    id("java-library")  // 1
}

dependencies {
    implementation("com.google.guava:guava:32.1.2-jre") // 2
    api("org.apache.juneau:juneau-marshall:8.2.0")      // 3
}
```

1. 应用帮助构建 java 库的插件
2. 添加 Google 的 guava 库依赖
3. 添加 Apache 的 Juneau 依赖

在 gradle 中，依赖通过配置进行分组，配置定义了何时和怎样使用依赖
- `implementation` 用于依赖需要在编译和运行代码时使用
- `api` 用于依赖应该暴露给你的库的消费者时使用

gradle 支持更多其他的配置，例如：`testImplementation`, `runtimeOnly`, `compileOnly`, `api` 等

#### 2.6.2 查看工程依赖

可以使用 `dependencies` task 检查依赖树。例如：查看 app 子工程的依赖

```bash
$ ./gradlew :app:dependencies
```

gradle 将会按照配置分类并输出依赖树：

```bash
$ ./gradlew :app:dependencies

> Task :app:dependencies

------------------------------------------------------------
Project ':app'
------------------------------------------------------------

...

runtimeClasspath - Runtime classpath of source set 'main'.
+--- org.apache.juneau:juneau-marshall:8.2.0
|    \--- org.apache.httpcomponents:httpcore:4.4.13
\--- com.google.guava:guava:32.1.2-jre
     +--- com.google.guava:guava-parent:32.1.2-jre
     |    +--- com.google.code.findbugs:jsr305:3.0.2 (c)
     |    +--- org.checkerframework:checker-qual:3.33.0 (c)
     |    \--- com.google.errorprone:error_prone_annotations:2.18.0 (c)
     +--- com.google.guava:failureaccess:1.0.1
     +--- com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava
     +--- com.google.code.findbugs:jsr305 -> 3.0.2
     +--- org.checkerframework:checker-qual -> 3.33.0
     \--- com.google.errorprone:error_prone_annotations -> 2.18.0
```

#### 2.6.3 使用版本目录

版本目录提供了集中的和稳定的方式来管理依赖坐标和构建的全部版本。不是直接在每个 `build.gradle(.kts)` 中声明版本，而是在 `libs.versions.toml` 文件中定义它们。

使得这些变得简单：

- 在子工程间共享公共依赖
- 避免重复和版本不连续
- 在大工程中强制要求依赖和插件的版本

版本目录通常包含四个部分：

1. `[versions]` 定义引入的插件和库的版本号
2. `[libraries]` 定义 build 文件使用的库
3. `[bundles]` 定义依赖集合
4. `[plugins]` 定义插件

例如：

```bash
gradle/libs.versions.toml

[versions]
guava = "32.1.2-jre"
juneau = "8.2.0"

[libraries]
guava = { group = "com.google.guava", name = "guava", version.ref = "guava" }
juneau-marshall = { group = "org.apache.juneau", name = "juneau-marshall", version.ref = "juneau" }
```

把这个文件命名为 `libs.versions.toml` 放在 `gradle/` 下。通过 build 脚本的 `libs` 访问器，gradle 会自动选择并暴露它的内容。

IntelliJ 和 Android Studio等 IDE 也会读取这些元数据帮助补全代码。

只要定义了 `libs.versions.toml`，就可以在 build 文件中使用这些别名：

```kotlin
dependencies {
    implementation(libs.guava)
    api(libs.juneau.marshall)
}
```

更多内容[依赖管理章节](https://docs.gradle.org/current/userguide/getting_started_dep_man.html#dependency-management-in-gradle)

### 2.7 任务基础

任务代表了执行构建的一些独立工作单元，例如：编译 class、创建 jar、生成 javadoc 或者发布存档到存储库。

![gradle basic 5](https://docs.gradle.org/current/userguide/img/gradle-basic-5.png)

task 是每个 gradle 构建的构建模块。常用的类型包括：

- 编译源码
- 运行测试
- 打包输出（jar、apk等）
- 生成文档（javadoc）
- 发布构建的工件到存储库

每一个任务都是独立的，但是可以依赖其他的任务先执行。gradle 使用这些信息确定执行这些任务最高效的顺序 — 跳过已经是最新的内容。

#### 2.7.1 运行 task

使用工程根目录的 wrapper 运行 task。例如运行 build task

```bash
$ ./gradlew build
```

#### 2.7.2 列出可用的 task

插件和 build 脚本定义了工程可用的 task。查看它们：

```bash
$ ./gradlew tasks
```

会显示一个 task 的分类列表：

```bash
Application tasks
-----------------
run - Runs this project as a JVM application

Build tasks
-----------
assemble - Assembles the outputs of this project.
build - Assembles and tests this project.

...

Documentation tasks
-------------------
javadoc - Generates Javadoc API documentation for the main source code.

...

Other tasks
-----------
compileJava - Compiles main Java source.

...
```

#### 2.7.3 运行 task 

在 build 文件中应用了 application 插件，那么 run task 就可用。这样运行：

```bash
$ ./gradlew run
```

输出：

```bash
> Task :app:compileJava # 首先编译
> Task :app:processResources NO-SOURCE
> Task :app:classes

> Task :app:run
Hello World!  # 应用程序输出 Hello World

BUILD SUCCESSFUL in 904ms
2 actionable tasks: 2 executed
```

#### 2.7.4 task 依赖

大部分任务都不会互相隔离运行。但是 gradle 知道 task 的运行顺序，即使某个任务依赖于另一个任务先执行。

例如当运行 `./gradlew build` 时，由于 `build` 依赖 `compileJava`, `test`, and `jar`，所以会先执行它们：

```bash
$ ./gradlew build

> Task :app:compileJava
> Task :app:processResources NO-SOURCE
> Task :app:classes
> Task :app:jar
> Task :app:startScripts
> Task :app:distTar
> Task :app:distZip
> Task :app:assemble
> Task :app:check
> Task :app:build # 最后 build

BUILD SUCCESSFUL in 764ms
7 actionable tasks: 7 executed
```

不用担心执行顺序问题，gradle 会帮你确定。

[学习更多](https://docs.gradle.org/current/userguide/more_about_tasks.html#more_about_tasks)

### 2.8 插件基础

gradle 开箱即用，是因为建立在灵活的插件系统之上。gradle 提供了如：依赖解析、task 编排和增量构建等核心基础设施。插件提供了大部分功能：编译 java、构建安卓app或发布工件等。

![gradle basic 6](https://docs.gradle.org/current/userguide/img/gradle-basic-6.png)

插件是一个软件的可复用部分，提供额外功能给 gradle 的构建系统。它可以：

- 添加新任务到构建中（`compileJava` 或 `test`）
- 添加新配置（ `implementation` 或 `runtimeOnly`）
- 使用 DSL 元素（`application {}` 或 `publishing {}`）

使用 Kotlin DSL 或 Groovy DSL的 `plugins` 块，可以将插件应用于 build 脚本中，它们会带来指定域名或工作流所需要的所有逻辑。

#### 2.8.1 常用插件

- **Java Library Plugin (`java-library`)**：编译 java 源码、生成 Javadoc、打包 class 文件到  JAR。添加 `compileJava`、`javadoc` 和 `jar` 任务。
- **Google Services Plugin (`com.google.gms.google-services`)**：配置 Firebase 和 谷歌的安卓 API。添加 `googleServices {}` DSL和 `generateReleaseAssets` 任务等。
- **Gradle Bintray Plugin(`com.jfrog.bintray`)** ：使用一个 `bintray {}` 配置块，发布工件到二进制（或 Maven 风格的存储库）

#### 2.8.2 应用插件

使用插件可以拓展工程的能力。应用插件需要在 build 脚本中使用插件的 id（全局唯一的标识符/名字）和版本号。

```kotlin
plugins {
    id("«plugin id»").version("«plugin version»")
}

// 例如：这会添加相应的任务、配置、DSL
plugins {
    id("java-library")
    id("com.diffplug.spotless").version("6.25.0")
}
```

#### 2.8.3 插件类型

gradle 支持的插件类型：

- 脚本插件：可复用 `.gradle` 或 `.gradle.kts` 文件，通过 `apply from:` 应用。
- 预编译插件：打包 Kotlin 或 Groovy 代码，通过 `plugins {}` 块应用。
- 二进制插件：打包和发布插件（通常来自该插件的门户或Maven），通过 `plugins {}` 块应用。

 **binary** 或 **precompiled** 插件更现代化。

#### 2.8.4 配置插件

[配置插件](https://docs.gradle.org/current/userguide/part4_gradle_plugins.html#step_3_configuring_the_plugin)

#### 2.8.5 发行的插件

gradle 插件来自不同的源码，根据实际情况选择正确的类型。

1. 核心插件（gradle 自带）

   核心插件是 gradle 发行版自带的一系列插件，它们提供了构建和管理工程的基本功能。核心插件的特点是它们有简短的名称，例如：`java-library` 是核心插件 [JavaLibraryPlugin](https://docs.gradle.org/current/javadoc/org/gradle/api/plugins/JavaLibraryPlugin.html)。通过 ID 应用它们，不需要额外设置：

   ```kotlin
   plugins {
       id("java-library")
   }
   ```

   这些插件都是 gradle 团队在维护。[完整的核心插件列表参考](https://docs.gradle.org/current/userguide/plugin_reference.html#plugin_reference)

2. 社区插件（来自插件的门户网站）

   社区插件由 gradle 社区开发，而不是 gradle 核心发行版的一部分。这些插件提供了特定情况或特定技术的额外功能。

   gradle 的插件生态包括社区贡献的上千个开源插件。它们发布在 [gradle 门户网站](https://plugins.gradle.org/?_gl=1*1ml8ua*_gcl_au*MTQ2NDQxODkyMi4xNzQ5NjMzNzgz*_ga*MTE5MDE4OTg4OS4xNzQ5NjMzNzg0*_ga_7W7NC6YNPT*czE3NDk4MDU0OTIkbzYkZzEkdDE3NDk4MDU2NjgkajU1JGwwJGgw)，可以通过 ID 和版本号应用：

   ```kotlin
   plugins {
       id("org.springframework.boot").version("3.1.5")
   }
   ```

   当构建在运行时，gradle 会自动下载这个插件。

3. 本地或自定义插件（定义在自己的 build 中，位于  `buildSrc/` 或者指定的子项目中）

   你可以写自己的插件 —— 可用于单个项目，或在同一个构建中的多个项目间共享使用。

   同样使用 `plugins{}` 块，就像外部插件一样：

   ```kotlin
   plugins {
       id("my.custom-conventions")
   }
   ```
   
   **约定插件**：用于在子项目之间共享通用构建逻辑的自定义插件（如**代码风格、编译器选项或测试设置**）
   
   Gradle 建议使用约定插件来避免重复并管理大型构建中的共享设置。[学习更多约定插件](https://docs.gradle.org/current/samples/sample_convention_plugins.html)

[学习更多插件](https://docs.gradle.org/current/userguide/custom_plugins.html#custom_plugins)

### 2.9 缓存基础

gradle 通过两个特性来减少构建时间：**增量构建**和**构建缓存**。

![gradle basic 8](https://docs.gradle.org/current/userguide/img/gradle-basic-8.png)

#### 2.9.1 增量构建

增量构建避免运行输出没有变化的任务，没必要再执行这些生成相同的输出的任务。

要让增量构建正常工作，task 必须定义它们的**输入和输出**，gradle 在构建时，会确定这些输入输出是否有改变。如果有改变则执行该任务，否则跳过执行。

增量构建总是可用的，开启 **verbose** 模式是查看它们工作情况的最佳方式。**verbose** 模式会在构建时，给每个任务打上状态标签。

```bash
$ ./gradlew compileJava --console=verbose

> Task :buildSrc:generateExternalPluginSpecBuilders UP-TO-DATE
> Task :buildSrc:extractPrecompiledScriptPluginPlugins UP-TO-DATE
> Task :buildSrc:compilePluginsBlocks UP-TO-DATE
> Task :buildSrc:generatePrecompiledScriptPluginAccessors UP-TO-DATE
> Task :buildSrc:generateScriptPluginAdapters UP-TO-DATE
> Task :buildSrc:compileKotlin UP-TO-DATE
> Task :buildSrc:compileJava NO-SOURCE
> Task :buildSrc:compileGroovy NO-SOURCE
> Task :buildSrc:pluginDescriptors UP-TO-DATE
> Task :buildSrc:processResources UP-TO-DATE
> Task :buildSrc:classes UP-TO-DATE
> Task :buildSrc:jar UP-TO-DATE
> Task :list:compileJava UP-TO-DATE
> Task :utilities:compileJava UP-TO-DATE
> Task :app:compileJava UP-TO-DATE

BUILD SUCCESSFUL in 374ms
12 actionable tasks: 12 up-to-date
```

当运行之前执行过并且没有改变的任务时，会打上 `UP-TO-DATE` 标签

在 gradle 配置文件 `gradle.properties` 中添加 `org.gradle.console=verbose` 可以永久开启 verbose 模式。

#### 2.9.2 构建缓存

增量构建是一个巨大的优化。如果开发者始终改变单个文件，肯定就不需要重新构建项目的其他文件。

然而，当开发者切换到上周新建的分支上时，会发生什么？这些文件将会被重新构建，即使开发者正在构建之前已经被构建的文件。这时，构建缓存是有帮助的。

构建缓存会保存先前的构建结果，当需要它们时，会恢复它们。它避免了冗余的工作、执行耗时的成本和昂贵的进程。

当构建缓存重新填充至本地目录时，task 会标记为 `FROM-CACHE`：

```bash
$ ./gradlew compileJava --build-cache

> Task :buildSrc:generateExternalPluginSpecBuilders UP-TO-DATE
> Task :buildSrc:extractPrecompiledScriptPluginPlugins UP-TO-DATE
> Task :buildSrc:compilePluginsBlocks UP-TO-DATE
> Task :buildSrc:generatePrecompiledScriptPluginAccessors UP-TO-DATE
> Task :buildSrc:generateScriptPluginAdapters UP-TO-DATE
> Task :buildSrc:compileKotlin UP-TO-DATE
> Task :buildSrc:compileJava NO-SOURCE
> Task :buildSrc:compileGroovy NO-SOURCE
> Task :buildSrc:pluginDescriptors UP-TO-DATE
> Task :buildSrc:processResources UP-TO-DATE
> Task :buildSrc:classes UP-TO-DATE
> Task :buildSrc:jar UP-TO-DATE
> Task :list:compileJava FROM-CACHE
> Task :utilities:compileJava FROM-CACHE
> Task :app:compileJava FROM-CACHE

BUILD SUCCESSFUL in 364ms
12 actionable tasks: 3 from cache, 9 up-to-date
```

一旦填充到本地目录，下次执行会标记为 `UP-TO-DATE` 而不是 `FROM-CACHE`

- `gradle.properties` 添加 `org.gradle.caching=true` 永久启用构建缓存

- `FROM-CACHE` - 任务从本地构建缓存中检索

- `UP-TO-DATE` - 任务使用了增量构建，并且没有重复执行

- 使用 `clean` 任务，可以清理本地构建缓存

- 构建缓存开启就是把之前构建的输出内容（保存在 cache 目录）复制到工程的 build 目录

  cache 目录位置：

  - **Windows**: `%USERPROFILE%\.gradle\caches`
  - **OS X / UNIX**: `~/.gradle/caches/`

- gradle 会定期清理本地构建缓存目录，删除最近没有使用的条目，以节省磁盘空间。

构建缓存允许在团队间共享和复用未改变的构建和测试输出，提升了本地和持续集成（CI）构建的速度，因为时间不会浪费在构建没有受到新代码影响的二进制文件。

#### 2.9.3 远程构建缓存

除了开发者本地有构建缓存外，gradle 还可以提供**远程构建缓存**给**多名开发者共享**。目的是提升构建时间。

远程和本地的缓存同时启用时，本地会优先检查。如果本地缓存不存在该任务的输出，就会从远程下载并保存在本地。

Gradle 提供了单个远程构建缓存节点的免费 [Docker 镜像](https://hub.docker.com/r/gradle/build-cache-node)。对于生产级别的部署，建议使用 [Develocity](https://gradle.com/gradle-enterprise-solutions/build-cache/?_gl=1*wlzlnj*_gcl_au*MTE5Mzk0Nzg4OC4xNzQ5ODcyNTkx*_ga*OTM4OTA5ODM4LjE3NDk4NzI1OTE.*_ga_7W7NC6YNPT*czE3NDk5MDU3OTMkbzckZzEkdDE3NDk5MDYwNTckajU4JGwwJGgw)。

 [学习更多构建缓存相关内容](https://docs.gradle.org/current/userguide/build_cache.html#build_cache)。

### 2.10 构建扫描

**构建扫描**呈现了运行构建时所采集的元数据。

#### 2.10.1 关于构建扫描

gradle 采集构建的元数据，并发送给构建扫描服务（[Build Scan Service](https://scans.gradle.com/)） 。该服务随后转化元数据为可解析并且能和其他人共享的信息。

扫描收集的信息可能是故障排除、协作或优化构建性能的宝贵资源。例如，有构建扫描，不在需要复制粘贴错误消息或者环境细节信息，到 Stack Overflow  或 gradle 论坛上提问，而是 copy 最新的构建扫描链接。

#### 2.10.2 启用构建扫描

在 gradle 命令上，添加 `--scan` 到命令行选项上，以启用构建扫描

``` bash
./gradlew build --scan
```

使用构建扫描，需要同意一些条款：[看构建扫描页了解更多](https://scans.gradle.com/)。

#### 2.10.3 采集的信息

在构建扫描中，采集和发送了什么数据，参考[Gradle Develocity 插件文档采集信息章节](https://docs.gradle.com/develocity/gradle-plugin/current/?_gl=1*smkvvh*_gcl_au*MTE5Mzk0Nzg4OC4xNzQ5ODcyNTkx*_ga*OTM4OTA5ODM4LjE3NDk4NzI1OTE.*_ga_7W7NC6YNPT*czE3NDk4ODI4OTIkbzIkZzEkdDE3NDk4ODI4OTYkajU2JGwwJGgw#captured_information)

[动手入门教程](https://docs.gradle.org/current/userguide/part1_gradle_init.html#part1_begin)

## 3. Gradle 的参考资料

下面的参考材料是有用的：

- Gradle API [Javadocs](http://gradle.org/docs/current/javadoc/)
- Gradle’s [Groovy DSL](https://docs.gradle.org/current/dsl/index.html)
- Gradle’s [Kotlin DSL](https://docs.gradle.org/current/kotlin-dsl/index.html)

查找插件：

- Gradle [Core Plugins](https://docs.gradle.org/current/userguide/plugin_reference.html#plugin_reference)
- Gradle [Plugin Portal](https://plugins.gradle.org/)

Gradle 所有的发行版：

- [Gradle Releases](https://gradle.org/releases/)
- [Release Notes](http://gradle.org/docs/current/release-notes)

对 Gradle 支持：

- [Gradle Forum](https://discuss.gradle.org/)
- [Gradle Slack Channel](https://gradle-community.slack.com/)
- [Free Gradle Training](https://gradle.org/courses/)

以上是开发人员需要学习的内容，[用户手册](https://docs.gradle.org/current/userguide/userguide.html)说明了接下来需要学习的内容。



[单项目转为多项目](https://docs.gradle.org/current/userguide/how_to_convert_single_build_to_multi_build.html)

**在子项目之间共享构建逻辑的一种不正确的方式是通过 [`subprojects {}`](https://docs.gradle.org/current/javadoc/org/gradle/api/Project.html#subprojects-groovy.lang.Closure-) 和 [`allprojects {}`](https://docs.gradle.org/current/javadoc/org/gradle/api/Project.html#allprojects-groovy.lang.Closure-) 进行跨项目配置。从长远来看，跨项目配置通常会增加复杂性并成为一种负担。跨项目配置还可能在项目之间引入配置时耦合，这可能会阻止按需配置等优化正常工作。**

[BuildSrc 共享构建逻辑，即约定配置，替代跨项目配置](https://docs.gradle.org/current/userguide/sharing_build_logic_between_subprojects.html)

版本目录仅对主项目（根项目）及其子模块（`subprojects`）的构建脚本（`build.gradle.kts`）生效，**不适用于 `buildSrc` 项目** 。



# 4. 工程中的实践

- 工程根目录

  - `settings.gradle.kts`

    ```kotlin
    rootProject.name = "projectName"
    include(
        "api",
        "home",
        "gate",
        "common",
        "protocol",
        "tools",
        "thirdparty",
        "opshell",
        "data",
        "frameMatch"
    )
    
    // 配置 Gradle 插件管理仓库，优先级高于项目级 build.gradle.kts 中的仓库配置
    // pluginManagement 块用于定义从哪里下载 Gradle 插件，当 Gradle 需要插件时，按顺序检查这些仓库
    pluginManagement {
        // 仓库配置，定义插件下载的仓库地址列表
        repositories {
            // nexus repository oss 仓库地址
            val nexusUrl: String = File("${rootProject.projectDir}/nexus-repo-url.txt").readText().trim()
            // 仓库 1
            maven {
                isAllowInsecureProtocol = true // 允许 HTTP 协议
                url = uri("$nexusUrl/gradle-plugins") // 
            }
            // 仓库 2
            maven {
                isAllowInsecureProtocol = true
                url = uri("$nexusUrl/snapshots/")
            }
        }
    }
    ```

  - 版本目录

  - **Gradle 运行时属性在某些情况下可代替版本目录**

    作用在 settings 和 初始化脚本，有两种定义方式：

    - [工程属性](https://docs.gradle.org/current/userguide/build_environment.html#sec:project_properties)是全局的，在 `root/gradle.properties` 中定义版本号（还可以命令行参数指定），可在项目及其子项目的 `build.gradle` 中引用：

      ```kotlin
      kotlin.daemon.jvm.options=-Xmx8G
      org.gradle.jvmargs=-Xmx8G -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8
      
      kotlin.code.style=official
      #lib
      libVersion=hotd-1.0.2
      # akka
      akkaVersion=2.6.21
      # netty
      nettyVersion=4.1.96.Final
      # gradle zk root
      zk_root=hotd_global
      ```

    - [额外属性](https://docs.gradle.org/current/userguide/writing_build_scripts.html#sec:extra_properties)在 `build.gradle.kts` **通过 `extra` 定义**

      ```kotlin
      extra["myProperty"] = "myProperty"
      ```

    - 访问属性的方式

      ```kotlin
      // build 脚本中
      val akkaVersion: String by project
      val nettyVersion: String by project
      
      val myProperty: String by project  
      val myNullableProperty: String? by project
      
      tasks {
          test {
              val reportType by extra("dev")  // 定义属性
          }
      
          register<Zip>("archiveTestReports") {
              val reportType: String by test.get().extra  // 访问属性
              archiveAppendix = reportType
          }
      }
      ```

- buildSrc

  - `src/main/kotlin/constants`：管理常量

    ```kotlin
    const val KOTLIN_VERSION = "2.1.20"
    const val CONSOLE_LOG_OPTION = "-Dconsole_log=true"
    const val JAVA_NIO = "java.base/java.nio=ALL-UNNAMED"
    
    //项目包含模块
    val gameWorldProjects = listOf(
        "common", "gate", "home", "api", "data"
    )
    
    //需要热更的模块
    val needPatchProjects = listOf(
        "gate", "home", "thirdparty"
    )
    
    //不需要打包的模块
    val noDistProjects = listOf("opshell")
    ```



Now in Android 和我公司的应用都用 Version Catalogs 加上 convention plugins。你只需要在模块里用 convention plugins 注册 catalogs 就行了。

你可以看看 Now in Android 的仓库： https://github.com/android/nowinandroid

他们甚至在 Convention Plugins 里面获取版本号。他们有一个扩展在这里： https://github.com/android/nowinandroid/blob/main/build-logic/convention/src/main/kotlin/com/google/samples/apps/nowinandroid/ProjectExtensions.kt
