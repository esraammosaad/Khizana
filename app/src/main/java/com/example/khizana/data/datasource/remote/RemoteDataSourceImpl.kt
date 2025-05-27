package com.example.khizana.data.datasource.remote


import com.example.khizana.data.dto.Order
import com.example.khizana.data.dto.Product
import com.example.khizana.data.repository.RemoteDataSource

class RemoteDataSourceImpl(private val apiService: ApiService) : RemoteDataSource{

//    suspend fun getCollects() : Response<GetCollectsResponse> {
//        return apiService.getCollects()
//    }
//    suspend fun getCollectsById(collectsId : Long) : Response<GetCollectByIdResponse> {
//        return apiService.getCollectsById(collectsId)
//    }

    override suspend fun getProducts() : Product {
        return apiService.getProducts()
    }

    override suspend fun getOrders() : Order {
        return apiService.getOrders()
    }


}