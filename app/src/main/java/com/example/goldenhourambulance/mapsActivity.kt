package com.example.goldenhourambulance

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class mapsActivity : AppCompatActivity(), OnMapReadyCallback  {

    private var mGoogleMap:GoogleMap? = null
    private var fusedLocationClient: FusedLocationProviderClient? = null

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Check and request location permission if needed
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            initMap()
        }
    }


    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (isGooglePlayServicesAvailable()) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
                    location?.let {
                        val userLatLng = LatLng(it.latitude, it.longitude)

                        // Clear existing markers
                        mGoogleMap?.clear()

                        // Add a red marker with a custom icon at the user's location
                        mGoogleMap?.addMarker(
                            MarkerOptions()
                                .position(userLatLng)
                                .title("Your Location")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulance_marker))
                        )

                        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15f))
                    } ?: run {
                        // Handle the case where last known location is null
                        setDefaultLocation()
                    }
                }?.addOnFailureListener { e ->
                    // Handle the failure to retrieve the location
                    setDefaultLocation()
                }
            }
        } else {
            // Google Play Services is not available
            setDefaultLocation()
        }
    }



    private fun setDefaultLocation() {
        // Default to a specific location (e.g., city center) if last known location is null or GPS is unavailable
        val defaultLocation = LatLng(28.7532, 77.4971)
        mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 15f))
    }
    private fun isGooglePlayServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this)
        return resultCode == ConnectionResult.SUCCESS
    }

    override fun onMapReady(googleMap: GoogleMap)
    {
        mGoogleMap = googleMap

        // Enable the user's location on the map
        mGoogleMap?.isMyLocationEnabled = true

        // Get the last known location and add a marker
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient?.lastLocation?.addOnSuccessListener { location ->
                location?.let {
                    val userLatLng = LatLng(it.latitude, it.longitude)
                    mGoogleMap?.addMarker(MarkerOptions().position(userLatLng).title("Your Location"))
                    mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15f))
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initMap()
        }
    }

}