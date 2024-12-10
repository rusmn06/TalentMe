package com.example.talentme.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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
import com.example.talentme.ui.register.RegisterActivity


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
        playAnimation()
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
            /*viewModel.loginResult.value?.message?.token?.let { it1 ->
                UserModel(email,
                    it1
                )
            }?.let { it2 -> viewModel.saveSession(it2) }*/

        }
        binding.signupBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.loginResult.observe(this) { response ->
            if (response.message == "Berhasil Login") {
                val loginResult = response.user
                val token = "token"
                if (loginResult?.idUser != null && loginResult.email != null) {
                    val userModel = response.user.idUser?.let { id ->
                        response.user.email?.let { email ->
                            UserModel(id, email, token, true)
                        }
                    }
                    if (userModel != null) {
                        viewModel.saveSession(userModel)
                    }
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
    private fun playAnimation(){
        ObjectAnimator.ofFloat(binding.imageView2, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val title = ObjectAnimator.ofFloat(binding.textView5, View.ALPHA, 1f).setDuration(420)
        val tvEmail = ObjectAnimator.ofFloat(binding.textView9, View.ALPHA, 1f).setDuration(420)
        val emailEditText = ObjectAnimator.ofFloat(binding.edRegisterEmail, View.ALPHA, 1f).setDuration(420)
        val tvPassword = ObjectAnimator.ofFloat(binding.textView10, View.ALPHA, 1f).setDuration(420)
        val passwordEditText = ObjectAnimator.ofFloat(binding.edRegisterPassword, View.ALPHA, 1f).setDuration(420)
        val signInBtn = ObjectAnimator.ofFloat(binding.signinBtn, View.ALPHA, 1f).setDuration(420)
        val tvMessage = ObjectAnimator.ofFloat(binding.textView11, View.ALPHA, 1f).setDuration(420)
        val signup = ObjectAnimator.ofFloat(binding.signupBtn, View.ALPHA, 1f).setDuration(420)

        AnimatorSet().apply {
            playSequentially(title,tvEmail,emailEditText,tvPassword,passwordEditText,signInBtn, tvMessage, signup)
            start()
        }
    }
}