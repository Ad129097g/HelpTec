package com.helptec

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class Inicio2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio2)

        val buttonIniciarSesion = findViewById<Button>(R.id.buttonIniciarSesion)
        val buttonRegistrarse = findViewById<Button>(R.id.buttonRegistrarse)

        buttonIniciarSesion.setOnClickListener {
            val intent = Intent(this, SignIn::class.java)
            startActivity(intent)
        }

        buttonRegistrarse.setOnClickListener {
            val intent = Intent(this, Registrarse::class.java)
            startActivity(intent)
        }
    }
}
