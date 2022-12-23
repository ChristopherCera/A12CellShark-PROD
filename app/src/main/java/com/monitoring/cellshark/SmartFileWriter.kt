package com.monitoring.cellshark

import android.os.Environment
import android.util.Log
import com.opencsv.CSVWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException

class SmartFileWriter {

    private var fileName: String? = null

    fun writeDirectories() {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val metricsDir = File("$path/Smart/Metrics_Data/")
        val serialDir = File("$path/Smart/Serial_Data/")
        if (!metricsDir.exists()) metricsDir.mkdirs()
        if (!serialDir.exists()) serialDir.mkdirs()
    }

    fun writeToFile(content: String) {

        val data = arrayOf("This", "Is", "a", "test")
        val path: File =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val newDir = File("$path/$fileName")
        try {
            if (!newDir.exists()) {
                newDir.mkdir()
            }

            val fw = FileWriter(File(newDir.absolutePath, "test.csv"), true)
            val cw = CSVWriter(fw)
            cw.writeNext(data)
            cw.close()
//            val writer = FileOutputStream(File(newDir.absolutePath + "test.txt"))
//            writer.write(content.toByteArray())
//            writer.close()
            Log.e("TAG", "Wrote to file: $fileName")
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun changeFileName() {

    }


}