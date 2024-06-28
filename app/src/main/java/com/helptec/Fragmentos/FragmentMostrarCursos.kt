package com.helptec.Fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.helptec.databinding.FragmentMostrarCursosBinding

class FragmentMostrarCursos : Fragment() {

    private var _binding: FragmentMostrarCursosBinding? = null
    private val binding get() = _binding!!
    private var department: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            department = it.getString(ARG_DEPARTMENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMostrarCursosBinding.inflate(inflater, container, false)
        val view = binding.root
        // Aquí debes implementar el filtro y la lógica para mostrar los cursos correspondientes
        // según el departamento.
        return view
    }

    companion object {
        private const val ARG_DEPARTMENT = "department"

        @JvmStatic
        fun newInstance(department: String) =
            FragmentMostrarCursos().apply {
                arguments = Bundle().apply {
                    putString(ARG_DEPARTMENT, department)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
