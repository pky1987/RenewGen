package com.example.re.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.re.data.LocationDao
import com.example.re.data.LocationEntity
import com.example.re.data.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    private val locationDao: LocationDao = AppDatabase.getDatabase(application).locationDao()
    val allLocations: LiveData<List<LocationEntity>> = locationDao.getAllLocations()
    val lastLocation: LiveData<LocationEntity?> = locationDao.getLastLocation()
    
    private val _isTracking = MutableLiveData<Boolean>()
    val isTracking: LiveData<Boolean> = _isTracking
    
    private val _locations = MutableLiveData<List<LocationEntity>>()
    val locations: LiveData<List<LocationEntity>> = _locations

    init {
        _isTracking.value = false
        loadLocations()
    }

    fun setTracking(tracking: Boolean) {
        _isTracking.value = tracking
    }

    fun loadMore() {
        viewModelScope.launch(Dispatchers.IO) {
            // Implement pagination logic here
            // For now, just reload all locations
            loadLocations()
        }
    }

    private fun loadLocations() {
        viewModelScope.launch(Dispatchers.IO) {
            val locationList = locationDao.getAllLocationsSync()
            _locations.postValue(locationList)
        }
    }

    fun insertLocation(location: LocationEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            locationDao.insertLocation(location)
            loadLocations()
        }
    }

    fun deleteAllLocations() {
        viewModelScope.launch(Dispatchers.IO) {
            locationDao.deleteAllLocations()
            loadLocations()
        }
    }

    fun deleteLocation(location: LocationEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            locationDao.deleteLocation(location)
            loadLocations()
        }
    }

    fun getLocationsByTimeRange(startTime: Long, endTime: Long): LiveData<List<LocationEntity>> {
        return locationDao.getLocationsByTimeRange(startTime, endTime)
    }

    fun getTodayLocations(): LiveData<List<LocationEntity>> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val startTime = calendar.timeInMillis
        val endTime = System.currentTimeMillis()
        return getLocationsByTimeRange(startTime, endTime)
    }
} 