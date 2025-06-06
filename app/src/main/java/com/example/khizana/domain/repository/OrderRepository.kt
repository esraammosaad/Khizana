package com.example.khizana.domain.repository

import com.example.khizana.domain.model.OrderDomain
import com.example.khizana.domain.model.OrdersCountDomain

interface OrderRepository {

    suspend fun getOrders(minDate: String, maxDate: String) : OrderDomain
    suspend fun getOrdersCount(minDate: String, maxDate: String): OrdersCountDomain
}