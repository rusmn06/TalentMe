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
import com.example.talentme.data.room.User
import com.example.talentme.data.room.UserDao
import kotlinx.coroutines.flow.Flow


class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: UserApiService,
    private val userDao: UserDao,

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
    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }
    suspend fun insert(user: User) = userDao.insert(user)
    suspend fun getAllUsers(): List<User> = userDao.getAllUsers()
    suspend fun delete(user: User) = userDao.delete(user)
    suspend fun getUserById(userId: Int): User? = userDao.getUserById(userId)

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: UserApiService,
            userDao: UserDao
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService, userDao)
            }.also { instance = it }
    }
}