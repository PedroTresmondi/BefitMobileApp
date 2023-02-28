package com.example.befitapp

import com.google.android.gms.maps.model.LatLng

data class Gym(
    val name: String,
    val location: LatLng,
    val address: String
)