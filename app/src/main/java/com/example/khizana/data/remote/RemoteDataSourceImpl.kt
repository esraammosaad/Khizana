package com.example.khizana.data.remote

import com.example.khizana.data.model.GetCollectByIdResponse
import com.example.khizana.data.model.GetCollectsResponse
import com.example.khizana.data.model.GetProductsResponse
import retrofit2.Response

class RemoteDataSourceImpl(private val apiService: ApiService) {

    suspend fun getCollects() : Response<GetCollectsResponse> {
        return apiService.getCollects()
    }
    suspend fun getCollectsById(collectsId : Long) : Response<GetCollectByIdResponse> {
        return apiService.getCollectsById(collectsId)
    }

    suspend fun getProducts() : Response<GetProductsResponse> {
        return apiService.getProducts()
    }


}