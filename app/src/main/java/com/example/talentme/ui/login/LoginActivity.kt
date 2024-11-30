package com.example.talentme.ui.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.talentme.MainActivity

import com.example.talentme.ViewModelFactory
import com.example.talentme.data.pref.UserModel
import com.example.talentme.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private val viewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupAction()
        observeViewModel()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.signinBtn.setOnClickListener {
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            if (email.isBlank() || password.isBlank()) {
                showAlertDialog("Error", "Email and Password cannot be null.")
                return@setOnClickListener
            }
            viewModel.login(email, password)
            viewModel.loginResult.value?.loginResult?.token?.let { it1 ->
                UserModel(email,
                    it1
                )
            }?.let { it2 -> viewModel.saveSession(it2) }
        }
    }
    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.loginResult.observe(this) { response ->
            if (response.error == false) {
                val loginResult = response.loginResult
                if (loginResult?.name != null && loginResult.token != null) {
                    val userModel = UserModel(loginResult.name, loginResult.token, true)
                    viewModel.saveSession(userModel)
                    AlertDialog.Builder(this).apply {
                        setTitle("Yeah!")
                        setMessage("login Succeed. Can't wait to know your major?")
                        setPositiveButton("Next") { _, _ ->
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                } else {
                    showAlertDialog("Error", "Login Data is not filled.")
                }
            } else {
                showAlertDialog("Error", response.message ?: "Login Failed.")
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