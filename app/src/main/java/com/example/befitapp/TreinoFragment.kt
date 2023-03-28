package com.example.befitapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.befitapp.entity.Catalogo
import com.google.android.gms.common.util.CollectionUtils.listOf


class TreinoFragment : Fragment() {

    private val listaTreinos = listOf(
        Catalogo("TreinoA", "descricaoA", "https://saude.abril.com.br/wp-content/uploads/2020/05/treino-em-casa.png?w=680&h=453&crop=1", true),
        Catalogo("TreinoB", "descricaoB", "https://saude.abril.com.br/wp-content/uploads/2020/05/treino-em-casa.png?w=680&h=453&crop=1", false),
        Catalogo("TreinoC", "descricaoC", "https://saude.abril.com.br/wp-content/uploads/2020/05/treino-em-casa.png?w=680&h=453&crop=1", false),
        Catalogo("TreinoD", "descricaoD", "https://saude.abril.com.br/wp-content/uploads/2020/05/treino-em-casa.png?w=680&h=453&crop=1", true),
        Catalogo("TreinoE", "descricaoE", "https://saude.abril.com.br/wp-content/uploads/2020/05/treino-em-casa.png?w=680&h=453&crop=1", false),
        Catalogo("TreinoF", "descricaoF", "https://saude.abril.com.br/wp-content/uploads/2020/05/treino-em-casa.png?w=680&h=453&crop=1", true),
    )

    private val adapter = CatalogoAdapter(listaTreinos)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_treino, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_treino_catalogo)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return view
    }
}
