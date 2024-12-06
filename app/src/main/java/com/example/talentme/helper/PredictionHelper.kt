package com.example.talentme.helper

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.example.talentme.R
import com.google.android.gms.tflite.client.TfLiteInitializationOptions
import com.google.android.gms.tflite.gpu.support.TfLiteGpu
import com.google.android.gms.tflite.java.TfLite
import org.tensorflow.lite.InterpreterApi
import org.tensorflow.lite.gpu.GpuDelegateFactory
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class PredictionHelper(
    private val modelName: String = "tflite_model.tflite",
    val context: Context,
    var onResult: (String) -> Unit,
    var onError: (String) -> Unit,
) {
    private var isGPUSupported: Boolean = false
    private var interpreter: InterpreterApi? = null
    private val sectors = listOf(
        "IT Sector",
        "Government Sector",
        "Health Sector",
        "Education Sector",
        "Sports Sector",
        "Finance Sector",
        "Entertainment Sector"
    )

    init {
        TfLiteGpu.isGpuDelegateAvailable(context).onSuccessTask { gpuAvailable ->
            val optionsBuilder = TfLiteInitializationOptions.builder()
            if (gpuAvailable) {
                optionsBuilder.setEnableGpuDelegateSupport(true)
                isGPUSupported = true
            }
            TfLite.initialize(context, optionsBuilder.build())
        }.addOnSuccessListener {
            loadLocalModel()
        }.addOnFailureListener {
            onError(context.getString(R.string.tflite_is_not_initialized_yet))
        }
    }
    private fun getPredictionResult(outputArray: FloatArray): String {
        // Menentukan indeks dengan nilai terbesar
        val maxIndex = outputArray.indices.maxByOrNull { outputArray[it] } ?: -1

        // Mengembalikan nama sektor berdasarkan indeks
        return if (maxIndex != -1) {
            sectors[maxIndex] // Mengambil sektor berdasarkan indeks dengan nilai terbesar
        } else {
            "Unknown Sector"
        }
    }

    fun predict(inputString: String) {
        if (interpreter == null) {
            onError("Interpreter is not loaded")
            return
        }

        // Parsing inputString menjadi array 2D jika inputString berupa string array
        val inputArray = parseInputArray(inputString)

        // Sesuaikan ukuran array output sesuai dengan ukuran output model
        val outputArray = Array(1) { FloatArray(7) } // Sesuaikan dengan ukuran output model yang benar

        try {
            interpreter?.run(inputArray, outputArray)
            val result = getPredictionResult(outputArray[0])
            onResult(result)
        } catch (e: Exception) {
            onError("Error during prediction: ${e.message}")
            Log.e(TAG, e.message.toString())
        }
    }

    // Fungsi untuk parsing input string yang berformat array
    private fun parseInputArray(inputString: String): Array<FloatArray> {
        val cleanedInput = inputString.replace("[[", "").replace("]]", "") // Hapus tanda kurung luar
        val rows = cleanedInput.split("], [") // Pisah berdasarkan baris array
        return rows.map { row ->
            row.split(",").map { it.trim().toFloat() }.toFloatArray() // Konversi setiap elemen menjadi Float
        }.toTypedArray()
    }



    private fun loadLocalModel() {
        try {
            val buffer: ByteBuffer = loadModelFile(context.assets, modelName)
            initializeInterpreter(buffer)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            onError("Failed to load the model")
        }
    }

    private fun initializeInterpreter(model: Any) {
        interpreter?.close()
        try {
            val options = InterpreterApi.Options()
                .setRuntime(InterpreterApi.Options.TfLiteRuntime.FROM_SYSTEM_ONLY)
            if (isGPUSupported) {
                options.addDelegateFactory(GpuDelegateFactory())
            }
            if (model is ByteBuffer) {
                interpreter = InterpreterApi.create(model, options)
            }
        } catch (e: Exception) {
            onError(e.message.toString())
            Log.e(TAG, "Error initializing interpreter: ${e.message}")
        }
    }

    fun close() {
        interpreter?.close()
    }

    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        assetManager.openFd(modelPath).use { fileDescriptor ->
            FileInputStream(fileDescriptor.fileDescriptor).use { inputStream ->
                val fileChannel = inputStream.channel
                val startOffset = fileDescriptor.startOffset
                val declaredLength = fileDescriptor.declaredLength
                return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
            }
        }
    }

    // This method parses the input string into an appropriate input format for the model.
    private fun parseInput(inputString: String): FloatArray {
        // Assuming the inputString is a CSV or space-separated list of numbers
        // Example inputString: "[0, 0, 0, 0, 1, 0, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 0, 0, 1]"
        val cleanedInput = inputString
            .replace("[", "")
            .replace("]", "")
            .replace(" ", "")

        // Convert it to an array of floats
        val inputArray = cleanedInput.split(",").map { it.toFloat() }.toFloatArray()

        return inputArray
    }

    companion object {
        private const val TAG = "PredictionHelper"
    }
}
