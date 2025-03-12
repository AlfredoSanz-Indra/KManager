import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.20-RC"
}

kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.7.3")
            runtimeOnly("androidx.collection:collection:1.4.5")

            //MONGODB
            implementation("org.mongodb:mongodb-driver-kotlin-coroutine:5.3.0")
            implementation("org.mongodb:bson-kotlinx:5.3.0")

            //LOGGING
            implementation("org.slf4j:slf4j-api:2.0.17")
            implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
            implementation("ch.qos.logback:logback-classic:1.5.17")
        }
    }
}


compose.desktop {
    application {
        mainClass = "es.alfred.kmanager.MainKt"

        nativeDistributions {
            includeAllModules = true
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "KManager"
            packageVersion = "1.0.12"
        }
    }
}
