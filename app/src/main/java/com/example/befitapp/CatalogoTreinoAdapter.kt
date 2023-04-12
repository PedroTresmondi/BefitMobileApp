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

class CatalogoAdapter(private val listaTreinos: List<Catalogo>) :
    RecyclerView.Adapter<CatalogoAdapter.CatalogoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_catalogo, parent, false)
        return CatalogoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatalogoViewHolder, position: Int) {
        val treino = listaTreinos[position]
        holder.bindView(treino)
    }

    override fun getItemCount(): Int {
        return listaTreinos.size
    }

    inner class CatalogoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imagemTreino: ImageView = itemView.findViewById(R.id.imagem_catalogo)
        private val nomeTreino: TextView = itemView.findViewById(R.id.nome_treino_catalogo)
        private val descricaoTreino: TextView = itemView.findViewById(R.id.descricao_treino_catalogo)
        private val likeButton: ImageButton = itemView.findViewById(R.id.like_treino_catalogo)

        fun bindView(treino: Catalogo) {
            Picasso.get().load(treino.imagem).into(imagemTreino)
            nomeTreino.text = treino.nome
            descricaoTreino.text = treino.descricao
            likeButton.setImageResource(if (treino.favoritado) R.drawable.ic_like_vermelho else R.drawable.ic_like)
            likeButton.setOnClickListener {
                treino.favoritado = !treino.favoritado
                likeButton.setImageResource(if (treino.favoritado) R.drawable.ic_like_vermelho else R.drawable.ic_like)
            }
        }
    }
}
