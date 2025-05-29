package com.example.khizana.data.datasource.remote


import com.example.khizana.data.dto.Order
import com.example.khizana.data.dto.OrdersCount
import com.example.khizana.data.dto.Product
import com.example.khizana.data.dto.ProductRequest
import com.example.khizana.data.repository.RemoteDataSource

class RemoteDataSourceImpl(private val apiService: ApiService) : RemoteDataSource{

    override suspend fun getProducts() : Product {
        return apiService.getProducts()
    }

    override suspend fun createProduct(productRequest: ProductRequest)  {
        apiService.createProduct(productRequest)
    }

    override suspend fun getOrders() : Order {
        return apiService.getOrders()
    }

    override suspend fun getOrdersCount(minDate: String, maxDate: String) : OrdersCount {
        return apiService.getOrdersCountToday(minDate, maxDate)
    }


}