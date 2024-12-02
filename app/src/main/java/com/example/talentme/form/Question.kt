package com.example.talentme.form

data class Question(
    val questionText: String,
    val buttonFirstText: String,
    val buttonSecondText: String,
    val buttonThirdText: String,
    var selectedOptionIndex: Int? = null
)
