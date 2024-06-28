package com.helptec

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.helptec.Inicio2
import com.helptec.R

class Inicio : AppCompatActivity() {

    // Nombre del archivo de preferencias compartidas
    private val PREFS_FILENAME = "com.helptec.prefs"
    private val USER_TYPE_KEY = "user_type"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        val studentButton: Button = findViewById(R.id.button)
        val professorButton: Button = findViewById(R.id.button2)

        // Listener para el botón de Estudiante
        studentButton.setOnClickListener {
            saveUserType("student")
            navigateToNextScreen()
        }

        // Listener para el botón de Profesor
        professorButton.setOnClickListener {
            saveUserType("professor")
            navigateToNextScreen()
        }
    }

    // Función para guardar el tipo de usuario en SharedPreferences
    private fun saveUserType(type: String) {
        val sharedPrefs = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString(USER_TYPE_KEY, type)
        editor.apply()
    }

    // Función para navegar a la siguiente pantalla
    private fun navigateToNextScreen() {
        val intent = Intent(this, Inicio2::class.java)
        startActivity(intent)
    }
}
