package com.example.khizana.domain.repository

import com.example.khizana.domain.model.OrderDomain

interface OrderRepository {

    suspend fun getOrders() : OrderDomain
}