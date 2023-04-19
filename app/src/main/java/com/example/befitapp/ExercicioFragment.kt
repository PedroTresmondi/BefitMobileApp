package com.example.befitapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.befitapp.entity.BefitApi
import com.example.befitapp.entity.Exercicio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExercicioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recycler = inflater.inflate(R.layout.fragment_exercicio, container, false)
        val recyclerView: RecyclerView = recycler.findViewById(R.id.recycler_view_item_exercicio)
        recyclerView.layoutManager = LinearLayoutManager(context)


        val exercises = listOf(
            Exercicio(
                idExercicio = 1,
                nome = "Supino reto",
                descricao = "Deixar o peso descer lentamente em direção ao peito, até que a barra chegue bem perto do corpo. Depois, basta estender os cotovelos até a barra retornar à posição original, quando é contada uma repetição",
                imagem = "https://grandeatleta.com.br/wp-content/uploads/2018/09/supino-reto.jpg",
                quantidade = 15,
                tempo = "00:01:00",
                repeticao = 3
            ),
            Exercicio(
                idExercicio = 2,
                nome = "Supino inclinado",
                descricao = "O supino inclinado consiste essencialmente em uma flexão de ombro horizontal seguida por uma extensão de cotovelo na barra, deitado em um banco levemente inclinado para cima. A pegada na barra deve ser feita em uma distancia ligeiramente maior que a distancia entre os ombros.",
                imagem = "https://blog.gsuplementos.com.br/wp-content/uploads/2021/04/iStock-1246046696.jpg",
                quantidade = 12,
                tempo = "00:01:00",
                repeticao = 3
            ),
            Exercicio(
                idExercicio = 3,
                nome = "Supino declinado",
                descricao = "O supino reto consiste essencialmente em uma flexão de ombro horizontal seguida por uma extensão de cotovelo na barra, deitado em um banco levemente declinado para baixo, negativando a execuçao do movimeto e tensionando mais o musculo. A pegada na barra deve ser feita em uma distancia ligeiramente maior que a distancia entre os ombros.",
                imagem = "https://i.ytimg.com/vi/ifWEwZDWMAw/maxresdefault.jpg",
                quantidade = 12,
                tempo = "00:01:00",
                repeticao = 3
            ),
            Exercicio(
                idExercicio = 4,
                nome = "Peck Deck",
                descricao = "É fundamental que o cotovelo fique abaixo do ombro, o movimento deve ser feito sem que o cotovelo desencoste do apoio o movimento deve ser feito ate o final da amplitude do aparelho para garantir uma boa execução.",
                imagem = "http://aparelhodemusculacaosaj.com.br/image/cache/data/Produtos/peitoral/GPM65_Ex_Fly-700x700.png",
                quantidade = 12,
                tempo = "00:01:00",
                repeticao = 3
            ))

        recyclerView.adapter = ExercicioItemAdapter(exercises)



//        val call = BefitApi.http().getTreino(1)
//        call.enqueue(object : Callback<List<Exercicio>> {
//            override fun onResponse(
//                call: Call<List<Exercicio>>,
//                response: Response<List<Exercicio>>
//            ) {
//                if (response.isSuccessful) {
//                    response.body()?.let {
//                        println(it)
//                        recyclerView.adapter = ExercicioItemAdapter(it)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<List<Exercicio>>, t: Throwable) {
//                Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show()
//                println(t.message)
//                println(t.stackTraceToString())
//                println(println(call))
//                println(t.localizedMessage)
//                println(t.cause)
//            }
//
//        })

        return recycler
    }
}