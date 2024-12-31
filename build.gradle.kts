plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.openapi.generator") version "7.0.1"

}

group = "ru.kolesnikov"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
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

    val mapStructVersion = "1.6.3"
    val jacksonOpenApiNullable = "0.2.6"
    val springdocOpenApiVersion = "2.3.0"

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocOpenApiVersion")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.mapstruct:mapstruct:${mapStructVersion}")
    implementation("org.mapstruct:mapstruct-processor:${mapStructVersion}")
    implementation("org.openapitools:jackson-databind-nullable:$jacksonOpenApiNullable")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
    implementation("org.webjars:jquery:3.4.1")
    implementation("org.webjars:bootstrap:5.0.0")
    implementation("org.webjars:webjars-locator-core")
    implementation("org.webjars:js-cookie:2.1.0")
    implementation("org.postgresql:postgresql")



    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    annotationProcessor("org.mapstruct:mapstruct-processor:${mapStructVersion}")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:${mapStructVersion}")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
}





kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-test")
        implementation( "org.springframework.security:spring-security-test")
        implementation("org.testcontainers:junit-jupiter")
        implementation("org.jetbrains.kotlin:kotlin-test-junit5")
        implementation("org.testcontainers:postgresql")
        implementation("org.junit.platform:junit-platform-launcher")
        implementation("io.rest-assured:rest-assured")
        implementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
    }
}

tasks.named("compileKotlin") {
    dependsOn(tasks.named("openApiGenerate"))
}

val generatedSourcesDir = "$rootDir/build/generated"


openApiGenerate {
    generatorName.set("kotlin-spring")
    apiNameSuffix.set("Interface")
    inputSpec.set("$rootDir/src/main/resources/static/openapi.yml")
    outputDir.set(generatedSourcesDir)
    apiPackage.set("ru.kolesnikov.social_net_otus.controller")
    modelPackage.set("ru.kolesnikov.social_net_otus.model")
    configOptions.set(
        mapOf(
            "library" to "spring-boot",
            "interfaceOnly" to "true",
            "useSpringBoot3" to "true",
            "useTags" to "true",
            "skipDefaultInterface" to "true",
            "serializationLibrary" to "jackson"
        )
    )
    globalProperties.set(
        mapOf(
            "modelDocs" to "false"
        )
    )
    typeMappings.putAll(
        mapOf(
            "OffsetDateTime" to "Instant"
        )
    )
    importMappings.putAll(
        mapOf(
            "java.time.OffsetDateTime" to "java.time.Instant"
        )
    )
}

sourceSets.named("main") {
    kotlin.srcDir(generatedSourcesDir)
}