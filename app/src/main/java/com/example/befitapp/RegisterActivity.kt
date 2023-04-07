package com.example.befitapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.example.befitapp.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var passwordTextInputLayout: TextInputLayout
    private lateinit var confirmPasswordTextInputLayout: TextInputLayout
    private lateinit var registerButton: Button
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginSubTitle.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        emailTextInputLayout = findViewById(R.id.ipt_email)
        passwordTextInputLayout = findViewById(R.id.ipt_password)
        confirmPasswordTextInputLayout = findViewById(R.id.ipt_confirm_password)
        registerButton = findViewById(R.id.btn_confirm)

        registerButton.setOnClickListener {
            val email = emailTextInputLayout.editText?.text.toString()
            val password = passwordTextInputLayout.editText?.text.toString()
            val confirmPassword = confirmPasswordTextInputLayout.editText?.text.toString()

            if (validatePassword(password, confirmPassword) && isValidEmail(email)) {
                passwordTextInputLayout.error = "teste erro"
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else if (!validatePassword(password, confirmPassword)) {

                Toast.makeText(this, "As senhas não correspondem", Toast.LENGTH_SHORT).show()
            } else if (!isValidEmail(email)) {
                Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun validatePassword(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+[a-z]+\\.com"
        return email.matches(emailPattern.toRegex())
    }

}