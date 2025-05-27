package com.example.khizana.data.remote

import com.example.khizana.data.model.GetCollectsResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("collects.json")
    suspend fun getCollects(): Response<GetCollectsResponse>


}