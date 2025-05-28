package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.OrdersCount
import com.example.khizana.domain.model.OrdersCountDomain

fun OrdersCount.toDomain(): OrdersCountDomain {
    return OrdersCountDomain(
        count = count
    )
}

