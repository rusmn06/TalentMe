package com.example.talentme.ui.result.talent

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.talentme.MainActivity
import com.example.talentme.R
import com.example.talentme.data.response.ModelResponse
import com.example.talentme.databinding.ActivityResultTalentBinding
import com.example.talentme.ui.recomendation.MajorRecomendationActivity

class ResultTalentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultTalentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultTalentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val result: ModelResponse? = intent.getParcelableExtra("PREDICTION_RESULT")
        val sectorName = result?.data?.predict?.sector?.get(0)?.name
        when (sectorName) {
            "IT Sector" -> binding.imageView10.setImageResource(R.drawable.img_18)
            "Government Sector" -> binding.imageView10.setImageResource(R.drawable.govermentsector)
            "Health Sector" -> binding.imageView10.setImageResource(R.drawable.healthsector)
            "Education Sector" -> binding.imageView10.setImageResource(R.drawable.education)
            "Sports Sector" -> binding.imageView10.setImageResource(R.drawable.sports)
            "Finance Sector" -> binding.imageView10.setImageResource(R.drawable.finance)
            "Entertainment Sector" -> binding.imageView10.setImageResource(R.drawable.entertaintmentsector)

        }
        binding.button.text = sectorName
        binding.button4.setOnClickListener {
            val intent = Intent(this, MajorRecomendationActivity::class.java)
            intent.putExtra("PREDICTION_RESULT", result)
            startActivity(intent)
            finish()
        }
    }
}