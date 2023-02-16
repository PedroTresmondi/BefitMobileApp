package com.example.befitapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class HomeActivity : AppCompatActivity() {

    private val places = arrayListOf(
        Place("Academia 1", LatLng(-23.5621063,-46.6717574),"R. da Consolação, 2929 - Jardins, São Paulo - SP", 4.3f),
        Place("Academia 2", LatLng(-23.5602708,-46.66327),"R. da Consolação, 3002-3190 - Cerqueira César, São Paulo - SP", 3.3f),
        Place("Academia 3", LatLng(-23.5602932,-46.6642),"Rua Haddock Lobo, 846 - sala 105A - Cerqueira César, São Paulo - SP, 01414-000", 4.9f),
        Place("Academia 4", LatLng(-23.5602702,-46.6699),"R. Bela Cintra, 1631 - Cerqueira César, São Paulo - SP", 2.5f),

    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync{googleMap ->
            addMarkers(googleMap)
            googleMap.setInfoWindowAdapter(MarkerInfoAdapter(this))

            googleMap.setOnMapLoadedCallback {
                val bounds = LatLngBounds.builder()
                places.forEach{
                    bounds.include(it.latLng)
                }
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 100))
            }
        }
    }
    private fun addMarkers(googleMap: GoogleMap){
        places.forEach{place ->
          val marker = googleMap.addMarker(
                MarkerOptions()
                    .title(place.name)
                    .snippet(place.adress)
                    .position(place.latLng)
                    .icon(
                    BitMapHelper.vectorToBitMap(this, R.drawable.baseline_fitness_center_24,
                        ContextCompat.getColor(this, R.color.primary_color))
                    )
            )
            marker.tag = place
        }
    }
}

data class Place(
    val name: String,
    val latLng: LatLng,
    val adress: String,
    val rating: Float
)


