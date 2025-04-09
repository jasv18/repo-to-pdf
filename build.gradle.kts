plugins {
    kotlin("jvm") version "2.1.10"
    application
}

group = "com.github.jasv18"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.pdfbox:pdfbox:3.0.4")
    implementation("com.github.ajalt.clikt:clikt:5.0.3")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(22)
}

application() {
    mainClass.set("com.github.jasv18.r2pdf.MainKt")
}