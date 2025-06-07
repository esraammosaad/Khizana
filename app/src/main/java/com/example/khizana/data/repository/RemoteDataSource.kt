package com.example.khizana.data.repository

import com.example.khizana.data.dto.DiscountCode
import com.example.khizana.data.dto.DiscountCodeRequest
import com.example.khizana.data.dto.InventoryLevel
import com.example.khizana.data.dto.InventoryLevelRequest
import com.example.khizana.data.dto.Location
import com.example.khizana.data.dto.Order
import com.example.khizana.data.dto.OrdersCount
import com.example.khizana.data.dto.PriceRule
import com.example.khizana.data.dto.PriceRuleRequest
import com.example.khizana.data.dto.Product
import com.example.khizana.data.dto.ProductRequest
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    suspend fun getProducts(): Flow<Product>
    suspend fun createProduct(product: ProductRequest) : Flow<ProductRequest>
    suspend fun getOrders(minDate: String, maxDate: String): Flow<Order>
    suspend fun getOrdersCount(minDate: String, maxDate: String): Flow<OrdersCount>
    suspend fun deleteProduct(productId: String)
    suspend fun getProductById(productId: String): Flow<ProductRequest>
    suspend fun editProduct(productId: String, product: ProductRequest)
    suspend fun getAllInventoryLocations() : Flow<Location>
    suspend fun getInventoryLevels(locationId : String) : Flow<InventoryLevel>
    suspend fun adjustInventory(inventoryLevelRequest: InventoryLevelRequest)
    suspend fun getPriceRules() : Flow<PriceRule>
    suspend fun createPriceRules(priceRuleRequest : PriceRuleRequest)
    suspend fun updatePriceRules(priceRuleId : String ,priceRuleRequest : PriceRuleRequest)
    suspend fun deletePriceRules(priceRuleId : String)
    suspend fun getDiscountCodes(priceRuleId : String) : Flow<DiscountCode>
    suspend fun createDiscountCodes(priceRuleId : String ,discountCodeRequest : DiscountCodeRequest)
    suspend fun deleteDiscountCodes(priceRuleId : String ,discountCodeId : String)
    suspend fun updateDiscountCodes(priceRuleId : String ,discountCodeId : String ,discountCodeRequest : DiscountCodeRequest)
}