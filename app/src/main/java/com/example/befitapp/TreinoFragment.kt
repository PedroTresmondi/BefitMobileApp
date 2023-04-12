package com.example.befitapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.befitapp.entity.Catalogo
import com.example.befitapp.service.BeFitApiService
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val GLOBAL_PERSONID_NAME: String = "5bb7a32c-20ff-42d2-b684-33bf61f6eb13"

class TreinoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_treino, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_treino_catalogo)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CatalogoTreinoAdapter(emptyList())

        val call = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().let {
                it.create(BeFitApiService::class.java).getTreinos(GLOBAL_PERSONID_NAME)
            }

        call.enqueue(object : Callback<List<Catalogo>> {
            override fun onResponse(call: Call<List<Catalogo>>, response: Response<List<Catalogo>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        recyclerView.adapter = CatalogoTreinoAdapter(it)
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
