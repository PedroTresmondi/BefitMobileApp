package com.example.befitapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.befitapp.databinding.ActivityLoginBinding
import com.example.befitapp.entity.BefitApi
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
    private var emailApi: String = ""

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
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                val call = BefitApi.http().loginUsuario(emailUser, senhaUser)
                call.enqueue(object: Callback<Login> {
                    override fun onResponse(call: Call<Login>, response: Response<Login>) {
                        try {
                            if (response.isSuccessful) {
                                println("chegou resposta")
                                println(response)
                                val login = response.body()
                                emailApi = login?.email ?: ""
                                println("email" + emailApi)
                                if (response.code() == 200 && emailUser == emailApi) {
                                    println("autenticou")
                                    startActivity(intent)
                                    Toast.makeText(applicationContext, "Usuário autenticado com sucesso", Toast.LENGTH_SHORT).show()
                                } else {
                                    println("Email ou senha incorretos")
                                    Toast.makeText(applicationContext, "Email ou senha incorretos", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                println("Erro na resposta da API")

                                throw Exception("Erro na resposta da API")
                            }
                        } catch (e: Exception) {
                            println("caiu na exception")
                            Toast.makeText(applicationContext, "Erro ao processar a resposta da API", Toast.LENGTH_SHORT).show()
                            Log.e("LoginActivity", "Erro ao processar a resposta da API", e)
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