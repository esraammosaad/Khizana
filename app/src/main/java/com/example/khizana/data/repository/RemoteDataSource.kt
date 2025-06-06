package com.example.khizana.data.repository

import com.example.khizana.data.dto.InventoryLevel
import com.example.khizana.data.dto.InventoryLevelRequest
import com.example.khizana.data.dto.Location
import com.example.khizana.data.dto.Order
import com.example.khizana.data.dto.OrdersCount
import com.example.khizana.data.dto.PriceRule
import com.example.khizana.data.dto.PriceRuleRequest
import com.example.khizana.data.dto.Product
import com.example.khizana.data.dto.ProductRequest

interface RemoteDataSource {
    suspend fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    suspend fun getProducts(): Product
    suspend fun createProduct(product: ProductRequest) : ProductRequest
    suspend fun getOrders(minDate: String, maxDate: String): Order
    suspend fun getOrdersCount(minDate: String, maxDate: String): OrdersCount
    suspend fun deleteProduct(productId: String)
    suspend fun getProductById(productId: String): ProductRequest
    suspend fun editProduct(productId: String, product: ProductRequest)
    suspend fun getAllInventoryLocations() : Location
    suspend fun getInventoryLevels(locationId : String) : InventoryLevel
    suspend fun adjustInventory(inventoryLevelRequest: InventoryLevelRequest)
    suspend fun getPriceRules() : PriceRule
    suspend fun createPriceRules(priceRuleRequest : PriceRuleRequest)
    suspend fun updatePriceRules(priceRuleId : String ,priceRuleRequest : PriceRuleRequest)
    suspend fun deletePriceRules(priceRuleId : String)

}