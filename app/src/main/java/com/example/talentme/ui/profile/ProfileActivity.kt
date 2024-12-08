package com.example.talentme.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.talentme.MainActivity
import com.example.talentme.R
import com.example.talentme.ViewModelFactory
import com.example.talentme.data.pref.UserModel
import com.example.talentme.databinding.ActivityProfileBinding
import com.example.talentme.ui.login.LoginViewModel

class ProfileActivity : AppCompatActivity() {
    private val viewModel by viewModels<ProfileActivityViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageButton9.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel.GetUserById(1)
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.getUserByIdResult.observe(this){
            binding.textView32.text = it.data?.nama
            binding.textView34.text = it.data?.email
            binding.textView36.text = it.data?.idUser.toString()
        }

    }
}