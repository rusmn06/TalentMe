package com.example.talentme.data.model

import com.google.gson.annotations.SerializedName

data class PredictRequest(
    @SerializedName("input_data")
    val inputData: List<List<Int>>
)
