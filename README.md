# alset-pulse-android-kotlin
Alset Pulse Protocol Client

# ALSET Pulse SDK for Android

Cliente oficial de **ALSET Pulse Protocol v1.0** para Android/Kotlin.

## Instalación

1-Si usas JitPack:

repositories {
         google()
         mavenCentral()
         maven { url = uri("https://jitpack.io")
 }

dependencies {
    implementation 'com.github.yecharlot:alset-pulse-android-kotlin:1.0.0'
}

2-Si usas Maven

dependencies {
    implementation("com.alset:pulse:1.0.0")
}

##Uso básico

val client = AlsetPulseClient("https://server/pulse")

client.on("reloj-digital") { data ->
    textView.text = data.toString()
}

##Características

    Reconexión automática

    Soporte de heartbeat

    Mutaciones direccionables por target




##Uso básico
