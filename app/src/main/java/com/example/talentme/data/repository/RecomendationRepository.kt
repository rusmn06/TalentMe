package com.example.talentme.data.repository

import com.example.talentme.data.model.PredictRequest
import com.example.talentme.data.pref.UserModel
import com.example.talentme.data.pref.UserPreference
import com.example.talentme.data.response.ModelResponse
import com.example.talentme.data.retrofit.ApiService
import com.example.talentme.data.retrofit.ModelApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class RecomendationRepository private constructor(private var apiService: ModelApiService, private val preference: UserPreference) {

    fun updateApiService(newApiService: ModelApiService) {
        apiService = newApiService
    }
    fun getSession(): Flow<UserModel> {
        return preference.getSession()
    }
    suspend fun logout() {
        preference.logout()
    }
    suspend fun predict(input: PredictRequest) : ModelResponse {
        return apiService.predictSector(input)
    }


    companion object {
        @Volatile
        private var instance: RecomendationRepository? = null

        fun getInstance(apiService: ModelApiService, preference: UserPreference): RecomendationRepository =
            instance ?: synchronized(this) {
                instance ?: RecomendationRepository(apiService, preference).also { instance = it }
            }
    }
}