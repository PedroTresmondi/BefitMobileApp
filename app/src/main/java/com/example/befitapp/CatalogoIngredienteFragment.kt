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
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.befitapp.entity.BefitApi
import com.example.befitapp.entity.Dieta
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

        adapter = ItemIngredienteAdapter(lista, view.context)

        val layoutManager = LinearLayoutManager(view.context)

        recyclerView1.layoutManager = layoutManager

        recyclerView1.adapter = adapter

        val dietaId = arguments?.getInt("dieta_id")

        val call = BefitApi.http().getDietaUnique(dietaId!!)

        call.enqueue(object : Callback<Dieta> {
            override fun onResponse(
                call: Call<Dieta>,
                response: Response<Dieta>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        lista.addAll(it.ingredientes)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<Dieta>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })

        /*fragmentIngrediente.findViewById<ImageView>(R.id.iv_voltar_dieta).let {
            it.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }*/
    }
}