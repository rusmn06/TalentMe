package com.example.talentme.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.talentme.R
import com.example.talentme.ViewModelFactory
import com.example.talentme.data.room.User
import com.example.talentme.databinding.ActivityRegisterBinding
import com.example.talentme.ui.login.LoginActivity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private  var name : String = ""
    private  var email : String = ""
    private  var password : String = ""
    private  var birthDate : String = ""
    private  var gender : String = ""

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
        playAnimation()
    }
    private fun setupAction() {
        binding.registerButton.setOnClickListener {
            name = binding.edRegisterName.text?.toString() ?: ""
            email = binding.edRegisterEmail.text?.toString() ?: ""
            password = binding.edRegisterPassword.text?.toString() ?: ""
            birthDate = binding.edRegisterBirthDate.text?.toString() ?: ""
            gender = binding.edRegisterGender.selectedItem?.toString() ?: ""
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
            val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = inputFormat.parse(birthDate)
            val formattedDate = outputFormat.format(date)
            viewModel.viewModelScope.launch {
                viewModel.register(name, gender, formattedDate, email, password)
            }
        }
        binding.signupBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        val spinnerGender: Spinner = findViewById(R.id.edRegisterGender)
        val genderOptions = arrayOf("Choose your Gender","female", "male")

        val adapter = ArrayAdapter(this,R.layout.selected_item, genderOptions)
        adapter.setDropDownViewResource(R.layout.spinner_item)
        spinnerGender.adapter = adapter

        binding.edRegisterBirthDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.edRegisterBirthDate.setText(selectedDate)
            }, year, month, day)

            datePickerDialog.show()
        }

    }
    private fun observeViewModel() {
        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.signUpResult.observe(this) { response ->
            val user = response.userId?.let { User(it, name, gender, birthDate, email, password) }
            if (user != null) {
                viewModel.insertUser(user)
            }
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
        val title = ObjectAnimator.ofFloat(binding.textView5, View.ALPHA, 1f).setDuration(420)
        val tvName = ObjectAnimator.ofFloat(binding.textView6, View.ALPHA, 1f).setDuration(420)
        val tvBirthdate = ObjectAnimator.ofFloat(binding.textView7, View.ALPHA, 1f).setDuration(420)
        val tvGender = ObjectAnimator.ofFloat(binding.textView8, View.ALPHA, 1f).setDuration(420)
        val tvEmail = ObjectAnimator.ofFloat(binding.textView9, View.ALPHA, 1f).setDuration(420)
        val tvPassword = ObjectAnimator.ofFloat(binding.textView10, View.ALPHA, 1f).setDuration(420)
        val tvMessage = ObjectAnimator.ofFloat(binding.textView11, View.ALPHA, 1f).setDuration(420)

        val nameEditText = ObjectAnimator.ofFloat(binding.edRegisterName, View.ALPHA, 1f).setDuration(420)
        val birthdateEditText = ObjectAnimator.ofFloat(binding.edRegisterBirthDate, View.ALPHA, 1f).setDuration(420)
        val genderEditText = ObjectAnimator.ofFloat(binding.edRegisterGender, View.ALPHA, 1f).setDuration(420)
        val emailEditText = ObjectAnimator.ofFloat(binding.edRegisterEmail, View.ALPHA, 1f).setDuration(420)
        val passwordEditText = ObjectAnimator.ofFloat(binding.edRegisterPassword, View.ALPHA, 1f).setDuration(420)
        val signUpBtn = ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(420)
        val signInBtn = ObjectAnimator.ofFloat(binding.signupBtn, View.ALPHA, 1f).setDuration(420)

        AnimatorSet().apply {
            playSequentially(title,tvName,nameEditText,tvBirthdate,birthdateEditText,tvGender,genderEditText,tvEmail,emailEditText,tvPassword,passwordEditText,signUpBtn, tvMessage, signInBtn)
            start()
        }
    }

}