package com.example.befitapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.befitapp.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var passwordTextInputLayout: TextInputLayout
    private lateinit var loginButton: Button
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSubTitle.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        emailTextInputLayout = findViewById(R.id.ipt_email)
        passwordTextInputLayout = findViewById(R.id.ipt_password)

        loginButton = findViewById(R.id.btn_login)


        loginButton.setOnClickListener {
            val email = emailTextInputLayout.editText?.text.toString()
            val password = passwordTextInputLayout.editText?.text.toString()
            authenticateUser(email, password)

            fun isValidEmail(email: String): Boolean {
                val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+[a-z]+\\.com"
                return email.matches(emailPattern.toRegex())
            }

            if (email.isEmpty() || password.isEmpty()) {
                if (email.isEmpty()) {
                    emailTextInputLayout.error = "Por favor, insira um e-mail."
                }
                if (password.isEmpty()) {
                    passwordTextInputLayout.error = "Por favor, insira uma senha."
                }

            } else if (!isValidEmail(email)) {
                emailTextInputLayout.error = "Por favor, insira um e-mail válido."
                Toast.makeText(this, "Email inválido.", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }


    }

    private fun authenticateUser(email: String, password: String) {
        if (isValidUser(email, password)) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        } else {

            Toast.makeText(this, "Credenciais incorretas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidUser(username: String, password: String): Boolean {
        return (username == "123" && password == "123")
    }




}