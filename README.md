# Vaxcare Interview - Book Explorer

A modern Android application demonstrating Clean Architecture, MVI design pattern, and offline-first data management using Jetpack Compose.

## 🚀 Features

- **Book List**: Displays a collection of books retrieved from a remote API.
- **Offline Support**: Uses Room database to cache book data, allowing for seamless offline viewing.
- **Book Details**: Detailed view for each book including author, current status (OnShelf/CheckedOut), fees, and relevant dates.
- **MVI Architecture**: State-driven UI using a robust `BaseViewModel` with strictly defined States, Events, and Side Effects.
- **Favorites**: Mark books as favorites. Status is persisted locally and preserved even after refreshing data from the network.
- **Type-Safe Navigation**: Leverages the latest Jetpack Navigation with Kotlin Serialization for compile-time safety.
- **Sorting**: Toggle between Ascending (A-Z) and Descending (Z-A) title sorting.
- **Refresh Capability**: Pull-to-refresh implementation to synchronize local data with the remote source.

## 🛠 Tech Stack

- **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose) (100% Kotlin)
- **Architecture**: Clean Architecture (Domain, Data, Presentation layers)
- **Design Pattern**: MVI (Model-View-Intent)
- **Dependency Injection**: [Hilt](https://dagger.dev/hilt/) with **KSP** (Kotlin Symbol Processing)
- **Networking**: [Ktor Client](https://ktor.io/) with OkHttp engine
- **Local Persistence**: [Room Database](https://developer.android.com/training/data-storage/room)
- **Navigation**: [Compose Navigation](https://developer.android.com/jetpack/compose/navigation) (Type-Safe API)
- **Serialization**: [Kotlinx Serialization](https://kotlinlang.org/docs/serialization.html)
- **Asynchronous Work**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
- **Testing**:
    - **Unit Testing**: [MockK](https://mockk.io/), [Coroutines Test](https://kotlinlang.org/docs/optimizing-coroutines-tests.html), JUnit4
    - **UI Testing**: Compose UI Test library

## 📂 Project Structure

- `data/`: Contains Repository implementations, Local (Room) and Remote (Ktor) data sources, and DTOs.
- `domain/`: Pure business logic containing Repository interfaces, Use Cases, and Domain Models.
- `presentation/`: Compose Screens, ViewModels, and MVI Contracts.
- `di/`: Hilt Modules for providing application-wide dependencies.

## 🧪 Testing

The project maintains high code coverage across all layers:
- **ViewModels**: Verified state transitions and side-effect production.
- **Repositories**: Tested data coordination and mapping logic.
- **Remote Service**: Validated API parsing using Ktor's `MockEngine`.
- **UI**: Verified interaction and rendering via instrumented Compose tests.

Run tests using:
- `./gradlew test` (Unit Tests)
- `./gradlew connectedAndroidTest` (UI Tests)
