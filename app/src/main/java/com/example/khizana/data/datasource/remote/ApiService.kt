package com.example.khizana.data.datasource.remote

import com.example.khizana.data.dto.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

//    @GET("collects.json")
//    suspend fun getCollects(): Response<GetCollectsResponse>
//
//    @GET("collections/{collectsId}.json")
//    suspend fun getCollectsById(@Path("collectsId")  collectsId : Long): Response<GetCollectByIdResponse>

    @GET("products.json")
    suspend fun getProducts(): Product

//    @GET("products/{productId}.json")
//    suspend fun getProductById(@Path("productId")  productId : Long): Response<GetProduct>
//
//    @POST("products.json")
//    suspend fun createProduct(@Body product : GetProduct): Response<GetProduct>
//
//    @PUT("products/{productId}.json")
//    suspend fun editProduct(@Path("productId")  productId : Long, @Body product : GetProduct): Response<GetProduct>

}