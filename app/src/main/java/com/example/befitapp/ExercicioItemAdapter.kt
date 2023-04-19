package com.example.befitapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.befitapp.entity.Catalogo
import com.example.befitapp.entity.Exercicio
import com.squareup.picasso.Picasso

class ExercicioItemAdapter(private val listaExercicio: List<Exercicio>) :
    RecyclerView.Adapter<ExercicioItemAdapter.ExercicioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercicioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercicio, parent, false)
        return ExercicioViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExercicioViewHolder, position: Int) {
        val exercicio = listaExercicio[position]
        holder.bindView(exercicio)
    }

    override fun getItemCount(): Int {
        return listaExercicio.size
    }

    inner class ExercicioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val exercicioNome: TextView = itemView.findViewById(R.id.tv_exercicio_nome)
        private val exercicioRepeticoes: TextView = itemView.findViewById(R.id.tv_exercicio_repeticoes)
        private val itemExercicio: LinearLayout = itemView.findViewById(R.id.item_exercicio)
        //private val imagemExercicio: ImageView = itemView.findViewById(R.id.iv_exercicio)

        fun bindView(exercicio: Exercicio) {
            exercicioNome.text = exercicio.nome
            exercicioRepeticoes.text = "repetições ${exercicio.repeticao}"

//            itemExercicio.setOnClickListener {
//                Picasso.get().load(exercicio.imagem).into(imagemExercicio)
//            }
        }
    }
}