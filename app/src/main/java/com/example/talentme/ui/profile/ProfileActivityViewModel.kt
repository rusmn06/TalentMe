package com.example.talentme.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.talentme.data.pref.UserModel
import com.example.talentme.data.repository.UserRepository
import com.example.talentme.data.response.ErrorResponse
import com.example.talentme.data.response.GetUserByIdResponse
import com.example.talentme.data.room.User
import com.google.gson.Gson
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ProfileActivityViewModel(val userRepository: UserRepository) : ViewModel(){
    private val _getUserByIdResult = MutableLiveData<GetUserByIdResponse>()
    val getUserByIdResult: LiveData<GetUserByIdResponse> = _getUserByIdResult

    private val _getUserByIdRoom = MutableLiveData<User?>()
    val getUserByIdRoom: LiveData<User?> = _getUserByIdRoom

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private lateinit var response: GetUserByIdResponse


    fun GetUserById(id : Int) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                response = userRepository.getUserbyId(id)
                if (isActive) {
                    _getUserByIdResult.postValue(response)
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
    fun getUserById(userId: Int) {
        viewModelScope.launch {
            val user = userRepository.getUserById(userId)
            if (user != null) {
                _getUserByIdRoom.postValue(user)
            }
        }
    }
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
}