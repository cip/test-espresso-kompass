# Espresso Kompass ☕

Eine offline nutzbare Android-App auf Deutsch mit neun Schritten für Espresso aus einer Siebträgermaschine. Enthält Fortschrittsanzeige, Vor-/Zurück-Navigation, Tipps, 30-Sekunden-Timer und App-Version.

## Startrezept
18 g Kaffee → etwa 36 g Espresso in 25–30 Sekunden ab Pumpenstart. Werte sind Richtwerte; Bohnen, Sieb und Geschmack variieren.

## Bauen
Mit JDK 17, Android SDK 35 und Gradle 8.10.2:
```sh
gradle :app:testDebugUnitTest
gradle :app:assembleDebug
```
APK: `app/build/outputs/apk/debug/app-debug.apk`. Alternativ startet GitHub Actions auf Push/PR oder manuell und stellt das Debug-APK als Artifact bereit.

## Datenschutz
Offline; keine Konten, Tracking-SDKs oder Netzwerkberechtigungen.

## Status
Build und Gerätetest sind vorerst nicht bestätigt. Bitte GitHub Actions prüfen.
