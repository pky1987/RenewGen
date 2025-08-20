package com.example.re

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.example.re.activities.GpsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class TrackingService : Service() {
    private var fusedClient: FusedLocationProviderClient? = null
    private lateinit var viewModel: LocationViewModel
    private val scope = CoroutineScope(Dispatchers.IO)
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            result.lastLocation?.let(::processLocation)
        }
    }
    private var lastGeocodeTime = 0L

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(1, createNotification())
        if (checkPermissions()) {
            initDependencies()
            startLocationUpdates()
        } else {
            stopSelf()
        }
    }

    private fun checkPermissions(): Boolean {
        val hasForeground = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE_LOCATION) == PackageManager.PERMISSION_GRANTED
        } else true
        return hasForeground && (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }

    private fun initDependencies() {
        fusedClient = LocationServices.getFusedLocationProviderClient(this)
        viewModel = LocationViewModel(application)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000L)
            .setMinUpdateIntervalMillis(5000L)
            .build()
        fusedClient?.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())
    }

    private fun processLocation(location: Location) {
        if (System.currentTimeMillis() - lastGeocodeTime < 30000) return
        scope.launch {
            try {
                val addresses = withContext(Dispatchers.IO) {
                    Geocoder(this@TrackingService, Locale.getDefault())
                        .getFromLocation(location.latitude, location.longitude, 1)
                }
                lastGeocodeTime = System.currentTimeMillis()
                addresses?.firstOrNull()?.let { address ->
                    viewModel.addLocation(
                        LocationEntity(
                            latitude = location.latitude,
                            longitude = location.longitude,
                            locality = address.locality ?: "N/A",
                            area = address.subLocality ?: "N/A",
                            city = address.subAdminArea ?: "N/A",
                            district = address.subAdminArea ?: "N/A",
                            state = address.adminArea ?: "N/A",
                            country = address.countryName ?: "N/A",
                            postalCode = address.postalCode ?: "N/A",
                            timestamp = System.currentTimeMillis()
                        )
                    )
                }
            } catch (e: Exception) {
                viewModel.addLocation(
                    LocationEntity(
                        latitude = location.latitude,
                        longitude = location.longitude,
                        locality = "Unknown",
                        area = "Unknown",
                        city = "Unknown",
                        district = "Unknown",
                        state = "Unknown",
                        country = "Unknown",
                        postalCode = "Unknown",
                        timestamp = System.currentTimeMillis()
                    )
                )
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                "tracking_channel",
                "Location Tracking",
                NotificationManager.IMPORTANCE_LOW
            ).also {
                (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
                    .createNotificationChannel(it)
            }
        }
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, "tracking_channel")
            .setContentTitle("Location Tracking Active")
            .setContentText("Recording location data")
            .setSmallIcon(R.drawable.gps)
            .setContentIntent(
                PendingIntent.getActivity(
                    this,
                    0,
                    Intent(this, GpsActivity::class.java),
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedClient?.removeLocationUpdates(locationCallback)
    }
}