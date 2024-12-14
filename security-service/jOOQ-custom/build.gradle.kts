val jooqVersion: String by rootProject.extra
plugins {
    kotlin("jvm")
}

group = "com.tokkaiiii.account"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation ("org.jooq:jooq-codegen:$jooqVersion")
    runtimeOnly ("com.mysql:mysql-connector-j:8.2.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}