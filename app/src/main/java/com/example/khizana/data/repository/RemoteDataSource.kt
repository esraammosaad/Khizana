package com.example.khizana.data.repository

import com.example.khizana.data.dto.Order
import com.example.khizana.data.dto.Product

interface RemoteDataSource {

    suspend fun getProducts() : Product
    suspend fun getOrders() : Order

}