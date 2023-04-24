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
import com.example.befitapp.entity.Catalogo
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatalogoTreinoAdapter(private val listaTreinos: List<Catalogo>, private val personId: String) :
    RecyclerView.Adapter<CatalogoTreinoAdapter.CatalogoViewHolder>() {

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
        private val nomeTreino: TextView = itemView.findViewById(R.id.nome_catalogo)
        private val descricaoTreino: TextView = itemView.findViewById(R.id.descricao_catalogo)
        private val likeButton: ImageButton = itemView.findViewById(R.id.like_catalogo)
        private val itemTreino: LinearLayout = itemView.findViewById(R.id.item)

        fun bindView(treino: Catalogo) {
            itemTreino.setOnClickListener {
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
                bundle.putInt("treino_id", treino.id)
                fragment.arguments = bundle
                val transaction = (itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }

            Picasso.get().load(treino.imagem).into(imagemTreino)
            nomeTreino.text = treino.nome
            descricaoTreino.text = treino.descricao
            likeButton.setImageResource(if (treino.favoritado) R.drawable.ic_like_vermelho else R.drawable.ic_like)
            likeButton.setOnClickListener {
                treino.favoritado = !treino.favoritado

                val apiService = BefitApi.http()

                val call = if (treino.favoritado) {
                    likeButton.setImageResource(R.drawable.ic_like_vermelho)
                    apiService.favoritar(personId, treino.id)
                } else {
                    likeButton.setImageResource(R.drawable.ic_like)
                    apiService.desfavoritar(personId, treino.id)
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
