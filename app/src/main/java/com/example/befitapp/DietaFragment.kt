package com.example.befitapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.befitapp.entity.Catalogo


class DietaFragment : Fragment() {
    private val listaDietas = listOf(
        Catalogo(
            1,
            "DietaA",
            "descricaoA",
            "https://cdn-icons-png.flaticon.com/512/815/815128.png",
            true
        ),
        Catalogo(
            2,
            "DietaB",
            "descricaoB",
            "https://cdn-icons-png.flaticon.com/512/815/815128.png",
            false
        ),
        Catalogo(
            3,
            "DietaC",
            "descricaoC",
            "https://cdn-icons-png.flaticon.com/512/815/815128.png",
            false
        ),
        Catalogo(
            4,
            "DietaD",
            "descricaoD",
            "https://cdn-icons-png.flaticon.com/512/815/815128.png",
            true
        ),
        Catalogo(
            5,
            "DietaE",
            "descricaoE",
            "https://cdn-icons-png.flaticon.com/512/815/815128.png",
            false
        ),
        Catalogo(
            6,
            "DietaF",
            "descricaoF",
            "https://cdn-icons-png.flaticon.com/512/815/815128.png",
            true
        ),
    )

    private val adapter = CatalogoDietaAdapter(listaDietas)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dieta, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_dieta_catalogo)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return view
    }
}