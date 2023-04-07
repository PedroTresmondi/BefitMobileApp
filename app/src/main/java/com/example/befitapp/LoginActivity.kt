package com.example.befitapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.befitapp.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSubTitle.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        val emailInputLayout = findViewById<TextInputLayout>(R.id.ipt_email)
        emailEditText = emailInputLayout.editText!!

        val passwordInputLayout = findViewById<TextInputLayout>(R.id.ipt_password)
        passwordEditText = passwordInputLayout.editText!!

        loginButton = findViewById(R.id.btn_login)

        loginButton.setOnClickListener {
            val username = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            authenticateUser(username, password)
        }
    }

    private fun authenticateUser(username: String, password: String) {
        if (isValidUser(username, password)) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        } else {

            Toast.makeText(this, "Credenciais incorretas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isValidUser(username: String, password: String): Boolean {
        // Aqui você pode adicionar a lógica para verificar se as credenciais do usuário estão corretas
        // Por exemplo, você pode fazer uma verificação de usuário e senha na API do seu backend
        return (username == "123" && password == "123")
    }
}