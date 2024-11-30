package com.example.talentme.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.talentme.R
import com.example.talentme.ViewModelFactory
import com.example.talentme.databinding.ActivityRegisterBinding
import com.example.talentme.ui.login.LoginActivity
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(this))[RegisterViewModel::class.java]
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupAction()
        observeViewModel()
    }
    private fun setupAction() {
        binding.registerButton.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            if (name.isBlank() || email.isBlank() || password.isBlank()) {
                AlertDialog.Builder(this).apply {
                    setTitle("Error")
                    setMessage("Please fill all fields")
                    setPositiveButton("OK", null)
                    create()
                    show()
                }
                return@setOnClickListener
            }
            viewModel.viewModelScope.launch {
                viewModel.register(name, email, password)
            }
        }
        binding.signupBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.signUpResult.observe(this) { response ->
            if (response.error == false) {
                AlertDialog.Builder(this).apply {
                    setTitle("Success")
                    setMessage(response.message ?: "Registration successful")
                    setPositiveButton("OK") { _, _ ->
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    create()
                    show()
                }
            }
        }

        viewModel.errorMessage.observe(this) { error ->
            error?.let {
                showAlertDialog("Error", it)
            }
        }
    }
    private fun showAlertDialog(title: String, message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK", null)
            create()
            show()
        }
    }

}