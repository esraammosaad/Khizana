package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.Location
import com.example.khizana.data.dto.LocationsItemEntity
import com.example.khizana.domain.model.LocationDomain
import com.example.khizana.domain.model.LocationsItem

fun Location.toDomain(): LocationDomain {
    return LocationDomain(
        locations = locations.map { it.toDomain() }
    )
}

fun LocationsItemEntity.toDomain(): LocationsItem {
    return LocationsItem(
        country = country,
        active = active,
        country_code = country_code,
        name = name,
        country_name = country_name,
        id = id
    )
}

