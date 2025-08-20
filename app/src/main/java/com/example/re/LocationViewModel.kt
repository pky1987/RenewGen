package com.example.re

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application)
    private val _locations = MutableLiveData<List<LocationEntity>>()
    private val _isTracking = MutableLiveData(false)
    val locations: LiveData<List<LocationEntity>> = _locations
    val isTracking: LiveData<Boolean> = _isTracking
    private var currentPage = 0
    private val pageSize = 20

    fun loadMore() {
        viewModelScope.launch(Dispatchers.IO) {
            val newItems = dao.getRecent(currentPage * pageSize, pageSize)
            _locations.postValue((_locations.value ?: emptyList()) + newItems)
            currentPage++
        }
    }

    fun setTracking(enabled: Boolean) {
        _isTracking.value = enabled
        if (enabled) refreshData()
    }

    fun addLocation(location: LocationEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(location)
            resetPagination()
        }
    }

    private fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            _locations.postValue(dao.getRecent(0, pageSize))
            currentPage = 1
        }
    }

    private fun resetPagination() {
        currentPage = 0
        refreshData()
    }
}