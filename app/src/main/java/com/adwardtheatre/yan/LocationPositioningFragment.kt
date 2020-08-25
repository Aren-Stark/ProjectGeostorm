package com.adwardtheatre.yan

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import retrofit2.HttpException
import com.adwardtheatre.yan.CommonUtils


import android.annotation.SuppressLint
import kotlinx.coroutines.*
import com.adwardtheatre.yan.WhereAbouts
import com.adwardtheatre.yan.ApiClient
import android.os.Handler
import androidx.recyclerview.widget.RecyclerView
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.*
import com.adwardtheatre.yan.ImageUtils
import java.util.*
import com.adwardtheatre.yan.R



class LocationPositioningFragment : Fragment(), AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val apiService = ApiClient.getClient()
    private lateinit var timer: Timer
    private var timerTask: TimerTask? = null
    private val handler = Handler()
    private var mMarker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_positioning, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment : SupportMapFragment? =
            supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val isParsingSuccess = mMap.setMapStyle(
            MapStyleOptions.loadRawResourceStyle(
                this,
                R.raw.style_json
            )
        )
        if (!isParsingSuccess)
            Log.d("isParsingSuccess", "Style parsing failed")

        startTimerToGetIssLocation()
    }

    private fun initISSLocation(it: WhereAbouts) {
        Log.d("ISS_LOCATION", it.latitude + it.longitude)
        val longitudeStr: String = it.longitude
        val latitudeStr: String = it.latitude
        val iss = LatLng(CommonUtils.stringConvertDouble(latitudeStr), CommonUtils.stringConvertDouble(longitudeStr))
        val markerOptions = MarkerOptions().position(iss).title("ISS Location Now")
        if (mMarker == null) {
            mMarker = mMap.addMarker(
                markerOptions.icon(
                    ImageUtils.getBitmapDescriptor(
                        R.drawable.space_station, this
                    )
                )
            )
        } else {
            mMap.clear()
            mMap.addMarker(
                markerOptions.icon(
                    ImageUtils.getBitmapDescriptor(
                        R.drawable.space_station, this
                    )
                )
            )

        }
        mMap.animateCamera(CameraUpdateFactory.newLatLng(iss))
        circleAroundLocation(iss)
    }
    private fun circleAroundLocation(latLng: LatLng) {
        val circle = mMap.addCircle(
            CircleOptions()
                .center(latLng)
                .radius(10000.00)
                .strokeColor(Color.RED)
                .fillColor(Color.RED)
        )
        circle.center = latLng
    }
    private fun startTimerToGetIssLocation() {
        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                handler.post(Runnable {
                    CoroutineScope(Dispatchers.IO).launch {
                        val request = apiService.getISSNow()
                        try {
                            val response = request.await()
                            withContext(Dispatchers.Main) {
                                if (response.message == "success") {
                                    initISSLocation(response.whereAbouts)
                                }
                            }
                        } catch (e: HttpException) {
                            Log.d("REQUEST", "Exception ${e.message}")
                        } catch (e: Throwable) {
                            Log.d("REQUEST", "Ooops: Something else went wrong")
                        }

                    }
                })
            }
        }
        timer.schedule(timerTask, 5000, 5000)
    }

    //To stop timer
    private fun stopTimer() {
        timer.cancel()
        timer.purge()
    }

    override fun onDestroy() {
        super<Fragment>.onDestroy()
        stopTimer()
    }

}