package com.example.befitapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.befitapp.entity.BefitApi
import com.example.befitapp.entity.Catalogo
import com.example.befitapp.entity.Dieta
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DietaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dieta, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_dieta_catalogo)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val personId = arguments?.getString("personId")
        recyclerView.adapter = CatalogoTreinoAdapter(emptyList(), personId!!)


        val call = BefitApi.http().getDietas(personId)

        call.enqueue(object : Callback<List<Dieta>> {
            override fun onResponse(call: Call<List<Dieta>>, response: Response<List<Dieta>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        recyclerView.adapter = CatalogoDietaAdapter(it, personId)
                    }
                }
            }

            override fun onFailure(call: Call<List<Dieta>>, t: Throwable) {
                Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }
}