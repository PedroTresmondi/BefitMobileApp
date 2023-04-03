package com.example.befitapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavoritoAdapter(val listaFavoritos: List<String>, val type: String) :
    RecyclerView.Adapter<FavoritoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorito, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listaFavoritos[position], type)
    }

    override fun getItemCount() = listaFavoritos.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nomeTextView: TextView = itemView.findViewById(R.id.nome_item)
        private val iconeTmageView: ImageView = itemView.findViewById(R.id.item_imagem)

        fun bind(item: String, type: String) {
            when (type) {
                "treino" -> iconeTmageView.setImageResource(R.drawable.ic_treino)
                "dieta" -> iconeTmageView.setImageResource(R.drawable.ic_dieta)
            }
            iconeTmageView.scaleY = 0.7F
            iconeTmageView.scaleX = 0.7F
            nomeTextView.text = item
        }
    }
}
