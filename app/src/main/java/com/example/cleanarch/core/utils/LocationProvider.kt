package com.example.cleanarch.core.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient


class LocationProvider(private val activity: Activity, val locationChangeListener: LocationChangeListener) {

    lateinit var mLocationRequest: LocationRequest
    lateinit var mLocationCallback: LocationCallback

    init {
        startLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        mLocationRequest = LocationRequest().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = (10 * 1000).toLong()
            fastestInterval = 2000
        }

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        val locationSettingsRequest = builder.build()

        val settingsClient = LocationServices.getSettingsClient(activity)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val latitude = locationResult.lastLocation.latitude
                val longitude = locationResult.lastLocation.longitude
                if (latitude != 0.0 && longitude != 0.0) {
                    locationChangeListener.onLocationChange(
                        latitude,
                        longitude
                    )
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun start() {
        getFusedLocationProviderClient(activity).requestLocationUpdates(
            mLocationRequest, mLocationCallback, Looper.myLooper()
        )
    }

    fun stop() {
        getFusedLocationProviderClient(activity).removeLocationUpdates(mLocationCallback);
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        val locationClient = getFusedLocationProviderClient(activity)
        locationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    if (latitude != 0.0 && longitude != 0.0) {
                        locationChangeListener.onLocationChange(
                            latitude,
                            longitude
                        )
                    }
                }
            }.addOnFailureListener { e ->
                Log.d("LocationProvider", "Error trying to get last GPS location")
                e.printStackTrace()
            }
    }

}