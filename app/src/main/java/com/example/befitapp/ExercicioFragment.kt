package com.example.befitapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.befitapp.entity.BefitApi
import com.example.befitapp.entity.Exercicio
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExercicioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentExercicio = inflater.inflate(R.layout.fragment_exercicio, container, false)
        val recyclerView: RecyclerView =
            fragmentExercicio.findViewById(R.id.recycler_view_item_exercicio)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val treinoId = arguments?.getInt("treino_id")

        val call = BefitApi.http().getTreino(treinoId!!)

        call.enqueue(object : Callback<List<Exercicio>> {
            override fun onResponse(
                call: Call<List<Exercicio>>,
                response: Response<List<Exercicio>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        recyclerView.adapter = ExercicioItemAdapter(it, fragmentExercicio)

                        fragmentExercicio.findViewById<TextView>(R.id.tv_exercicio_nome_titulo).text =
                            it[0].nome.uppercase()

                        Picasso.get().load(it[0].imagem)
                            .into(fragmentExercicio.findViewById<ImageView>(R.id.iv_exercicio))

                        fragmentExercicio.findViewById<TextView>(R.id.numero_serie).text =
                            it[0].repeticao.toString()

                        fragmentExercicio.findViewById<TextView>(R.id.numero_repeticao).text =
                            it[0].quantidade.toString()
                    }
                }
            }

            override fun onFailure(call: Call<List<Exercicio>>, t: Throwable) {
                Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })

        return fragmentExercicio
    }
}