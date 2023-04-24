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
import com.example.befitapp.entity.Catalogo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TreinoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_treino, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_treino_catalogo)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val personId = arguments?.getString("personId")
        recyclerView.adapter = CatalogoTreinoAdapter(emptyList(), personId!!)


        val call = BefitApi.http().getTreinos(personId)

        call.enqueue(object : Callback<List<Catalogo>> {
            override fun onResponse(call: Call<List<Catalogo>>, response: Response<List<Catalogo>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        recyclerView.adapter = CatalogoTreinoAdapter(it, personId)
                    }
                }
            }

            override fun onFailure(call: Call<List<Catalogo>>, t: Throwable) {
                Toast.makeText(context, "Network Error", Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }
}
