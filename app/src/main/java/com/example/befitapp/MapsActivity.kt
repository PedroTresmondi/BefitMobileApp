package com.example.befitapp

import android.Manifest
import android.app.DownloadManager
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.Places

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private fun searchNearbyGyms(latLng: LatLng) {
        val apiKey = getString(R.string.google_maps_key)
        val placesClient = Places.createClient(this)
        val radius = 5000 // Raio de busca em metros
        val type = "gym" // Tipo de lugar a ser procurado
        val location = "${latLng.latitude},${latLng.longitude}"
        val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                "location=$location&radius=$radius&type=$type&key=$apiKey"

        val request = JsonObjectRequest(
            DownloadManager.Request.Method.GET, url, null,
            { response ->
                val results = response.getJSONArray("results")
                for (i in 0 until results.length()) {
                    val result = results.getJSONObject(i)
                    val name = result.getString("name")
                    val placeId = result.getString("place_id")
                    val location = result.getJSONObject("geometry").getJSONObject("location")
                    val lat = location.getDouble("lat")
                    val lng = location.getDouble("lng")
                    val latLng = LatLng(lat, lng)
                    mMap.addMarker(MarkerOptions().position(latLng).title(name))
                }
            },
            { error ->
                Log.e(TAG, "Error searching nearby gyms", error)
                Toast.makeText(this, "Error searching nearby gyms", Toast.LENGTH_SHORT).show()
            })

        placesClient.fetchPlace(request)
    }


    private fun updateMapLocation(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(latLng).title("Minha localização"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        searchNearbyGyms(latLng)
    }




    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true

            fusedLocationClient.lastLocation.addOnCompleteListener(this, object : OnCompleteListener<Location> {
                override fun onComplete(task: Task<Location>) {
                    if (task.isSuccessful && task.result != null) {
                        val currentLocation: Location = task.result

                        val currentLatLng = LatLng(currentLocation.latitude, currentLocation.longitude)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))

                        val gymList = mapOf(
                            "Academia 1" to LatLng(-23.564198, -46.651851),
                            "Academia 2" to LatLng(-23.563345, -46.649067),
                            "Academia 3" to LatLng(-23.563803, -46.650970),
                            "Academia 4" to LatLng(-23.564405, -46.652503)
                        )

                        for ((name, position) in gymList) {
                            mMap.addMarker(MarkerOptions().position(position).title(name))
                        }
                    } else {
                        Toast.makeText(this@MapsActivity, "Erro ao obter localização atual", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        } else {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                onMapReady(mMap)
            } else {
                Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show()
            }
        }
    }
}