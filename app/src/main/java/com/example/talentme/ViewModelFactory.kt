package com.example.talentme

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.talentme.data.repository.RecomendationRepository
import com.example.talentme.data.repository.UserRepository
import com.example.talentme.di.Injection
import com.example.talentme.ui.home.HomeViewModel
import com.example.talentme.ui.login.LoginViewModel
import com.example.talentme.ui.profile.ProfileActivityViewModel
import com.example.talentme.ui.register.RegisterViewModel

import com.example.talentme.ui.result.passion.ResultPassionViewModel
import com.example.talentme.ui.test.talent.TestTalentViewModel

class ViewModelFactory(private val userRepository: UserRepository, private val recomendationRepository: RecomendationRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainActivityViewModel::class.java) -> {
                MainActivityViewModel(recomendationRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(ResultPassionViewModel::class.java) -> {
                ResultPassionViewModel(recomendationRepository) as T
            }
            modelClass.isAssignableFrom(TestTalentViewModel::class.java) -> {
                TestTalentViewModel(recomendationRepository) as T
            }
            modelClass.isAssignableFrom(ProfileActivityViewModel::class.java) -> {
                ProfileActivityViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    val userRepository = Injection.provideRepository(context)
                    val  recomendationRepository = Injection.provideRecomendationRepository(context)
                    INSTANCE = ViewModelFactory(userRepository = userRepository, recomendationRepository = recomendationRepository)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}