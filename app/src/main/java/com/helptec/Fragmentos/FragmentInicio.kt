package com.helptec.Fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.helptec.R
import com.helptec.databinding.FragmentInicioBinding


class FragmentInicio : Fragment() {

    private var _binding: FragmentInicioBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.imageButton1.setOnClickListener {
            navigateToCourses("Gestion")
        }

        binding.imageButton2.setOnClickListener {
            navigateToCourses("Maviacion")
        }

        binding.imageButton3.setOnClickListener {
            navigateToCourses("Electricidad")
        }

        binding.imageButton4.setOnClickListener {
            navigateToCourses("Mineria")
        }

        binding.imageButton5.setOnClickListener {
            navigateToCourses("Agricola")
        }

        binding.imageButton6.setOnClickListener {
            navigateToCourses("Seguridad")
        }

        return view
    }

    private fun navigateToCourses(department: String) {
        val fragment = FragmentMostrarCursos.newInstance(department)
        val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
