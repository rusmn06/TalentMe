package com.example.talentme.data.repository

import com.example.talentme.data.pref.UserModel
import com.example.talentme.data.pref.UserPreference
import com.example.talentme.data.response.LoginResponse
import com.example.talentme.data.response.RegisterResponse
import com.example.talentme.data.retrofit.ApiService


class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }
    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return apiService.register(name, email, password)
    }
    suspend fun login(email: String, password: String): LoginResponse {
        return apiService.login(email, password)
    }
    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}