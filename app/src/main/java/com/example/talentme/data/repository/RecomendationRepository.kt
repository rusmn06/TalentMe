package com.example.talentme.data.repository

import com.example.talentme.data.pref.UserModel
import com.example.talentme.data.pref.UserPreference
import com.example.talentme.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class RecomendationRepository private constructor(private var apiService: ApiService, private val preference: UserPreference) {

    fun updateApiService(newApiService: ApiService) {
        apiService = newApiService
    }
    fun getSession(): Flow<UserModel> {
        return preference.getSession()
    }
    suspend fun logout() {
        preference.logout()
    }


    companion object {
        @Volatile
        private var instance: RecomendationRepository? = null

        fun getInstance(apiService: ApiService, preference: UserPreference): RecomendationRepository =
            instance ?: synchronized(this) {
                instance ?: RecomendationRepository(apiService, preference).also { instance = it }
            }
    }
}