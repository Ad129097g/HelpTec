package com.helptec

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.helptec.databinding.ActivityRegistrarseBinding

class Registrarse : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarseBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    // Nombre del archivo de preferencias compartidas
    private val PREFS_FILENAME = "com.helptec.prefs"
    private val USER_TYPE_KEY = "user_type"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnRegistrar.setOnClickListener {
            validarInfo()
        }
    }

    private var email = ""
    private var password = ""
    private var r_password = ""
    private var nombreUsuario = ""
    private var telefono = ""

    private fun validarInfo() {
        email = binding.editGmail.text.toString().trim()
        password = binding.editPassword.text.toString().trim()
        r_password = binding.editRPassword.text.toString().trim()
        nombreUsuario = binding.editUser.text.toString().trim()
        telefono = binding.editTelefono.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editGmail.error = "Email inválido"
            binding.editGmail.requestFocus()
        } else if (email.isEmpty()) {
            binding.editGmail.error = "Ingrese email"
            binding.editGmail.requestFocus()
        } else if (password.isEmpty()) {
            binding.editPassword.error = "Ingrese password"
            binding.editPassword.requestFocus()
        } else if (r_password.isEmpty() || r_password != password) {
            binding.editRPassword.error = "Las contraseñas no coinciden"
            binding.editRPassword.requestFocus()
        } else if (nombreUsuario.isEmpty()) {
            binding.editUser.error = "Ingrese nombre de usuario"
            binding.editUser.requestFocus()
        } else if (telefono.isEmpty()) {
            binding.editTelefono.error = "Ingrese teléfono"
            binding.editTelefono.requestFocus()
        } else {
            registrarUsuario()
        }
    }

    private fun registrarUsuario() {
        progressDialog.setMessage("Creando cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val uidUsuario = authResult.user?.uid
                guardarTipoUsuario(uidUsuario)
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se registró el usuario debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun guardarTipoUsuario(uidUsuario: String?) {
        val sharedPrefs = getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        val userType = if (binding.checkBoxEstudiante.isChecked) "Estudiante" else "Profesor"

        if (userType.isNotEmpty() && uidUsuario != null) {
            val database = FirebaseDatabase.getInstance()
            val refUsuarios = database.getReference("Usuarios")

            val usuarioData = hashMapOf(
                "nombres" to nombreUsuario,
                "codigoTelefono" to "",
                "telefono" to telefono,
                "urlImagenPerfil" to "",
                "proveedor" to "Email",
                "online" to true,
                "email" to email,
                "uid" to uidUsuario,
                "fecha_nac" to "",
                "tipo" to userType
            )

            refUsuarios.child(uidUsuario).setValue(usuarioData)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(
                        this,
                        "No se registró el usuario debido a ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}
