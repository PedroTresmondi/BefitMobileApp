package com.example.befitapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.befitapp.databinding.ActivityLoginBinding
import com.example.befitapp.entity.Login
import com.example.befitapp.service.BeFitApiService
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var passwordTextInputLayout: TextInputLayout
    private lateinit var loginButton: Button
    private lateinit var binding: ActivityLoginBinding

    private val api = BeFitApiService.getInstance()


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
            val emailUser = emailTextInputLayout.editText?.text.toString()
            val senhaUser = passwordTextInputLayout.editText?.text.toString()
            fun isValidEmail(email: String): Boolean {
                val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+[a-z]+\\.com"
                return email.matches(emailPattern.toRegex())
            }
            if (emailUser.isEmpty() || senhaUser.isEmpty()) {
                if (emailUser.isEmpty()) {
                    emailTextInputLayout.error = "Por favor, insira um e-mail."
                }
                if (senhaUser.isEmpty()) {
                    passwordTextInputLayout.error = "Por favor, insira uma senha."
                }

            } else if (!isValidEmail(emailUser)) {
                emailTextInputLayout.error = "Por favor, insira um e-mail válido."
                Toast.makeText(this, "Email inválido.", Toast.LENGTH_SHORT).show()
            } else {
                val call = api.loginUsuario(emailUser, senhaUser)
                call.enqueue(object: Callback<Login> {
                    override fun onResponse(call: Call<Login>, response: Response<Login>) {
                        if (response.isSuccessful) {
                            val login = response.body()
                            val emailApi = login?.email ?: ""
                            val senhaApi = login?.senha ?: ""
                            if (emailUser == emailApi && senhaUser == senhaApi) {
                                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(applicationContext, "Usuário autenticado com sucesso", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(applicationContext, "Email ou senha incorretos", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    override fun onFailure(call: Call<Login>, t: Throwable) {
                        Toast.makeText(applicationContext, "Erro ao conectar com a API", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }


    }







}