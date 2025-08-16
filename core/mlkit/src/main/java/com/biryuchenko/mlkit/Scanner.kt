package com.biryuchenko.mlkit

import android.content.Context
import android.util.Log
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class Scanner {
    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_ALL_FORMATS,
        )
        .build()

    suspend fun startBarcodeScanSuspend(context: Context): String? {
        val scanner = GmsBarcodeScanning.getClient(context, options)
        val moduleInstall = ModuleInstall.getClient(context)
        val moduleInstallRequest = ModuleInstallRequest.newBuilder()
            .addApi(scanner)
            .build()

        // Запускаем корутину для отслеживания процесса установки.
        val installSuccess = suspendCancellableCoroutine<Boolean> { continuation ->
            moduleInstall.installModules(moduleInstallRequest)
                .addOnSuccessListener { response ->
                    if (response.areModulesAlreadyInstalled()) {
                        Log.d("Scanner", "Module already installed. Proceeding.")
                        continuation.resume(true)
                    } else {
                        // Модуль только что установлен, можно продолжить.
                        Log.d("Scanner", "Module just installed. Proceeding.")
                        continuation.resume(true)
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Scanner", "Failed to install module: ${e.message}", e)
                    continuation.resume(false)
                }
        }
        if (!installSuccess) {
            Log.d("Scanner", "Modules were just installed. Waiting 500ms before scanning.")
            delay(500)
        }
        // Если установка успешна, запускаем сканирование.
        if (installSuccess) {
            return suspendCancellableCoroutine { continuation ->
                scanner.startScan()
                    .addOnSuccessListener { barcode ->
                        // Возвращаем результат, когда сканирование успешно.
                        Log.d("Scanner", "Barcode scanned: ${barcode.rawValue}")
                        continuation.resume(barcode.rawValue)
                    }
                    .addOnCanceledListener {
                        // Возвращаем null при отмене.
                        Log.d("Scanner", "Scanning was canceled by user.")
                        continuation.resume(null)
                    }
                    .addOnFailureListener { e ->
                        // Возвращаем исключение при ошибке.
                        Log.e("Scanner", "Failed to scan code: ${e.message}", e)
                        continuation.resumeWithException(e)
                    }
            }
        } else {
            return null
        }
    }
}
