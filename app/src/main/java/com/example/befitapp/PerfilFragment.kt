package com.example.befitapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.befitapp.service.MapApiService
import com.google.android.gms.common.util.CollectionUtils.listOf
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PerfilFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    private val listaTreinos = listOf(
        "Treino A",
        "Treino B",
        "Treino C"
    )

    private val listaDietas = listOf(
        "Dieta A",
        "Dieta B",
        "Dieta C"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)
        view.findViewById<TextView>(R.id.nome).let {
            it.text = "Bem vindo! ${arguments?.getString("nome")}"
        }
        val recyclerViewTreinos: RecyclerView = view.findViewById(R.id.recycler_view_treinos)
        recyclerViewTreinos.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewTreinos.adapter = FavoritoAdapter(listaTreinos, "treino")

        val recyclerViewDietas: RecyclerView = view.findViewById(R.id.recycler_view_dietas)
        recyclerViewDietas.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewDietas.adapter = FavoritoAdapter(listaDietas, "dieta")

        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        return view
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onMapReady(googleMap)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }


    override fun onMapReady(map: GoogleMap) {
        map.let {
            googleMap = it
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                googleMap.isMyLocationEnabled = true
                googleMap.uiSettings.isMyLocationButtonEnabled = true
                googleMap.setOnMyLocationButtonClickListener {
                    Toast.makeText(
                        requireContext(),
                        "My Location button clicked",
                        Toast.LENGTH_SHORT
                    ).show()
                    false
                }
                val fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireActivity())
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val currentLatLng = LatLng(location.latitude, location.longitude)

                        requireActivity().runOnUiThread {
                            googleMap.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    currentLatLng,
                                    15f
                                )
                            )
                        }

                        createMarkers(currentLatLng.latitude, currentLatLng.longitude, googleMap)
                    }
                }
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION
                )
            }
        }
    }

    private fun createMarkers(latitude: Double, longitude: Double, googleMap: GoogleMap) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/place/nearbysearch/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val mapApiService = retrofit.create(MapApiService::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val gyms = mapApiService.getGyms("$latitude,$longitude")

                withContext(Dispatchers.Main) {
                    gyms.results.forEach { gym ->
                        googleMap.addMarker(
                            MarkerOptions().position(
                                LatLng(
                                    gym.geometry.location.lat,
                                    gym.geometry.location.lng
                                )
                            ).title(gym.name.replace("\"", ""))
                                .snippet(gym.vicinity.replace("\"", ""))
                        )
                    }
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }


    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
}