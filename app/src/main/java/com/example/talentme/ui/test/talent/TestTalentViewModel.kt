package com.example.talentme.ui.test.talent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talentme.data.model.PredictRequest
import com.example.talentme.data.pref.UserModel
import com.example.talentme.data.repository.RecomendationRepository
import com.example.talentme.data.response.ErrorResponse
import com.example.talentme.data.response.LoginUserResponse
import com.example.talentme.data.response.ModelResponse
import com.google.gson.Gson
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class TestTalentViewModel(val repository: RecomendationRepository) : ViewModel() {
    private val _predictResult = MutableLiveData<ModelResponse>()
    val loginResult: LiveData<ModelResponse> = _predictResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private lateinit var response: ModelResponse

    fun predict(predictRequest: PredictRequest){
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                response = repository.predict(predictRequest)
                if (isActive) {
                    _predictResult.postValue(response)
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
}