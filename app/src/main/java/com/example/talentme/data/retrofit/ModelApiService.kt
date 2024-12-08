package com.example.talentme.data.retrofit

import com.example.talentme.data.model.PredictRequest
import com.example.talentme.data.response.ModelResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ModelApiService {
    @POST("predict")
    suspend fun predictSector(
        @Body predictRequest: PredictRequest
    ): ModelResponse

}