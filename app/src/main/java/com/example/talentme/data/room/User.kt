package com.example.talentme.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.sql.Date

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey
    val id: Int,
    val name: String,
    val gender: String,
    val birthdate: String,
    val email: String,
    val password: String
)
