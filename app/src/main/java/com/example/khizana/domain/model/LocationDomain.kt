package com.example.khizana.domain.model

data class LocationDomain(
    val locations: List<LocationsItem>
)

data class LocationsItem(
    val country: String,
    val active: Boolean,
    val country_code: String,
    val name: String,
    val country_name: String,
    val id: String
)

