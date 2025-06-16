plugins {
	id("java-common-conventions")
}

dependencies {
	"implementation"(libs.netty.all)
	"implementation"(libs.protobuf.core)
	"implementation"(libs.zookeeper)
	"implementation"(libs.curator.framework)
}
