/*
 * Copyright (c) 2021 XPATH-QS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.tasks.testing.logging.TestLogEvent

version = "0.1.2"
group = "org.xpathqs"

plugins {
    kotlin("jvm") version "1.5.0"
    id("org.jetbrains.dokka") version "1.4.32"
    `java-library`
    jacoco
    maven
    `maven-publish`
    signing
    id("io.codearte.nexus-staging") version "0.30.0"
    id("io.gitlab.arturbosch.detekt").version("1.18.0-RC2")
    id("info.solidsoft.pitest").version("1.7.0")
    id("org.jetbrains.kotlinx.kover") version "0.4.1"
}

java {
    withJavadocJar()
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

jacoco {
    toolVersion = "0.8.7"
}

repositories {
    mavenCentral()
    mavenLocal()
}

detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = true // activate all available (even unstable) rules.
    config = files("$projectDir/detekt.yml") // point to your custom config defining rules to run, overwriting default behavior
    reports {
        html.enabled = true // observe findings in your browser with structure and code snippets
        txt.enabled = true // similar to the console output, contains issue signature to manually edit baseline files
    }
}

dependencies {
    implementation("io.codearte.gradle.nexus:gradle-nexus-staging-plugin:0.30.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.0")
    implementation("org.yaml:snakeyaml:1.28")
    implementation("net.oneandone.reflections8:reflections8:0.11.7")

    implementation("org.xpathqs:gwt:0.1.1")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.0")

    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.23.1")
}

publishing {
    publications {
        beforeEvaluate {
            signing.sign(this@publications)
        }
        create<MavenPublication>("mavenJava") {
            pom {
                name.set("XpathQS Core")
                description.set("A library for building Xpath queries in an OOP style")
                url.set("https://xpathqs.org/")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                    }
                }
                developers {
                    developer {
                        id.set("nachg")
                        name.set("Nikita A. Chegodaev")
                        email.set("nikchgd@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/xpathqs/core.git")
                    developerConnection.set("scm:git:ssh://github.com/xpathqs/core.git")
                    url.set("https://xpathqs.org/")
                }
            }
            groupId = "org.xpathqs"
            artifactId = "core"

            from(components["java"])
        }
    }
    repositories {
        maven {
            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = project.property("ossrhUsername").toString()
                password = project.property("ossrhPassword").toString()
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}

nexusStaging {
    serverUrl = "https://s01.oss.sonatype.org/service/local/"
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        showStandardStreams = true
        events = setOf(
            TestLogEvent.FAILED,
            TestLogEvent.STANDARD_OUT,
            TestLogEvent.STANDARD_ERROR
        )
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
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

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    dokkaSourceSets {
        configureEach {
            samples.from("src/test/kotlin/org/xpathqs/core", "src/main/kotlin/org/xpathqs/core")
        }
    }
}

pitest {
    junit5PluginVersion.set("0.15")
    targetClasses.set(
        arrayListOf(
            "org.xpathqs.core.selector.*",
            "org.xpathqs.core.model.*",
            "org.xpathqs.core.reflection.*",
            "org.xpathqs.core.util.*",
            "org.xpathqs.core.constants.*"

            //exclude 'annotations' package. - due to integration problems with Kotlinn and PiTest
        )
    )
    threads.set(8)
    outputFormats.set(arrayListOf("HTML"))
    jvmArgs.set(arrayListOf("-Xmx1024m"))

    mutationThreshold.set(80)
    testStrengthThreshold.set(85)
    coverageThreshold.set(95)
}
