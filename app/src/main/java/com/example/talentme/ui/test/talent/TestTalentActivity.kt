package com.example.talentme.ui.test.talent

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
import com.example.talentme.databinding.ActivityTestTalentBinding
import com.example.talentme.form.Question
import com.example.talentme.form.QuestionAdapter
import com.example.talentme.ui.result.talent.ResultTalentActivity

class TestTalentActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var questionAdapter: QuestionAdapter
    private lateinit var questionList: List<Question>
    private lateinit var binding: ActivityTestTalentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTestTalentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)


        questionList = listOf(
            Question("If you had access to a time machine,would you rather travel to:", "Past", "Future", "I would sell it"),
            Question("What type of person are you?", "early bird", "afternoon person", "night owl"),
            Question("what is the nature of your job?", "work from home", "work by travel", "work with peers"),
            Question("What type of person are you?", "extrovert", "ambivert", "introvert"),
            Question("how good are you at studies(%)?", "80-100", "50-80", "below 50"),
            Question("Which age group do you belong?", "8-16", "16-24", "above 24"),
            Question("Talk about yourself(Text):", "confident", "not confident", "kinda confident"),
            Question("What insipires you?", "seeing the world", "improving the world", "making others happy"),
            Question("what type of person are you?", "Caring", "Intelligent", "adventurous"),
            Question("You feel the happiest when you?", "solve a technical issue", "perform on stage", "create something"),
            Question("Describe your ideal workout:", "Gym", "Outdoor exercise", "no workout"),
            Question("How do you spend your money?", "shop yourself", "throw a party", "invest it"),
            Question("describe your workspace:", "silent room", "informal lounge", "my favorite coffee shop"),
            Question("Would you rather be:", "A world class villain", "unrecognised hero", "richest person but alone")
            )

        questionAdapter = QuestionAdapter(questionList)
        recyclerView.adapter = questionAdapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.continueButton.setOnClickListener {
            val intent = Intent(this, ResultTalentActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
