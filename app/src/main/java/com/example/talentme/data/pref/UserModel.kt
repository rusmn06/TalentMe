package com.example.talentme.data.pref

data class UserModel (
    val id : Int,
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)