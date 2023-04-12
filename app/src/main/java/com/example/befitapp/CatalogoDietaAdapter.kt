package com.example.befitapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.befitapp.entity.Catalogo
import com.squareup.picasso.Picasso

class CatalogoDietaAdapter(private val listaDietas: List<Catalogo>) :
    RecyclerView.Adapter<CatalogoDietaAdapter.CatalogoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_catalogo, parent, false)
        return CatalogoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatalogoViewHolder, position: Int) {
        val dieta = listaDietas[position]
        holder.bindView(dieta)
    }

    override fun getItemCount(): Int {
        return listaDietas.size
    }

    inner class CatalogoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imagemDieta: ImageView = itemView.findViewById(R.id.imagem_catalogo)
        private val nomeDieta: TextView = itemView.findViewById(R.id.nome_catalogo)
        private val descricaoDieta: TextView = itemView.findViewById(R.id.descricao_catalogo)
        private val likeButton: ImageButton = itemView.findViewById(R.id.like_catalogo)

        fun bindView(dieta: Catalogo) {
            Picasso.get().load(dieta.imagem).into(imagemDieta)
            nomeDieta.text = dieta.nome
            descricaoDieta.text = dieta.descricao
            likeButton.setImageResource(if (dieta.favoritado) R.drawable.ic_like_vermelho else R.drawable.ic_like)
            likeButton.setOnClickListener {
                dieta.favoritado = !dieta.favoritado
                likeButton.setImageResource(if (dieta.favoritado) R.drawable.ic_like_vermelho else R.drawable.ic_like)
            }
        }
    }
}
