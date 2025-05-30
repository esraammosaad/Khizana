package com.example.khizana.data.datasource.remote

import com.example.khizana.data.dto.CloudinaryResponse
import com.example.khizana.data.dto.Order
import com.example.khizana.data.dto.OrdersCount
import com.example.khizana.data.dto.Product
import com.example.khizana.data.dto.ProductRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("products.json?order=created_at+desc")
    suspend fun getProducts(): Product

    @POST("products.json")
    suspend fun createProduct(@Body product: ProductRequest) : ProductRequest

    @GET("orders.json?created_at_min=2025-05-20T00:00:00Z&created_at_max=2025-05-27T23:59:59Z&status=any")
    suspend fun getOrders(): Order

    @GET("orders/count.json")
    suspend fun getOrdersCountToday(
        @Query("created_at_min") minDate: String,
        @Query("created_at_max") maxDate: String,
        @Query("status") status: String = "any"
    ): OrdersCount

    @DELETE("products/{productId}.json")
    suspend fun deleteProduct(@Path("productId")  productId : String)

    @GET("products/{productId}.json")
    suspend fun getProductById(@Path("productId")  productId : String): ProductRequest


    @PUT("products/{productId}.json")
    suspend fun editProduct(@Path("productId")  productId : String, @Body product : ProductRequest)

    @Multipart
    @POST("v1_1/YOUR_CLOUD_NAME/image/upload")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("upload_preset") uploadPreset: RequestBody
    ): CloudinaryResponse

}