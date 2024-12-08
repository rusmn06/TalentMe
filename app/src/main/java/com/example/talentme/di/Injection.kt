package com.example.talentme.di

import android.content.Context
import com.example.talentme.data.pref.UserPreference
import com.example.talentme.data.pref.dataStore
import com.example.talentme.data.repository.RecomendationRepository
import com.example.talentme.data.repository.UserRepository
import com.example.talentme.data.retrofit.ApiConfig
import com.example.talentme.data.retrofit.ModelApiConfig
import com.example.talentme.data.retrofit.UserApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = UserApiConfig.getUserApiService()
        return UserRepository.getInstance(pref, apiService)
    }
    fun provideRecomendationRepository(context: Context): RecomendationRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getUser().first() }
        val apiService = ModelApiConfig.getUserApiService()
        return RecomendationRepository.getInstance(apiService, pref)
    }

}