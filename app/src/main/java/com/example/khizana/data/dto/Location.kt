package com.example.khizana.data.dto

import com.google.gson.annotations.SerializedName

data class Location(
    val locations: List<LocationsItemEntity>
)

data class LocationsItemEntity(
    val country: String,
    val active: Boolean,
    @SerializedName("country_code")
    val countryCode: String,
    val name: String,
    @SerializedName("country_name")
    val countryName: String,
    val id: String
)

