package com.example.befitapp

import android.app.AlertDialog
import android.content.DialogInterface
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
import com.example.befitapp.entity.DietaCompleta
import com.example.befitapp.entity.Ingrediente
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CatalogoIngredienteFragment : Fragment() {

    lateinit var adapter:ItemIngredienteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ingredientes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lista = mutableListOf<Ingrediente>()

        val recyclerView1 = view?.findViewById<RecyclerView>(R.id.recycler_view_item_ingrediente)!!
        val tituloDieta = view.findViewById<TextView>(R.id.tv_dieta_nome_titulo)

        val viewInfo: ImageView = view.findViewById(R.id.iv_informacao_dieta)

        adapter = ItemIngredienteAdapter(lista, view.context)

        val layoutManager = LinearLayoutManager(view.context)

        recyclerView1.layoutManager = layoutManager

        recyclerView1.adapter = adapter

        val dietaId = arguments?.getInt("dieta_id")


        val call = BefitApi.http().getDietaUnique(dietaId!!)

        call.enqueue(object : Callback<DietaCompleta> {
            override fun onResponse(
                call: Call<DietaCompleta>,
                response: Response<DietaCompleta>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { dietaCompleta ->
                        lista.addAll(dietaCompleta.ingredientes)
                        tituloDieta.text = dietaCompleta.dieta.nome
                        viewInfo.setOnClickListener {
                            val builder = AlertDialog.Builder(context)
                            builder.setMessage(dietaCompleta.dieta.descricao)
                                .setCancelable(false)
                                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                                    dialog.dismiss()
                                })
                            val alert = builder.create()
                            alert.show()
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<DietaCompleta>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })

        view.findViewById<ImageView>(R.id.iv_voltar_dieta).let {
            it.setOnClickListener {
                parentFragmentManager.popBackStack()
            }


            //
        }
    }
}