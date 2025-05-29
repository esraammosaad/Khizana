package com.example.khizana.data.repository

import com.example.khizana.data.dto.Order
import com.example.khizana.data.dto.OrdersCount
import com.example.khizana.data.dto.Product
import com.example.khizana.data.dto.ProductRequest

interface RemoteDataSource {

    suspend fun getProducts() : Product
    suspend fun createProduct(productRequest: ProductRequest)
    suspend fun getOrders() : Order
    suspend fun getOrdersCount(minDate: String, maxDate: String) : OrdersCount
    suspend fun deleteProduct(productId : String)

}