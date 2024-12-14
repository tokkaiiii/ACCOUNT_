import org.jooq.meta.jaxb.*

val jooqVersion: String by extra("3.19.5")
plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
    id("dev.monosoul.jooq-docker") version "6.0.14"
}

group = "com.tokkaiiii.account"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-jooq")
    implementation("org.springframework.boot:spring-boot-starter-mustache")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // jOOQ 관련 의존성
    jooqCodegen("org.jooq:jooq:$jooqVersion")
    jooqCodegen("org.jooq:jooq-meta:$jooqVersion")
    jooqCodegen("org.jooq:jooq-codegen:$jooqVersion")
    jooqCodegen(project(":jOOQ-custom"))

    // Flyway 의존성
    jooqCodegen("org.flywaydb:flyway-core:10.8.1")
    jooqCodegen("org.flywaydb:flyway-mysql:10.8.1")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jooq {
    withContainer {
        image {
            name = "mysql:lts"
            envVars = mapOf(
                "MYSQL_ROOT_PASSWORD" to "1234",
                "MYSQL_DATABASE" to "security",
            )
        }
        db {
            username = "root"
            password = "1234"
            name = "security"
            port = 3306
            jdbc {
                schema = "jdbc:mysql"
                driverClassName = "com.mysql.cj.jdbc.Driver"
            }
        }
    }

}

tasks {
    generateJooqClasses {
        schemas.set(listOf("security"))
        outputDirectory.set(project.layout.projectDirectory.dir("src/generated"))
        includeFlywayTable.set(false)

        usingJavaConfig {
            generate = Generate()
                .withJavaTimeTypes(true)
                .withDeprecated(false)
                .withDaos(true)
                .withFluentSetters(true)
                .withRecords(true)
            withStrategy(
                Strategy().withName("com.tokkaiiii.account.jooq.custom.generator.JPrefixGeneratorStrategy")
            )

            database.withForcedTypes(
                ForcedType().apply {
                    userType = "java.lang.Long"
                    includeTypes = "int unsigned"
                },
                ForcedType().apply {
                    userType = "java.lang.Integer"
                    includeTypes = "tinyint unsigned"
                },
                ForcedType().apply {
                    userType = "java.lang.Integer"
                    includeTypes = "smallint unsigned"
                }
            )
        }
    }
}

sourceSets {
    main {
        kotlin {
            srcDirs(listOf("src/main/kotlin", "src/generated"))
        }
    }
}