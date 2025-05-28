package com.example.khizana.data.datasource.remote

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.khizana.data.dto.Order
import com.example.khizana.data.dto.OrdersCount
import com.example.khizana.data.dto.Product
import com.example.khizana.utilis.Strings
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

//    @GET("collects.json")
//    suspend fun getCollects(): Response<GetCollectsResponse>
//
//    @GET("collections/{collectsId}.json")
//    suspend fun getCollectsById(@Path("collectsId")  collectsId : Long): Response<GetCollectByIdResponse>

    @GET("products.json")
    suspend fun getProducts(): Product

    @GET("orders.json?created_at_min=2025-05-20T00:00:00Z&created_at_max=2025-05-27T23:59:59Z&status=any")
    suspend fun getOrders(): Order

    @GET("orders/count.json")
    suspend fun getOrdersCountToday(
        @Query("created_at_min") minDate: String,
        @Query("created_at_max") maxDate: String,
        @Query("status") status: String = "any"
    ): OrdersCount


//    @GET("products/{productId}.json")
//    suspend fun getProductById(@Path("productId")  productId : Long): Response<GetProduct>
//
//    @POST("products.json")
//    suspend fun createProduct(@Body product : GetProduct): Response<GetProduct>
//
//    @PUT("products/{productId}.json")
//    suspend fun editProduct(@Path("productId")  productId : Long, @Body product : GetProduct): Response<GetProduct>

}