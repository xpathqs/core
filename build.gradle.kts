import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "org.xpathqs"
version = "0.0.1"

plugins {
    kotlin("jvm") version "1.4.32"
    `java-library`
    jacoco
    `maven-publish`
    signing
}

java {
    withJavadocJar()
    withSourcesJar()
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.32")

    implementation("org.yaml:snakeyaml:1.28")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")

    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.23.1")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "org.xpathqs"
            artifactId = "xpathqs-core"
            version = "0.0.1"

            from(components["java"])
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.jar {
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version
            )
        )
    }
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = false
        csv.isEnabled = true
    }
}