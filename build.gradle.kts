plugins {
    `java-library`
    `maven-publish`
    signing
    // Replaces maven-assembly-plugin's "jar-with-dependencies". Check for a newer version before using.
    id("com.gradleup.shadow") version "9.0.0"
}

group = "com.github.xeroapi"
version = "18.3.1"
description = "This is the official Java SDK for Xero API"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(26)
    }
    withSourcesJar()
    withJavadocJar()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
    // Keep javadoc from failing the build.
    (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
}

val googleApiClientVersion = "2.9.2"
val jacksonVersion = "3.2.4"
val jacksonThreetenbpVersion = "2.18.2"
val jerseyVersion = "5.0.0-M1"
val junitVersion = "6.1.3"

dependencies {
    api("com.auth0:java-jwt:4.6.2") {
        exclude(group = "com.fasterxml.jackson.core", module = "jackson-databind")
        exclude(group = "com.fasterxml.jackson.core", module = "jackson-core")
    }
    api("com.auth0:jwks-rsa:0.24.2")
    api("com.google.api-client:google-api-client:$googleApiClientVersion") {
        exclude(group = "com.google.guava", module = "guava")
    }
    api("com.googlecode.json-simple:json-simple:1.1.1") {
        // json-simple incorrectly includes junit in compile scope
        exclude(group = "junit", module = "junit")
    }
    api("commons-io:commons-io:2.22.0")
    api("jakarta.servlet:jakarta.servlet-api:6.2.0-M2")
    api("org.slf4j:slf4j-api:2.0.20")

    // JSON processing: jackson
    api("tools.jackson.core:jackson-core:$jacksonVersion")
    api("com.fasterxml.jackson.core:jackson-annotations:2.22")
    api("tools.jackson.core:jackson-databind:$jacksonVersion")

    api("io.swagger.core.v3:swagger-annotations-jakarta:2.2.55")
    api("jakarta.ws.rs:jakarta.ws.rs-api:5.0.0-M1")

    api("org.glassfish.jersey.core:jersey-client:$jerseyVersion")
    api("org.glassfish.jersey.media:jersey-media-multipart:$jerseyVersion")
    api("org.glassfish.jersey.media:jersey-media-json-jackson:$jerseyVersion") {
        exclude(group = "com.fasterxml.jackson.core", module = "jackson-databind")
        exclude(group = "com.fasterxml.jackson.core", module = "jackson-core")
    }

    // Base64 encoding that works in both JVM and Android
    api("com.brsanthu:migbase64:2.2")

    // Tests
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.mockito:mockito-core:5.24.0")
    testImplementation("org.hamcrest:hamcrest:3.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:$junitVersion")
}

tasks.test {
    useJUnitPlatform()
    jvmArgs("-XX:+EnableDynamicAgentLoading", "-Duser.timezone=GMT-08:00")
}

// maven-assembly-plugin (jar-with-dependencies) -> shadow jar
tasks.shadowJar {
    archiveBaseName = "Xero-Java-SDK"
    archiveClassifier = "jar-with-dependencies"
}
tasks.assemble { dependsOn(tasks.shadowJar) }

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifactId = "xero-java"
            pom {
                name = "xero-java"
                description = project.description
                url = "https://github.com/XeroAPI/Xero-Java"
                licenses {
                    license {
                        name = "MIT License"
                        url = "http://www.opensource.org/licenses/mit-license.php"
                    }
                }
                developers {
                    developer {
                        name = "Xero API Team"
                        email = "api@xero.com"
                        organization = "Xero"
                        organizationUrl = "https://developer.xero.com"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/XeroAPI/Xero-Java.git"
                    developerConnection = "scm:git:ssh://github.com:XeroAPI/Xero-Java.git"
                    url = "http://github.com/XeroAPI/Xero-Java/tree/master"
                }
            }
        }
    }
    repositories {
        maven {
            name = "ossrh"
            val releases = uri("https://ossrh-staging-api.central.sonatype.com/service/local/staging/deploy/maven2/")
            val snapshots = uri("https://ossrh-staging-api.central.sonatype.com/content/repositories/snapshots")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshots else releases
            credentials {
                // Put these in ~/.gradle/gradle.properties (replaces the "ossrh" entry in ~/.m2/settings.xml)
                username = providers.gradleProperty("ossrhUsername").orNull
                password = providers.gradleProperty("ossrhPassword").orNull
            }
        }
    }
}

// maven-gpg-plugin with --pinentry-mode loopback: sign using the gpg command line tool.
signing {
    useGpgCmd()
    sign(publishing.publications["mavenJava"])
}
// Don't require a GPG key for local builds; signing only applies when publishing.
tasks.withType<Sign> {
    onlyIf { gradle.taskGraph.hasTask(":publish") || gradle.taskGraph.allTasks.any { it.name.startsWith("publish") } }
}
