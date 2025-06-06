package com.example.khizana.domain.repository

import com.example.khizana.domain.model.OrderDomain
import com.example.khizana.domain.model.OrdersCountDomain
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    suspend fun getOrders(minDate: String, maxDate: String) : Flow<OrderDomain>
    suspend fun getOrdersCount(minDate: String, maxDate: String): Flow<OrdersCountDomain>
}