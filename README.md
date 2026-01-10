# TodoK_MP

Application de gestion de réunions en Kotlin Multiplatform (Android/iOS).

## Stack technique

| Catégorie | Technologie | Version |
|-----------|-------------|---------|
| Langage | Kotlin | 2.3.0 |
| UI | Compose Multiplatform | 1.9.3 |
| Base de données | SQLDelight | 2.2.1 |
| DI | Koin | 4.1.1 |
| Async | Coroutines | 1.10.2 |
| Navigation | Navigation Compose | 2.9.1 |
| Date/Time | kotlinx-datetime | 0.7.1 |

## Architecture

```
┌─────────────────────────────────────────────────────────┐
│                      composeApp                         │
│              (Entry points + DI modules)                │
└─────────────────────────────────────────────────────────┘
         │                │                │
         ▼                ▼                ▼
┌─────────────┐   ┌─────────────┐   ┌─────────────┐
│     ui      │   │   domain    │   │    data     │
│  (MVVM +    │──▶│ (Use Cases  │◀──│ (Repository │
│  Compose)   │   │  + Entities)│   │  + SQLite)  │
└─────────────┘   └─────────────┘   └─────────────┘
```

**Pattern** : Clean Architecture + MVVM

## Structure des modules

```
├── composeApp/          # Points d'entrée Android/iOS + configuration DI
├── build-logic/         # Convention plugins Gradle
├── domain/              # Use cases, entités, interfaces repository
├── data/                # Implémentation repository, SQLDelight, mappers
└── ui/                  # ViewModels, Composables, états UI
```

## Convention Plugins

Configuration centralisée via `build-logic/` :

| Plugin | Description |
|--------|-------------|
| `todok.kmp.library` | Config KMP (Android + iOS targets, JVM 17) |

## Fonctionnalités

- Création de réunions (titre, sujet, salle, participants, date)
- Liste des réunions avec tri (nom, date)
- Détails d'une réunion
- Suppression automatique des réunions de +30 min

## Build

```bash
# Android
./gradlew :composeApp:assembleDebug

# iOS
# Ouvrir iosApp/ dans Xcode

# Tests
./gradlew :domain:allTests

# Coverage
./gradlew koverHtmlReport
```

## Tests

- **Framework** : kotlin-test, Mokkery, AssertK, Turbine
- **Couverture** : Domain layer (use cases)
- **Outil** : Kover

## Configuration requise

- Android SDK : minSdk 28, targetSdk 35, compileSdk 36
- Xcode (pour iOS)
- JDK 17+
