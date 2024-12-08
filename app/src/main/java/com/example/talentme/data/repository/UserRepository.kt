package com.example.talentme.data.repository

import com.example.talentme.data.model.LoginRequest
import com.example.talentme.data.model.RegisterRequest
import com.example.talentme.data.pref.UserModel
import com.example.talentme.data.pref.UserPreference
import com.example.talentme.data.response.GetUserByIdResponse
import com.example.talentme.data.response.LoginResponse
import com.example.talentme.data.response.LoginUserResponse
import com.example.talentme.data.response.RegisterResponse
import com.example.talentme.data.response.RegisterUserResponse
import com.example.talentme.data.retrofit.ApiService
import com.example.talentme.data.retrofit.UserApiService


class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: UserApiService
) {
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }
    suspend fun register(name: String, gender : String, birthDate : String, email: String, password: String): RegisterUserResponse{
        val register = RegisterRequest(name, gender, birthDate, email, password)
        return apiService.registerUser(register)
    }
    suspend fun login(email: String, password: String): LoginUserResponse {
        val login = LoginRequest(email, password)
        return apiService.loginUser(login)
    }
    suspend fun getUserbyId(id: Int): GetUserByIdResponse {
        return apiService.getUserById(id)
    }
    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: UserApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}