package com.example.befitapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        val menuConfig = findViewById<ImageView>(R.id.menu_config)
        menuConfig.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        var topTextView = findViewById<TextView>(R.id.top_navigation)
        topTextView.text = "Perfil"

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, PerfilFragment())
            .commit()

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_training -> {
                    val fragment = TreinoFragment()
                    supportFragmentManager.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit()
                    topTextView.text = "Treinos"
                    true
                }
                R.id.menu_profile -> {
                    val fragment = PerfilFragment()
                    supportFragmentManager.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit()
                    topTextView.text = "Perfil"
                    true
                }
                R.id.menu_diet -> {
                    // Ação a ser executada quando o item "dieta" for selecionado
                    topTextView.text = "Dietas"
                    true
                }
                else -> false
            }
        }

    }
}