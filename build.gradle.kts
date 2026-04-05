plugins {
    id("java")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)  // принудительно используем Java 17
    }
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.postgresql:postgresql:42.7.1")
    implementation("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")
    implementation("com.google.code.gson:gson:2.13.2")
    implementation("org.reflections:reflections:0.10.2")
    implementation("javax.annotation:jsr250-api:1.0")
    implementation("org.slf4j:slf4j-simple:2.0.17")
    implementation("org.jboss:jboss-vfs:3.3.2.Final")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}



tasks.test {
    useJUnitPlatform()
}