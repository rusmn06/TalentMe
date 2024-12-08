package com.example.talentme.data.retrofit

import com.example.talentme.data.model.LoginRequest
import com.example.talentme.data.model.RegisterRequest
import com.example.talentme.data.response.GetAllUserResponse
import com.example.talentme.data.response.GetUserByIdResponse
import com.example.talentme.data.response.LoginResponse
import com.example.talentme.data.response.LoginUserResponse
import com.example.talentme.data.response.RegisterResponse
import com.example.talentme.data.response.RegisterUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService {
    @GET("")
    suspend fun getAllUser(): GetAllUserResponse
    @GET("{idUser}")
    suspend fun getUserById(
        @Path("idUser") userId: Int
    ): GetUserByIdResponse
    @POST("register")
    suspend fun registerUser(
        @Body registerRequest: RegisterRequest
    ): RegisterUserResponse
    @POST("login")
    suspend fun loginUser(
        @Body registerRequest: LoginRequest
    ): LoginUserResponse




}