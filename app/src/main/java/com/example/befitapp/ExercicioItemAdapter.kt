package com.example.befitapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.befitapp.entity.Exercicio
import com.squareup.picasso.Picasso

class ExercicioItemAdapter(
    private val listaExercicio: List<Exercicio>,
    private val exercicioView: View,
    private val context: Context?
) :
    RecyclerView.Adapter<ExercicioItemAdapter.ExercicioViewHolder>() {

    private var currentExercicioIndex = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExercicioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercicio, parent, false)
        return ExercicioViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExercicioViewHolder, position: Int) {
        val exercicio = listaExercicio[position]
        holder.bindView(exercicio, listaExercicio)
    }

    override fun getItemCount(): Int {
        return listaExercicio.size
    }

    inner class ExercicioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val exercicioNome: TextView = itemView.findViewById(R.id.tv_exercicio_nome)
        private val exercicioRepeticoes: TextView =
            itemView.findViewById(R.id.tv_exercicio_repeticoes)
        private val exercicioSeries: TextView = itemView.findViewById(R.id.tv_exercicio_series)
        private val itemExercicio: LinearLayout = itemView.findViewById(R.id.item_exercicio)
        private val infoExercicio: ImageView =
            exercicioView.findViewById(R.id.iv_informacao_exercicio)

        fun bindView(exercicio: Exercicio, listaExercicio: List<Exercicio>) {
            exercicioNome.text = exercicio.nome
            exercicioRepeticoes.text = "Repetições: ${exercicio.quantidade}"
            exercicioSeries.text = "Series: ${exercicio.repeticao}"
            itemExercicio.setOnClickListener {
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

                currentExercicioIndex = listaExercicio.indexOf(exercicio)

                exercicioView.findViewById<TextView>(R.id.tv_exercicio_nome_titulo).text =
                    exercicio.nome.uppercase()

                Picasso.get().load(exercicio.imagem)
                    .into(exercicioView.findViewById<ImageView>(R.id.iv_exercicio))

                exercicioView.findViewById<TextView>(R.id.numero_serie).text =
                    exercicio.repeticao.toString()

                exercicioView.findViewById<TextView>(R.id.numero_repeticao).text =
                    exercicio.quantidade.toString()
            }

            infoExercicio.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setMessage(listaExercicio[currentExercicioIndex].descricao)
                    .setCancelable(false)
                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                        dialog.dismiss()
                    })
                val alert = builder.create()
                alert.show()
            }
        }

    }
}