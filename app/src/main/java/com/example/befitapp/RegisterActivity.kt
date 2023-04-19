package com.example.befitapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.PopupWindow
import android.widget.Toast

import com.example.befitapp.databinding.ActivityRegisterBinding
import com.example.befitapp.entity.Usuario
import com.example.befitapp.service.BeFitApiService
import com.google.android.material.textfield.TextInputLayout

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var userTextInputLayout : TextInputLayout
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var passwordTextInputLayout: TextInputLayout
    private lateinit var confirmPasswordTextInputLayout: TextInputLayout
    private lateinit var registerButton: Button
    private lateinit var binding: ActivityRegisterBinding
    private val api = BeFitApiService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginSubTitle.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        registerButton = findViewById(R.id.btn_confirm)


        fun registerUser() {
            userTextInputLayout = findViewById(R.id.ipt_user)
            emailTextInputLayout = findViewById(R.id.ipt_email)
            passwordTextInputLayout = findViewById(R.id.ipt_password)
            confirmPasswordTextInputLayout = findViewById(R.id.ipt_confirm_password)
            registerButton = findViewById(R.id.btn_confirm)

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
                val call = api.adicionarUsuario(Usuario(user, email, password))
                call.enqueue(object: Callback<Usuario> {
                    override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                        if (response.isSuccessful) {
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(applicationContext, "Erro ao criar usuário", Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onFailure(call: Call<Usuario>, t: Throwable) {
                        Toast.makeText(applicationContext, "Erro ao conectar com a API", Toast.LENGTH_SHORT).show()
                    }
                })
            }

        }
        registerButton.setOnClickListener {
            val inflater = layoutInflater
            val popupView = inflater.inflate(R.layout.activity_lgpd, null)
            val termsCheckBox = popupView.findViewById<CheckBox>(R.id.chk_aceitar_termos)
            val confirmButton = popupView.findViewById<Button>(R.id.btn_confirm_popup)
            val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)

            popupWindow.showAtLocation(binding.root, Gravity.CENTER, 0, 0)
            confirmButton.setOnClickListener {
                if (termsCheckBox.isChecked) {
                    popupWindow.dismiss()
                    registerUser()
                } else {
                    Toast.makeText(this, "Você deve aceitar os termos e condições para continuar.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}




