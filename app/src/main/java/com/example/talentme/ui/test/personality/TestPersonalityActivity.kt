package com.example.talentme.ui.test.personality

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.talentme.R
import com.example.talentme.databinding.ActivityTestPassionBinding
import com.example.talentme.databinding.ActivityTestPersonalityBinding
import com.example.talentme.form.Question
import com.example.talentme.form.QuestionAdapter
import com.example.talentme.ui.result.personality.ResultPersonalityActivity

class TestPersonalityActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var questionAdapter: QuestionAdapter
    private lateinit var questionList: List<Question>
    private lateinit var binding: ActivityTestPersonalityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTestPersonalityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recycler_view) // Pastikan ID ini sesuai dengan layout Anda
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Membuat daftar pertanyaan
        questionList = listOf(
            Question("Jika kamu bisa menggunakan mesin waktu, lebih baik kamu pergi ke", "Masa Lalu 1", "Masa Depan 1", "Saya akan menjual mesin tersebut 1"),
            Question("Jika kamu bisa menggunakan mesin waktu, lebih baik kamu pergi ke", "Masa Lalu 2", "Masa Depan 2", "Saya akan menjual mesin tersebut 2"),
            Question("Jika kamu bisa menggunakan mesin waktu, lebih baik kamu pergi ke", "Masa Lalu 1", "Masa Depan 1", "Saya akan menjual mesin tersebut 1"),
            Question("Jika kamu bisa menggunakan mesin waktu, lebih baik kamu pergi ke", "Masa Lalu 1", "Masa Depan 1", "Saya akan menjual mesin tersebut 1"),
            Question("Jika kamu bisa menggunakan mesin waktu, lebih baik kamu pergi ke", "Masa Lalu 1", "Masa Depan 1", "Saya akan menjual mesin tersebut 1"),
            Question("Jika kamu bisa menggunakan mesin waktu, lebih baik kamu pergi ke", "Masa Lalu 1", "Masa Depan 1", "Saya akan menjual mesin tersebut 1"),
            Question("Jika kamu bisa menggunakan mesin waktu, lebih baik kamu pergi ke", "Masa Lalu 1", "Masa Depan 1", "Saya akan menjual mesin tersebut 1"),
            Question("Jika kamu bisa menggunakan mesin waktu, lebih baik kamu pergi ke", "Masa Lalu 1", "Masa Depan 1", "Saya akan menjual mesin tersebut 1"),
            Question("Jika kamu bisa menggunakan mesin waktu, lebih baik kamu pergi ke", "Masa Lalu 1", "Masa Depan 1", "Saya akan menjual mesin tersebut 1"),
            Question("Jika kamu bisa menggunakan mesin waktu, lebih baik kamu pergi ke", "Masa Lalu 1", "Masa Depan 1", "Saya akan menjual mesin tersebut 1"),
            Question("Jika kamu bisa menggunakan mesin waktu, lebih baik kamu pergi ke", "Masa Lalu 1", "Masa Depan 1", "Saya akan menjual mesin tersebut 1"),
            Question("Jika kamu bisa menggunakan mesin waktu, lebih baik kamu pergi ke", "Masa Lalu 1", "Masa Depan 1", "Saya akan menjual mesin tersebut 1"),
            Question("Jika kamu bisa menggunakan mesin waktu, lebih baik kamu pergi ke", "Masa Lalu 1", "Masa Depan 1", "Saya akan menjual mesin tersebut 1"),

            )

        questionAdapter = QuestionAdapter(questionList)
        recyclerView.adapter = questionAdapter

        // Mengatur padding untuk sistem bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.continueButton.setOnClickListener {
            val intent = Intent(this, ResultPersonalityActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
