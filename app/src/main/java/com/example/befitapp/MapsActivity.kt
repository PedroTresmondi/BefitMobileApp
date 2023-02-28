package com.example.befitapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.kittinunf.fuel.Fuel
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
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

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

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true

            fusedLocationClient.lastLocation.addOnCompleteListener(
                this,
                object : OnCompleteListener<Location> {
                    override fun onComplete(task: Task<Location>) {
                        if (task.isSuccessful && task.result != null) {
                            val currentLocation: Location = task.result

                            val currentLatLng =
                                LatLng(currentLocation.latitude, currentLocation.longitude)
                            mMap.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    currentLatLng,
                                    15f
                                )
                            )

                            val url =
                                "https://maps.googleapis.com/maps/api/place/nearbysearch/json?" +
                                        "location=${currentLocation.latitude},${currentLocation.longitude}&" +
                                        "radius=2000&types=gym&" +
                                        "key=AIzaSyAghQgS67kmlLDvmOBAlyf7UDEjEmH_0R8"


                            Fuel.get(url).response { request, response, result ->
                                val jsonString = result.get().toString(Charsets.UTF_8)
                                val jsonArray = Json.parseToJsonElement(jsonString)
                                val gyms = jsonArray.jsonObject.get("results")?.jsonArray!!.map {
                                    Gym(
                                        name = it.jsonObject.get("name").toString(),
                                        location = LatLng(
                                            it.jsonObject.get("geometry")!!.jsonObject.get("location")!!.jsonObject.get(
                                                "lat"
                                            ).toString().toDouble(),
                                            it.jsonObject.get("geometry")!!.jsonObject.get("location")!!.jsonObject.get(
                                                "lng"
                                            ).toString().toDouble()
                                        ),
                                        address = it.jsonObject.get("vicinity").toString()
                                    )

                                }

                                gyms.forEach {
                                    mMap.addMarker(
                                        MarkerOptions().position(it.location)
                                            .title(it.name.replace("\"", ""))
                                            .snippet(it.address.replace("\"", ""))
                                    )
                                }
                            }
                        } else {
                            Toast.makeText(
                                this@MapsActivity,
                                "Erro ao obter localização atual",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
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