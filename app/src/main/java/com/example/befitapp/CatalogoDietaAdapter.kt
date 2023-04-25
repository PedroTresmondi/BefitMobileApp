package com.example.befitapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.befitapp.entity.BefitApi
import com.example.befitapp.entity.Dieta
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatalogoDietaAdapter(private val listaDietas: List<Dieta>, private val personId: String) :
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
        private val itemDieta: LinearLayout = itemView.findViewById(R.id.item)

        fun bindView(dieta: Dieta) {
            itemDieta.setOnClickListener {
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
                bundle.putInt("dieta_id", dieta.id)
                fragment.arguments = bundle
                val transaction = (itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

            Picasso.get().load(dieta.imagem).into(imagemDieta)
            nomeDieta.text = dieta.nome
            descricaoDieta.text = dieta.descricao
            likeButton.setImageResource(if (dieta.favoritado) R.drawable.ic_like_vermelho else R.drawable.ic_like)
            likeButton.setOnClickListener {
                dieta.favoritado = !dieta.favoritado

                val apiService = BefitApi.http()

                val call = if (dieta.favoritado) {
                    likeButton.setImageResource(R.drawable.ic_like_vermelho)
                    apiService.favoritarDieta(personId, dieta.id)
                } else {
                    likeButton.setImageResource(R.drawable.ic_like)
                    apiService.desfavoritarDieta(personId, dieta.id)
                }

                call.enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                    }
                })


            }
        }
    }
}

