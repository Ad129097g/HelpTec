package com.helptec

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.helptec.databinding.ActivitySignInBinding

class SignIn : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnIngresar.setOnClickListener {
            validarInfo()
        }

    }

    private var email = ""
    private var password = ""
    private fun validarInfo(){

        email = binding.editGmail.text.toString().trim()
        password = binding.editPassword.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.editGmail.error = "Email invalido"
            binding.editGmail.requestFocus()
        }
        else if (email.isEmpty()){
            binding.editGmail.error = "Ingrese email"
            binding.editGmail.requestFocus()
        }
        else if (password.isEmpty()){
            binding.editPassword.error = "Ingrese password"
            binding.editPassword.requestFocus()
        }else{
            loginUsuario()
        }

    }

    private fun loginUsuario(){
        progressDialog.setMessage("Ingresando")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity()
                Toast.makeText(
                    this,
                    "Bienvenido",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se pudo iniciar sesion debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
