package com.example.befitapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.befitapp.entity.Catalogo

class FavoritoAdapter(val listaFavoritos: List<Catalogo>, val type: String) :
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
        private val itemFavorito: LinearLayout = itemView.findViewById(R.id.item_favorito)

        fun bind(item: Catalogo, type: String) {
            when (type) {
                "treino" -> iconeTmageView.setImageResource(R.drawable.ic_treino)
                "dieta" -> iconeTmageView.setImageResource(R.drawable.ic_dieta)
            }
            iconeTmageView.scaleY = 0.7F
            iconeTmageView.scaleX = 0.7F
            nomeTextView.text = item.nome

            if (type == "treino") {
                itemFavorito.setOnClickListener {
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

                    val fragment = ExercicioFragment()
                    val bundle = Bundle()
                    bundle.putInt("treino_id", item.id)
                    fragment.arguments = bundle
                    val transaction =
                        (itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment_container, fragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }

                if (type == "dieta") {
                    itemFavorito.setOnClickListener {
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

                        val fragment = ExercicioFragment()
                        val bundle = Bundle()
                        bundle.putInt("dieta_id", item.id)
                        fragment.arguments = bundle
                        val transaction =
                            (itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.fragment_container, fragment)
                        transaction.addToBackStack(null)
                        transaction.commit()
                    }
            }
        }
    }
    }
    }
