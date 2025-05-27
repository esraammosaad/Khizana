package com.example.khizana.data.remote

import com.example.khizana.data.model.GetCollectsResponse
import retrofit2.Response

class RemoteDataSourceImpl(private val apiService: ApiService) {

    suspend fun getCollects() : Response<GetCollectsResponse> {
        return apiService.getCollects()
    }


}