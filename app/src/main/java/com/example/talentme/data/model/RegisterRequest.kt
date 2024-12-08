package com.example.talentme.data.model

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("nama")
    val name: String,

    @SerializedName("jenis_kelamin")
    val gender: String,

    @SerializedName("tgl_lahir")
    val birthDate: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)