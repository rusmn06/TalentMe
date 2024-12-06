package com.example.talentme.data.model

import android.content.Context
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer

class TfliteModel(private val context: Context) {
    /*val model = TfliteModel.newInstance(context)
    fun loadModel(): TfliteModel? {
        return try {
            TfliteModel.newInstance(context)
        } catch (e: Exception) {
            null
        }
    }

    // Menjalankan inferensi dan mengembalikan hasil
    fun runInference(byteBuffer: ByteBuffer): FloatArray? {
        val model = loadModel() ?: return null

        // Membuat tensor input
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 25), DataType.FLOAT32)
        inputFeature0.loadBuffer(byteBuffer)

        // Menjalankan inferensi
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        // Mengembalikan hasil output
        return outputFeature0.floatArray
    }*/
}
