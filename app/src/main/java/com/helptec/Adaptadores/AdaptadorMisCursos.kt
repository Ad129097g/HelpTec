package com.helptec.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.helptec.Curso
import com.helptec.R

class AdaptadorMisCursos(private val context: Context) : RecyclerView.Adapter<AdaptadorMisCursos.ViewHolder>() {

    private var cursosList: List<Curso> = emptyList()
    private lateinit var databaseReference: DatabaseReference

    init {
        databaseReference = FirebaseDatabase.getInstance().reference.child("Cursos")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.course_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val curso = cursosList[position]
        holder.bind(curso)
    }

    override fun getItemCount(): Int {
        return cursosList.size
    }

    fun setItems(cursos: List<Curso>) {
        cursosList = cursos
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tituloTextView: TextView = itemView.findViewById(R.id.tituloTextView)

        fun bind(curso: Curso) {
            tituloTextView.text = curso.titulo

            // Ejemplo de acción al hacer clic en un elemento del RecyclerView
            itemView.setOnClickListener {
                // Aquí podrías implementar la lógica para manejar el clic en un curso
                // Por ejemplo, abrir un detalle del curso o cualquier otra acción deseada
            }
        }
    }
}
