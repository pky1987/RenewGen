package com.example.re.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.re.R
import com.example.re.adapters.LocationAdapter
import com.example.re.services.TrackingService
import com.example.re.viewmodels.LocationViewModel
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.*

class GpsActivity : AppCompatActivity() {
    private lateinit var viewModel: LocationViewModel
    private lateinit var adapter: LocationAdapter
    private val requiredPermissions = arrayListOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
        // Only add FOREGROUND_SERVICE_LOCATION for API 34 and above
        if (Build.VERSION.SDK_INT >= 34) {
            add("android.permission.FOREGROUND_SERVICE_LOCATION")
        }
    }.toTypedArray()

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { grants ->
        if (grants.all { it.value }) {
            startTrackingService()
            setupObservers()
        } else {
            Toast.makeText(this, "All permissions required", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)
        initializeComponents()
        setupRecyclerView()
        setupButtonListener()
        checkPermissions()
    }

    private fun initializeComponents() {
        viewModel = ViewModelProvider(this)[LocationViewModel::class.java]
        adapter = LocationAdapter()
    }

    private fun setupRecyclerView() {
        findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(this@GpsActivity)
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(this@GpsActivity, LinearLayoutManager.VERTICAL))
            itemAnimator = null
            adapter = this@GpsActivity.adapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (!canScrollVertically(1)) viewModel.loadMore()
                }
            })
        }
    }

    private fun setupButtonListener() {
        findViewById<Button>(R.id.btn_toggle).setOnClickListener {
            val currentState = viewModel.isTracking.value ?: false
            val newState = !currentState
            viewModel.setTracking(newState)
            updateServiceState(newState)
            (it as Button).text = if (newState) "Stop Tracking" else "Start Tracking"
        }
    }

    private fun checkPermissions() {
        if (requiredPermissions.all { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }) {
            startTrackingService()
            setupObservers()
        } else {
            permissionLauncher.launch(requiredPermissions)
        }
    }

    private fun startTrackingService() {
        try {
            Intent(this, TrackingService::class.java).let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(it)
                } else {
                    startService(it)
                }
            }
        } catch (e: IllegalStateException) {
            Toast.makeText(this, "Service start failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateServiceState(enable: Boolean) {
        if (enable) {
            startTrackingService()
        } else {
            stopService(Intent(this, TrackingService::class.java))
        }
    }

    private fun setupObservers() {
        viewModel.locations.observe(this) { locations ->
            adapter.submitList(locations)
            if (!locations.isNullOrEmpty()) {
                findViewById<RecyclerView>(R.id.recycler_view).smoothScrollToPosition(0)
            }
        }
        viewModel.isTracking.observe(this) { isTracking ->
            findViewById<Button>(R.id.btn_toggle).apply {
                text = if (isTracking) "Stop Tracking" else "Start Tracking"
                isEnabled = true
            }
        }
    }

    private fun updateLocationUI(location: com.example.re.data.LocationEntity) {
        findViewById<TextView>(R.id.tvAddress).text = "Address: ${location.address}"
        findViewById<TextView>(R.id.tvCity).text = "City: ${location.city}"
        findViewById<TextView>(R.id.tvState).text = "State: ${location.state}"
        findViewById<TextView>(R.id.tvCountry).text = "Country: ${location.country}"
        findViewById<TextView>(R.id.tvCoordinates).text = 
            "Coordinates: ${String.format("%.6f, %.6f", location.latitude, location.longitude)}"
        findViewById<TextView>(R.id.tvAccuracy).text = 
            "Accuracy: ${String.format("%.1f meters", location.accuracy)}"
    }

    override fun onDestroy() {
        super.onDestroy()
        if (viewModel.isTracking.value == true) {
            updateServiceState(false)
        }
    }
} 