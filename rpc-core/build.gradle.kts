plugins {
	id("java-common-conventions")
}

dependencies {
	"implementation"(libs.netty.all)
	"implementation"(libs.protostuff.core)
	"implementation"(libs.zookeeper)
	"implementation"(libs.curator.framework)

	// 依赖其他模块
	"implementation"(project(":rpc-protocol"))
	"implementation"(project(":rpc-common"))
}