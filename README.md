# Android Kotlin Technical Test

This repository contains the completed Android Kotlin Technical Test application. It is built using modern Android development practices, including Jetpack Compose, Clean Architecture principles, MVVM, Coroutines, and StateFlow.

## 🚀 Features & Requirements Fulfilled

1. **Active Adult Users Filter (Req 1)**
   - Implemented in `GetActiveAdultUserNamesUseCase`.
   - Filters users based on `isActive == true` and `age >= 18`.
   - Results are mapped to names and sorted alphabetically.

2. **MVVM, Coroutines, and State Management (Req 2)**
   - Leverages `ViewModel` with `viewModelScope` to ensure network/data fetching is executed on a background thread without blocking the Main Thread.
   - Utilizes `StateFlow` to manage and observe UI States (`Loading`, `Success`, `Error`).
   - Implemented a clean UI using Jetpack Compose that reacts dynamically to state changes.

3. **REST API Integration with Retrofit (Req 3)**
   - API communication is handled cleanly via Retrofit (`UserApi`) and the Repository pattern (`UserRepository`).
   - Features dynamic HTTP GET requests to fetch complete user lists and specific user details by ID.
   - **Note:** The backend is simulated locally using Mockoon.

4. **Search Functionality (Req 5)**
   - Implemented in `SearchUsersUseCase`.
   - Performs a real-time, case-insensitive search based on the user's name.
   - Automatically resets to the full sorted list if the search keyword is blank.

## 🛠️ Tech Stack
* **Language:** Kotlin
* **UI Toolkit:** Jetpack Compose (Material 3)
* **Architecture:** MVVM (Model-View-ViewModel) + Clean Architecture approach (Domain, Data, Presentation layers)
* **Concurrency:** Kotlin Coroutines & Flow (StateFlow)
* **Networking:** Retrofit2 & OkHttp (with Logging Interceptor)
* **Serialization:** Gson

## ⚙️ How to Run & Test the Application

### 1. Mockoon Setup (Local Backend)
Since the test requires REST API integration without providing a live backend, the API is simulated locally using [Mockoon](https://mockoon.com/).

1. Download and install Mockoon.
2. Open Mockoon and click **Import/Export** > **Mockoon's format**.
3. Import the `mockoon_cimb_test.json` file located in the root folder of this project.
4. Click the **Play (▶️)** button in Mockoon to start the server on port `3000`.

### 2. Running the App
1. Open the project in **Android Studio**.
2. Run the application on an **Android Emulator** (the app is pre-configured to communicate with Mockoon via `http://10.0.2.2:3000/`).
3. *If testing on a physical device via tethering/hotspot, you must change `BASE_URL` in `RetrofitInstance.kt` to your laptop's actual IP address.*

### 3. Testing the Features
* **Req 1 & 2:** The main screen automatically loads the users from the API and displays them. The top card instantly shows the filtered active adult users.
* **Req 5:** Type any letter in the search bar to see the list filter in real-time.
* **Req 3:** Tap on any user card in the list to trigger an API call to `GET /users/{id}`, which will display an alert dialog containing the user's detailed profile (including their email).

---
*Developed as part of the Android Kotlin Technical Test.*
