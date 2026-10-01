# Android Kotlin Technical Test

Repository ini berisi hasil pengerjaan technical test Android Developer. Aplikasi dibangun menggunakan pendekatan arsitektur modern Android, yaitu MVVM, Clean Architecture, Jetpack Compose, serta Coroutines dan StateFlow untuk asynchronous dan state management.

## Requirement yang Dikerjakan

1. Filter Active Adult Users (Req 1)
- Logika filtering dipisah ke dalam UseCase (GetActiveAdultUserNamesUseCase).
- Aplikasi menyaring data user dengan kondisi isActive bernilai true dan usia minimal 18 tahun.
- Output berupa nama user yang diurutkan secara abjad.

2. MVVM, Coroutine, dan State Management (Req 2)
- Menggunakan ViewModel dan viewModelScope agar network request tidak melakukan blocking pada Main Thread.
- UI direpresentasikan melalui StateFlow dengan tiga state utama: Loading, Success, dan Error.
- Tampilan dibangun secara deklaratif menggunakan Jetpack Compose.

3. REST API dan Retrofit (Req 3)
- API request dikelola menggunakan Retrofit (UserApi) yang diimplementasikan di dalam layer Repository (UserRepositoryImpl).
- Terdapat request dinamis GET /users/{id} untuk mengambil detail user.
- Karena tidak ada real backend yang disediakan di soal, API disimulasikan secara lokal menggunakan Mockoon.

4. Fitur Pencarian (Req 5)
- Pencarian data di-handle oleh SearchUsersUseCase.
- Filter bersifat case-insensitive berdasarkan nama user.
- Jika kolom pencarian kosong, data akan kembali menampilkan seluruh list yang diurutkan secara default.

## Tech Stack
- Kotlin
- Jetpack Compose (Material 3)
- arsitektur MVVM (Model-View-ViewModel) + Clean Architecture
- Coroutines & Flow (StateFlow)
- Retrofit2 & OkHttp (Logging Interceptor)
- Gson

## Panduan Menjalankan Aplikasi

1. Persiapan Mockoon (Simulasi Backend)
Karena instruksi tes mengharuskan integrasi REST API, aplikasi ini bergantung pada Mockoon untuk menyimulasikan response server.
- Install aplikasi Mockoon.
- Import file mockoon_cimb_test.json yang ada di dalam repository ini.
- Jalankan server di Mockoon (port 3000).

2. Build dan Run Android
- Buka repository ini di Android Studio.
- Jalankan aplikasi menggunakan Android Emulator (secara default Retrofit di-set ke localhost emulator: http://10.0.2.2:3000/).
- Catatan: Jika ingin menggunakan physical device, pastikan mengubah BASE_URL di RetrofitInstance.kt menjadi IP address komputer lokal.

3. Pengujian Fitur
- Saat aplikasi terbuka, list user akan ditarik dari Mockoon dan langsung ter-filter di layar.
- Untuk menguji fungsi search, ketikkan nama pada kolom input teks.
- Untuk menguji API GET /users/{id}, tap pada salah satu card user di dalam list. Aplikasi akan memunculkan dialog berisi detail email yang ditarik secara spesifik melalui Retrofit.
