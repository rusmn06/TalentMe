package com.example.talentme.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ModelResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("error")
	val error: Boolean? = null
) : Parcelable

@Parcelize
data class DataModel(

	@field:SerializedName("predict")
	val predict: Predict? = null,

	@field:SerializedName("accuracy")
	val accuracy: Double? = null
) : Parcelable

@Parcelize
data class UniversityItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("jurusan")
	val jurusan: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("sector")
	val sector: String? = null
) : Parcelable

@Parcelize
data class Predict(

	@field:SerializedName("sector")
	val sector: List<SectorItem?>? = null
) : Parcelable

@Parcelize
data class SectorItem(

	@field:SerializedName("university")
	val university: List<UniversityItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable
