package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.Count
import com.example.khizana.domain.model.CountDomain

fun Count.toDomain(): CountDomain {
    return CountDomain(
        count = count
    )
}

