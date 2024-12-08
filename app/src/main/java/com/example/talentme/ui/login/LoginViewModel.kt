package com.example.talentme.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talentme.data.pref.UserModel
import com.example.talentme.data.repository.UserRepository
import com.example.talentme.data.response.ErrorResponse
import com.example.talentme.data.response.LoginResponse
import com.example.talentme.data.response.LoginUserResponse
import com.google.gson.Gson
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    private val _loginResult = MutableLiveData<LoginUserResponse>()
    val loginResult: LiveData<LoginUserResponse> = _loginResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private lateinit var response: LoginUserResponse

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                response = repository.login(email, password)
                if (isActive) {
                    _loginResult.postValue(response)
                }
                _errorMessage.value = null
            } catch (e: Exception) {
                if (isActive) {
                    val errorMessage = when (e) {
                        is retrofit2.HttpException -> {
                            val errorBody = e.response()?.errorBody()?.string()
                            val errorResponse = Gson().fromJson(errorBody, ErrorResponse::class.java)
                            errorResponse.message ?: "An unknown error occurred"
                        }
                        else -> e.message ?: "An unknown error occurred"
                    }
                    _errorMessage.postValue(errorMessage)
                }
            } finally {
                _isLoading.postValue(false)
            }
        }
    }
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}