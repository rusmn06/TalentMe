package com.example.talentme.ui.recomendation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.talentme.MainActivity
import com.example.talentme.R
import com.example.talentme.data.response.ModelResponse
import com.example.talentme.databinding.ActivityMajorRecomendationBinding
import com.example.talentme.databinding.ActivityTestPassionBinding
import com.example.talentme.databinding.ActivityTestTalentBinding
import com.example.talentme.form.Question
import com.example.talentme.form.QuestionAdapter
import com.example.talentme.ui.result.talent.ResultTalentActivity

class MajorRecomendationActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recomendationAdapter: RecomendationAdapter
    private val recomendationList = mutableListOf<Recomendation>()
    private lateinit var binding: ActivityMajorRecomendationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMajorRecomendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recomendationRv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val result: ModelResponse? = intent.getParcelableExtra("PREDICTION_RESULT")
        result?.data?.predict?.sector?.forEach { sector ->
            sector?.university?.forEach { university ->
                recomendationList.add(
                    Recomendation(
                        major = university?.jurusan,
                        sector = "Sector: ${sector.name}",
                        university = university?.name
                    )
                )
            }
        }
        recomendationAdapter = RecomendationAdapter(recomendationList)
        recyclerView.adapter = recomendationAdapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.button2.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
