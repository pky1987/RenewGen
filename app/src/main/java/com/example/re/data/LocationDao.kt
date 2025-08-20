package com.example.re.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationEntity)

    @Query("SELECT * FROM location_history ORDER BY timestamp DESC")
    fun getAllLocations(): LiveData<List<LocationEntity>>

    @Query("SELECT * FROM location_history ORDER BY timestamp DESC")
    suspend fun getAllLocationsSync(): List<LocationEntity>

    @Query("SELECT * FROM location_history ORDER BY timestamp DESC LIMIT 1")
    fun getLastLocation(): LiveData<LocationEntity?>

    @Query("DELETE FROM location_history")
    suspend fun deleteAllLocations()

    @Delete
    suspend fun deleteLocation(location: LocationEntity)

    @Query("SELECT * FROM location_history WHERE timestamp >= :startTime AND timestamp <= :endTime ORDER BY timestamp DESC")
    fun getLocationsByTimeRange(startTime: Long, endTime: Long): LiveData<List<LocationEntity>>
} 