package com.biryuchenko.apachepoi

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.biryuchenko.room.entities.ProductWithDocument
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun createExcelFile(context: Context, products: List<ProductWithDocument>, fileName: String) {

    // Создаём workbook один раз, чтобы избежать дублирования
    val workbook = createWorkbookWithTotal(products)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        // Логика для Android 10 (Q) и выше
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$fileName.xlsx")
            put(
                MediaStore.MediaColumns.MIME_TYPE,
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            )
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
        }

        val dstUri = context.contentResolver.insert(
            MediaStore.Downloads.EXTERNAL_CONTENT_URI,
            contentValues
        )

        if (dstUri != null) {
            try {
                // Открываем OutputStream и записываем в него данные workbook напрямую
                context.contentResolver.openOutputStream(dstUri)?.use { outputStream ->
                    workbook.write(outputStream)
                }
                println("Файл успешно сохранен в Загрузки (Android Q+): $fileName.xlsx")
            } catch (e: IOException) {

                context.contentResolver.delete(dstUri, null, null)
                println("Ошибка при сохранении: ${e.message}")
                e.printStackTrace()
            } finally {

                try {
                    workbook.close()
                    Toast.makeText(context, "Импортировано в загрузки", Toast.LENGTH_SHORT).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        } else {
            Toast.makeText(
                context,
                "Не удалось создать URI для сохранения файла.",
                Toast.LENGTH_SHORT
            ).show()
            println("Не удалось создать URI для сохранения файла.")
        }
    } else {
        // Логика для Android 9 (P) и ниже
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                val documentsDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                val file = File(documentsDir, "$fileName.xlsx")

                FileOutputStream(file).use { fileOut ->
                    workbook.write(fileOut)
                }
                Toast.makeText(context, "Импортировано в загрузки", Toast.LENGTH_SHORT).show()
                println("Файл успешно создан (Android < Q): ${file.absolutePath}")
            } catch (e: IOException) {
                println("Ошибка записи файла: ${e.message}")
                e.printStackTrace()
            } finally {
                try {
                    workbook.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        } else {

        }
    }
}

fun createWorkbookWithTotal(products: List<ProductWithDocument>): XSSFWorkbook {
    val workbook = XSSFWorkbook()
    val sheet = workbook.createSheet("Products")


    val headerRow = sheet.createRow(0)
    val headers = listOf("№", "Категория", "Имя", "Количество", "Цена", "Цена за единицу")
    headers.forEachIndexed { index, header ->
        headerRow.createCell(index).setCellValue(header)
    }


    var rowNum = 1
    products.forEach { product ->
        val row = sheet.createRow(rowNum++)
        row.createCell(0).setCellValue(product.product.uid.toDouble())
        row.createCell(1).setCellValue(product.product.category)
        row.createCell(2).setCellValue(product.product.name)
        row.createCell(3).setCellValue(product.product.quantity.toDouble())
        row.createCell(4).setCellValue(product.product.price.toDouble())
        row.createCell(5).setCellValue(product.product.priceForOne.toDouble())
    }


    val lastDataRow = rowNum - 1
    val totalRow = sheet.createRow(lastDataRow + 1)
    val totalLabelCell: Cell = totalRow.createCell(3)
    totalLabelCell.setCellValue("Total:")
    val totalValueCell: Cell = totalRow.createCell(4, CellType.FORMULA)
    val startCell = "E2"
    val endCell = "E${lastDataRow + 1}"
    totalValueCell.cellFormula = "SUM($startCell:$endCell)"

    val desiredWidthInChars = 17

    headers.forEachIndexed { index, _ ->
        sheet.setColumnWidth(index, desiredWidthInChars * 256)
    }

    return workbook
}