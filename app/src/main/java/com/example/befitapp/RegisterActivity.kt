package com.example.befitapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

import com.example.befitapp.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {
    private lateinit var userTextInputLayout : TextInputLayout
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

        userTextInputLayout = findViewById(R.id.ipt_user)
        emailTextInputLayout = findViewById(R.id.ipt_email)
        passwordTextInputLayout = findViewById(R.id.ipt_password)
        confirmPasswordTextInputLayout = findViewById(R.id.ipt_confirm_password)
        registerButton = findViewById(R.id.btn_confirm)

        registerButton.setOnClickListener {
            val user = userTextInputLayout.editText?.text.toString()
            val email = emailTextInputLayout.editText?.text.toString()
            val password = passwordTextInputLayout.editText?.text.toString()
            val confirmPassword = confirmPasswordTextInputLayout.editText?.text.toString()


            fun validatePassword(password: String, confirmPassword: String): Boolean {
                return password == confirmPassword
            }

            fun isValidEmail(email: String): Boolean {
                val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+[a-z]+\\.com"
                return email.matches(emailPattern.toRegex())
            }

            if (user.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                if (user.isEmpty()) {
                    userTextInputLayout.error = "Por favor, insira um nome de usuario."
                }
                if (email.isEmpty()) {
                    emailTextInputLayout.error = "Por favor, insira um e-mail."
                }
                if (password.isEmpty()) {
                    passwordTextInputLayout.error = "Por favor, insira uma senha."
                }
                if (confirmPassword.isEmpty()) {
                    confirmPasswordTextInputLayout.error = "Por favor, confirme sua senha."
                }
            } else if (!validatePassword(password, confirmPassword)) {
                passwordTextInputLayout.error = "As senhas não correspondem."
                Toast.makeText(this, "As senhas não correspondem.", Toast.LENGTH_SHORT).show()
            } else if (!isValidEmail(email)) {
                emailTextInputLayout.error = "Por favor, insira um e-mail válido."
                Toast.makeText(this, "Email inválido.", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
    }