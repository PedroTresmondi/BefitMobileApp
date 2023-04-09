package com.example.befitapp

import androidx.appcompat.app.AppCompatActivity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Switch

import androidx.appcompat.app.AppCompatDelegate

class MenuActivity : AppCompatActivity() {

    private lateinit var switchModoNoturno: Switch
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            val prefs = PreferenceManager.getDefaultSharedPreferences(this)
            val modoNoturno = if (prefs.getBoolean("modo_noturno", false)) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(modoNoturno)

            setContentView(R.layout.activity_menu)

            val switchModoNoturno = findViewById<Switch>(R.id.switch_modo_noturno)
            switchModoNoturno.isChecked = modoNoturno == AppCompatDelegate.MODE_NIGHT_YES
            switchModoNoturno.setOnCheckedChangeListener { _, isChecked ->
                prefs.edit().putBoolean("modo_noturno", isChecked).apply()
                AppCompatDelegate.setDefaultNightMode(if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
                recreate()
            }
        }
}


