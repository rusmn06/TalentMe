package com.example.talentme.ui.startpage

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.talentme.R
import com.example.talentme.databinding.ActivityRegisterBinding
import com.example.talentme.databinding.ActivityStartPageBinding
import com.example.talentme.ui.register.RegisterActivity

class StartPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityStartPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val getStartedButton = findViewById<Button>(R.id.get_started_button)
        getStartedButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        playAnimation()
    }
    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val title = ObjectAnimator.ofFloat(binding.textView, View.ALPHA, 1f).setDuration(520)
        val title2 = ObjectAnimator.ofFloat(binding.textView2, View.ALPHA, 1f).setDuration(520)
        val title3 = ObjectAnimator.ofFloat(binding.textView3, View.ALPHA, 1f).setDuration(520)
        val message = ObjectAnimator.ofFloat(binding.textView4, View.ALPHA, 1f).setDuration(520)
        val getStartedBtn = ObjectAnimator.ofFloat(binding.getStartedButton, View.ALPHA, 1f).setDuration(520)

        AnimatorSet().apply {
            playSequentially(title,title2,title3,message,getStartedBtn)
            start()
        }
    }
}