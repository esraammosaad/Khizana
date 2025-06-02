package com.example.khizana.data.dto

data class Location(
    val locations: List<LocationsItemEntity>
)

data class LocationsItemEntity(
    val country: String,
    val active: Boolean,
    val country_code: String,
    val name: String,
    val country_name: String,
    val id: String
)

