我想复现这篇论文的内容，请从一开始就加入他的RPC 服务器的优化的考虑，还有加入动态代理，来教我实现这个rpc框架。现在一步一步来教我，我问问题，你回答，不要直接给出实现全部的过程和步骤。这一步是在当前目录中按下面的要求列出gradle创建这个工程的示例：实现这个系统应该怎么样设计架构，即整个 build 是怎么样的，分别负责什么任务。

由于论文的年份较久远，下面列举的技术，请使用当下最新的、可兼容的版本：
我要使用idea创建gradle工程（按照企业级工程的习惯，我使用过Maven）， 使用的技术有：netty（刚学完）、kotlin（有良好的Java基础）、jdk21、Protostuff（会写proto文件并在代码中使用过，但是没学过）、zookeeper（没学过，但是学过springCloud，用过consul和eruka写项目）、curator（没学过，注意zookeeper和curator兼容性，用相兼容的最新版本,zk我会使用docker来运行,根工程目录下compose.yaml）、SpringBoot 3、日志采用log4j2配合sjl4j。



