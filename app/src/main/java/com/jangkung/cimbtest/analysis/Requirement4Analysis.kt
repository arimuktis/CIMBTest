package com.jangkung.cimbtest.analysis

import android.app.Activity
import android.content.Context
import java.lang.ref.WeakReference

/**
 * REQUIREMENT 4 — Analysis of Singleton Activity Reference
 *
 * THE PROVIDED CODE:
 * =========================================
 * object UserManager {
 *     var activity: Activity? = null
 *     fun setActivity(activity: Activity) {
 *         this.activity = activity
 *     }
 * }
 * =========================================
 *
 * 1. PROBLEM / MASALAH DARI KODE TERSEBUT:
 * `UserManager` adalah sebuah Singleton (menggunakan keyword `object` di Kotlin), yang berarti instance-nya
 * akan hidup terus selama proses aplikasi berjalan di memori.
 * Menyimpan referensi `Activity` di dalam Singleton akan menyebabkan `Activity` tersebut tidak bisa di-Garbage Collect (dihapus dari memori)
 * ketika ia sudah dihancurkan (misalnya saat user menekan tombol Back, atau saat rotasi layar / configuration change).
 *
 * 2. RISIKO YANG DAPAT TERJADI:
 * - **Memory Leak**: Memori akan bocor karena `Activity` yang seharusnya sudah mati tetap dipegang oleh `UserManager`. 
 *   Bukan hanya Activity-nya saja, tetapi seluruh View Hierarchy (layout), Image, Bitmap, dan Context yang terikat pada Activity tersebut 
 *   akan ikut tertahan di memori.
 * - **OutOfMemoryError (OOM)**: Jika leak ini terjadi berulang kali (misalnya user bolak-balik buka-tutup halaman), 
 *   memori HP akan penuh dan aplikasi akan force close / crash.
 * - **NullPointerException / Crash**: Jika Singleton mencoba mengakses UI dari Activity yang sebenarnya statusnya sudah `onDestroy`, 
 *   aplikasi akan crash.
 *
 * 3. CARA MEMPERBAIKINYA:
 * Ada beberapa cara tergantung pada kebutuhannya:
 *
 * Solusi A (Paling disarankan): Jangan menyimpan Activity di level global/singleton sama sekali.
 * Jika butuh context secara global, gunakan ApplicationContext.
 * ```kotlin
 * class UserManager(context: Context) {
 *     private val appContext = context.applicationContext // Aman dari memory leak
 * }
 * ```
 *
 * Solusi B (Pass as Parameter): Jika memang butuh operasi yang melibatkan Activity (seperti menampilkan dialog), 
 * jangan di-simpan sebagai state/variabel, cukup pass sebagai parameter di fungsinya.
 * ```kotlin
 * object UserManager {
 *     fun showSomething(activity: Activity) {
 *         // Gunakan activity di sini saja, jangan simpan di variabel global
 *     }
 * }
 * ```
 *
 * Solusi C (WeakReference): Jika benar-benar terpaksa harus menyimpannya (jarang terjadi di arsitektur modern),
 * gunakan `WeakReference` agar GC tetap bisa menghapus Activity saat dibutuhkan.
 * ```kotlin
 * object UserManager {
 *     private var activityRef: WeakReference<Activity>? = null
 *     
 *     fun setActivity(activity: Activity) {
 *         activityRef = WeakReference(activity)
 *     }
 * }
 * ```
 */
