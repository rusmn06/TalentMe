package com.example.talentme.data.pref

data class UserModel (
    val name: String,
    val email: String,
    val birthdate: String,
    val gender: String,
    val token: String,
    val isLogin: Boolean = false
)