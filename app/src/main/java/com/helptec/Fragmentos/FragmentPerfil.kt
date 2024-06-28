package com.helptec.Fragmentos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.helptec.databinding.FragmentPerfilBinding

class FragmentPerfil : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private var currentUser: FirebaseUser? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        val view = binding.root

        firebaseAuth = FirebaseAuth.getInstance()
        currentUser = firebaseAuth.currentUser
        databaseReference = FirebaseDatabase.getInstance().reference.child("Usuarios").child(currentUser!!.uid)

        // Listener para cargar los datos del usuario
        loadUserProfile()

        // Botón para editar perfil
        binding.btnEditarPerfil.setOnClickListener {
            // Reemplazar el fragmento actual por el FragmentEditarPerfil
            parentFragmentManager.beginTransaction()
                .replace(android.R.id.content, FragmentEditalPerfil())
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    private fun loadUserProfile() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val nombres = snapshot.child("nombres").getValue(String::class.java)
                val email = snapshot.child("email").getValue(String::class.java)
                val telefono = snapshot.child("telefono").getValue(String::class.java)
                val tipo = snapshot.child("tipo").getValue(String::class.java)
                val fechaNac = snapshot.child("fecha_nac").getValue(String::class.java)

                binding.tvNombre.text = nombres
                binding.tvEmail.text = email
                binding.tvTelefono.text = telefono
                binding.tvTipo.text = tipo
                binding.tvFechaNac.text = fechaNac
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar error de lectura de datos, si es necesario
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
