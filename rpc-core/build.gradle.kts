plugins {
	id("java-common-conventions")
}

dependencies {
	"implementation"(libs.netty.all)
	"implementation"(libs.protostuff.core)
	"implementation"(libs.zookeeper)
	"implementation"(libs.curator.framework)
}
