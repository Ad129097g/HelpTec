package com.helptec.Fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.helptec.Adaptadores.AdaptadorMisCursos
import com.helptec.Curso
import com.helptec.PublicarCursoActivity
import com.helptec.databinding.FragmentMisCursosBinding

class FragmentMisCursos : Fragment() {

    private var _binding: FragmentMisCursosBinding? = null
    private val binding get() = _binding!!

    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var adaptador: AdaptadorMisCursos

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMisCursosBinding.inflate(inflater, container, false)
        val view = binding.root

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("Cursos")

        binding.coursesRecyclerView.layoutManager = LinearLayoutManager(activity)
        adaptador = AdaptadorMisCursos(requireContext())
        binding.coursesRecyclerView.adapter = adaptador

        // Verificar el rol del usuario después de obtener el usuario actual
        checkUserRole()

        return view
    }

    private fun checkUserRole() {
        val currentUser = firebaseAuth.currentUser
        currentUser?.let { user ->
            // Lógica para verificar si el usuario es profesor
            val uid = user.uid
            val usersRef = FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid)

            usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val tipo = snapshot.child("tipo").getValue(String::class.java)

                    if (tipo == "Profesor") {
                        // Habilitar el botón de publicar curso para el profesor
                        binding.btnPublicarCurso.visibility = View.VISIBLE
                        binding.btnPublicarCurso.isEnabled = true
                        binding.btnPublicarCurso.setOnClickListener {
                            startActivity(Intent(requireContext(), PublicarCursoActivity::class.java))
                        }
                    } else {
                        // Mostrar un mensaje para el estudiante que intenta publicar un curso
                        binding.btnPublicarCurso.visibility = View.VISIBLE
                        binding.btnPublicarCurso.isEnabled = true
                        binding.btnPublicarCurso.setOnClickListener {
                            Toast.makeText(requireContext(), "No puedes añadir un curso porque no eres profesor", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Manejar error de lectura de datos, si es necesario
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
