package com.example.befitapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.befitapp.entity.Ingrediente

class ItemIngredienteAdapter(val lista: MutableList<Ingrediente>,
                             val context: Context)
    : RecyclerView.Adapter<ItemIngredienteAdapter.IngredienteViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemIngredienteAdapter.IngredienteViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_catalogo_ingredientes,
            parent, false
        )
        return IngredienteViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemIngredienteAdapter.IngredienteViewHolder, position: Int) {
        val ingrediente = lista.get(position)
        holder.nome.text = ingrediente.nome
        holder.porcao.text = ingrediente.porcao.toString()
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    class IngredienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome = itemView.findViewById<TextView>(R.id.tv_ingrediente_nome)
        val porcao = itemView.findViewById<TextView>(R.id.tv_ingrediente_porcao)
    }
}