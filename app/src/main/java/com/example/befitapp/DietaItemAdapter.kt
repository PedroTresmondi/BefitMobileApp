package com.example.befitapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.befitapp.entity.DietaCompleta
import com.example.befitapp.entity.Ingrediente

class DietaItemAdapter(
    private val listaIngrediente: List<DietaCompleta>,
    private val ingredientesView: View,
    private val context: Context?
) :
    RecyclerView.Adapter<DietaItemAdapter.DietaViewHolder>() {

    private var currentIngredienteIndex = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_exercicio, parent, false)
        return DietaViewHolder(view)
    }

    override fun onBindViewHolder(holder: DietaViewHolder, position: Int) {
        val dieta = listaIngrediente[position]
        //holder.bindView(dieta, listaIngrediente )
    }

    override fun getItemCount(): Int {
        return listaIngrediente.size
    }

    inner class DietaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ingredienteNome: TextView = itemView.findViewById(R.id.tv_ingrediente_nome)
        private val ingredientePorcao: TextView =
            itemView.findViewById(R.id.tv_ingrediente_porcao)


        fun bindView(ingrediente: Ingrediente, listaIngrediente: List<Ingrediente>) {
            ingredienteNome.text = ingrediente.nome
            ingredientePorcao.text = "Porções: ${ingrediente.porcao}"

            ingredientesView.findViewById<TextView>(R.id.tv_dieta_nome_titulo).text =
                ingrediente.nome.uppercase()
        }
    }

}
