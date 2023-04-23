package com.example.befitapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.befitapp.entity.Exercicio
import com.squareup.picasso.Picasso

class ExercicioItemAdapter(private val listaExercicio: List<Exercicio>, private val exercicioView: View) :
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
        private val exercicioSeries: TextView = itemView.findViewById(R.id.tv_exercicio_series)
        private val teste: LinearLayout = itemView.findViewById(R.id.item_exercicio)


        fun bindView(exercicio: Exercicio) {
            exercicioNome.text = exercicio.nome
            exercicioRepeticoes.text = "repetições ${exercicio.quantidade}"
            exercicioSeries.text = "series ${exercicio.repeticao}"
            teste.setOnClickListener {
                it.animate()
                    .scaleX(0.9f)
                    .scaleY(0.9f)
                    .setDuration(100)
                    .withEndAction {
                        it.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100)
                            .start()
                    }
                    .start()

                exercicioView.findViewById<TextView>(R.id.tv_exercicio_nome_titulo).text =
                    exercicio.nome.uppercase()

                println(exercicio.imagem)
                Picasso.get().load(exercicio.imagem)
                    .into(exercicioView.findViewById<ImageView>(R.id.iv_exercicio))

                exercicioView.findViewById<TextView>(R.id.numero_serie).text =
                    exercicio.repeticao.toString()

                exercicioView.findViewById<TextView>(R.id.numero_repeticao).text =
                    exercicio.quantidade.toString()
            }
        }
    }
}