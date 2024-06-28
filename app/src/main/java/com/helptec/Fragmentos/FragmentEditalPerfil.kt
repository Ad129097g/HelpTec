package com.helptec.Fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.helptec.databinding.FragmentEditalPerfilBinding

class FragmentEditalPerfil : Fragment() {

    private var _binding: FragmentEditalPerfilBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditalPerfilBinding.inflate(inflater, container, false)
        val view = binding.root

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference.child("Usuarios").child(firebaseAuth.currentUser!!.uid)

        // Cargar datos actuales del usuario para editar
        loadCurrentUserData()

        // Guardar cambios al hacer clic en el botón Guardar
        binding.btnGuardarCambios.setOnClickListener {
            saveUserChanges()
        }

        return view
    }

    private fun loadCurrentUserData() {
        // Implementar carga de datos actuales si es necesario
    }

    private fun saveUserChanges() {
        val nuevoNombre = binding.etNombre.text.toString()
        val nuevoEmail = binding.etEmail.text.toString()
        val nuevoTelefono = binding.etTelefono.text.toString()
        val nuevoTipo = binding.etTipo.text.toString()
        val nuevaFechaNac = binding.etFechaNac.text.toString()

        // Actualizar datos en Firebase
        databaseReference.child("nombres").setValue(nuevoNombre)
        databaseReference.child("telefono").setValue(nuevoTelefono)
        databaseReference.child("tipo").setValue(nuevoTipo)
        databaseReference.child("fecha_nac").setValue(nuevaFechaNac)
            .addOnSuccessListener {
                // Mostrar mensaje o realizar acción adicional si es necesario
            }
            .addOnFailureListener {
                // Manejar errores al guardar cambios
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
