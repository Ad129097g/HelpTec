package com.helptec
import com.helptec.Curso
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.helptec.databinding.ActivityPublicarCursoBinding

class PublicarCursoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPublicarCursoBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPublicarCursoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().reference.child("Cursos")

        binding.btnPublicar.setOnClickListener {
            val titulo = binding.editTitulo.text.toString().trim()
            val descripcion = binding.editDescripcion.text.toString().trim()
            val fechaInicio = binding.editFechaInicio.text.toString().trim()
            val fechaFin = binding.editFechaFin.text.toString().trim()

            if (titulo.isNotEmpty() && descripcion.isNotEmpty() && fechaInicio.isNotEmpty() && fechaFin.isNotEmpty()) {
                // Generar un ID único para el curso
                val cursoId = databaseReference.push().key ?: ""

                // Crear objeto curso
                val Curso = Curso(
                    cursoId,
                    titulo,
                    descripcion,
                    fechaInicio,
                    fechaFin
                )

                // Guardar curso en Firebase
                databaseReference.child(cursoId).setValue(Curso)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Curso publicado correctamente", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error al publicar el curso: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCurrentUserId(): String {
        return FirebaseAuth.getInstance().currentUser?.uid ?: ""
    }
}
