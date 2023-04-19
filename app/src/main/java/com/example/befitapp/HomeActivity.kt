package com.example.befitapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        var topTextView = findViewById<TextView>(R.id.top_navigation)
        topTextView.text = "Perfil"

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PerfilFragment())
            .commit()

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_training -> {
                    val fragment = TreinoFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                    topTextView.text = "Treinos"
                    true
                }
                R.id.menu_profile -> {
                    val fragment = PerfilFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                    topTextView.text = "Perfil"
                    true
                }
                R.id.menu_diet -> {
                    val fragment = DietaFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                    topTextView.text = "Dietas"
                    true
                }
                else -> false
            }
        }

    }
}