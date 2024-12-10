package com.example.talentme.ui.test.talent

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.talentme.data.model.PredictRequest
import com.example.talentme.data.pref.UserModel
import com.example.talentme.data.repository.RecomendationRepository
import com.example.talentme.data.response.DataModel
import com.example.talentme.data.response.ErrorResponse
import com.example.talentme.data.response.LoginUserResponse
import com.example.talentme.data.response.ModelResponse
import com.example.talentme.data.response.Predict
import com.example.talentme.data.response.SectorItem
import com.example.talentme.data.response.UniversityItem
import com.google.gson.Gson
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class TestTalentViewModel(val repository: RecomendationRepository) : ViewModel() {
    private val _predictResult = MutableLiveData<ModelResponse>()
    val predictResult: LiveData<ModelResponse> = _predictResult

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
                Log.d("TestTalentViewModel", "API response: $response")

                // Cek apakah data kosong atau null
                if (response.error == false && (response.data?.predict?.sector.isNullOrEmpty() || response.data?.predict == null)) {
                    _predictResult.postValue(getDummyResponse())
                    Log.d("TestTalentViewModel", "Using dummy response")

                } else {
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
    fun getDummyResponse(): ModelResponse {
        // Data dummy untuk sektor IT
        val itUniversities = listOf(
            UniversityItem(
                name = "Tech University",
                description = "A university focused on technology",
                jurusan = "Computer Science",
                id = 1,
                sector = "IT"
            ),
            UniversityItem(
                name = "Digital Academy",
                description = "Specialized in digital transformation",
                jurusan = "Information Systems",
                id = 2,
                sector = "IT"
            )
        )
        val itSector = SectorItem(
            university = itUniversities,
            name = "IT Sector",
            id = 101
        )

        // Data dummy untuk sektor Kesehatan
        val healthUniversities = listOf(
            UniversityItem(
                name = "Health Institute",
                description = "A leading institute for health sciences",
                jurusan = "Public Health",
                id = 3,
                sector = "Health"
            ),
            UniversityItem(
                name = "Medical University",
                description = "Top university for medicine and healthcare",
                jurusan = "Medicine",
                id = 4,
                sector = "Health"
            )
        )
        val healthSector = SectorItem(
            university = healthUniversities,
            name = "Health Sector",
            id = 102
        )

        // Data dummy untuk sektor Pendidikan
        val educationUniversities = listOf(
            UniversityItem(
                name = "Education Academy",
                description = "Specialized in education sciences",
                jurusan = "Educational Psychology",
                id = 5,
                sector = "Education"
            ),
            UniversityItem(
                name = "Teaching University",
                description = "Focused on teacher training programs",
                jurusan = "Chemistry Education",
                id = 6,
                sector = "Education"
            )
        )
        val educationSector = SectorItem(
            university = educationUniversities,
            name = "Education Sector",
            id = 103
        )

        // Data dummy untuk sektor Teknik
        val engineeringUniversities = listOf(
            UniversityItem(
                name = "Engineering Institute",
                description = "A top institute for engineering and innovation",
                jurusan = "Mechanical Engineering",
                id = 7,
                sector = "Engineering"
            ),
            UniversityItem(
                name = "Techno Engineering",
                description = "Leading in modern engineering technology",
                jurusan = "Civil Engineering",
                id = 8,
                sector = "Engineering"
            )
        )
        val engineeringSector = SectorItem(
            university = engineeringUniversities,
            name = "Engineering Sector",
            id = 104
        )

        // Gabungkan semua sektor
        val allSectors = listOf(itSector, healthSector, educationSector, engineeringSector)

        // Buat prediksi
        val predict = Predict(
            sector = allSectors
        )

        // Buat model data
        val dataModel = DataModel(
            predict = predict,
            accuracy = 0.92 // Contoh tingkat akurasi prediksi
        )

        // Return ModelResponse
        return ModelResponse(
            data = dataModel,
            message = "Prediction successful",
            error = false
        )
    }


}