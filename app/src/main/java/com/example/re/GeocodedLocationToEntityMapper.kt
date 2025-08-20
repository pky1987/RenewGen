package com.example.re

object GeocodedLocationToEntityMapper {
    fun mapToLocationEntity(geocodedLocation: GeocodedLocation): LocationEntity {
        return LocationEntity(
            latitude = geocodedLocation.latitude,
            longitude = geocodedLocation.longitude,
            locality = geocodedLocation.locality,
            area = geocodedLocation.area,
            city = geocodedLocation.city,
            district = geocodedLocation.district,
            state = geocodedLocation.state,
            country = geocodedLocation.country,
            postalCode = geocodedLocation.postalCode,
            timestamp = geocodedLocation.timestamp
        )
    }
}
