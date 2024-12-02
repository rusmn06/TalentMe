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
import com.example.talentme.databinding.ActivityMajorRecomendationBinding
import com.example.talentme.databinding.ActivityTestPassionBinding
import com.example.talentme.databinding.ActivityTestTalentBinding
import com.example.talentme.form.Question
import com.example.talentme.form.QuestionAdapter
import com.example.talentme.ui.result.talent.ResultTalentActivity

class MajorRecomendationActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recomendationAdapter: RecomendationAdapter
    private lateinit var recomedationList: List<Recomendation>
    private lateinit var binding: ActivityMajorRecomendationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMajorRecomendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recomendationRv)
        recyclerView.layoutManager = LinearLayoutManager(this)


        recomedationList = listOf(
            Recomendation("Kesehatan Masyarakat", "Sector : Kesehatan", "Universitas Mulawarman"),
            Recomendation("Ilmu Komputer", "Sector : Teknologi", "Universitas Lambung Mangkurat"),
            Recomendation("Pendidikan Kimia", "Sector : Pendidikan", "Universitas Lambung Mangkurat"),
            Recomendation("Hukum", "Sector : Hukum", "Universitas Lambung Mangkurat"),
            Recomendation("Kesehatan Masyarakat", "Sector : Kesehatan", "Universitas Lambung Mangkurat"),
            Recomendation("Kesehatan Masyarakat", "Sector : Kesehatan", "Universitas Mulawarman"),
            Recomendation("Kesehatan Masyarakat", "Sector : Kesehatan", "Universitas Mulawarman")
        )

        recomendationAdapter = RecomendationAdapter(recomedationList)
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
