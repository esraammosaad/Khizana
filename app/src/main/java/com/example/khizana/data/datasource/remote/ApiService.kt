package com.example.khizana.data.datasource.remote

import com.example.khizana.data.dto.InventoryLevel
import com.example.khizana.data.dto.InventoryLevelRequest
import com.example.khizana.data.dto.Location
import com.example.khizana.data.dto.Order
import com.example.khizana.data.dto.OrdersCount
import com.example.khizana.data.dto.PriceRule
import com.example.khizana.data.dto.Product
import com.example.khizana.data.dto.ProductRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("products.json?order=created_at+desc")
    suspend fun getProducts(): Product

    @POST("products.json")
    suspend fun createProduct(@Body product: ProductRequest): ProductRequest

    @GET("orders.json?created_at_min=2025-05-20T00:00:00Z&created_at_max=2025-05-27T23:59:59Z&status=any")
    suspend fun getOrders(): Order

    @GET("orders/count.json")
    suspend fun getOrdersCountToday(
        @Query("created_at_min") minDate: String,
        @Query("created_at_max") maxDate: String,
        @Query("status") status: String = "any"
    ): OrdersCount

    @DELETE("products/{productId}.json")
    suspend fun deleteProduct(@Path("productId") productId: String)

    @GET("products/{productId}.json")
    suspend fun getProductById(@Path("productId") productId: String): ProductRequest


    @PUT("products/{productId}.json")
    suspend fun editProduct(@Path("productId") productId: String, @Body product: ProductRequest)

    @GET("locations.json")
    suspend fun getAllInventoryLocations() : Location

    @GET("inventory_levels.json?location_ids={locationId}")
    suspend fun getInventoryLevels(@Path("locationId") locationId: String) : InventoryLevel

    @POST("inventory_levels/adjust.json")
    suspend fun adjustInventory(@Body inventoryLevelRequest: InventoryLevelRequest)

    @GET("price_rules.json")
    suspend fun getPriceRules() : PriceRule

    @POST("price_rules.json")
    suspend fun createPriceRules(@Body priceRule : PriceRule)

    @PUT("price_rules/{priceRuleId}.json")
    suspend fun updatePriceRules(@Path("priceRuleId") priceRuleId : String ,@Body priceRule : PriceRule)

    @DELETE("price_rules/{priceRuleId}.json")
    suspend fun deletePriceRules(@Path("priceRuleId") priceRuleId : String)

}