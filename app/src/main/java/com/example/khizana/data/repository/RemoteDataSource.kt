package com.example.khizana.data.repository

import com.example.khizana.data.dto.DiscountCode
import com.example.khizana.data.dto.DiscountCodeRequest
import com.example.khizana.data.dto.InventoryLevel
import com.example.khizana.data.dto.InventoryLevelRequest
import com.example.khizana.data.dto.Location
import com.example.khizana.data.dto.Order
import com.example.khizana.data.dto.Count
import com.example.khizana.data.dto.InventoryItemRequest
import com.example.khizana.data.dto.PriceRule
import com.example.khizana.data.dto.PriceRuleRequest
import com.example.khizana.data.dto.Product
import com.example.khizana.data.dto.ProductRequest
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun login(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    suspend fun logout()
    suspend fun getProducts(): Flow<Product>
    suspend fun createProduct(product: ProductRequest) : Flow<ProductRequest>
    suspend fun getOrders(minDate: String, maxDate: String): Flow<Order>
    suspend fun getOrdersCount(minDate: String, maxDate: String): Flow<Count>
    suspend fun deleteProduct(productId: String)
    suspend fun getProductsCount(): Flow<Count>
    suspend fun getProductById(productId: String): Flow<ProductRequest>
    suspend fun editProduct(productId: String, product: ProductRequest)
    suspend fun getAllInventoryLocations() : Flow<Location>
    suspend fun setInventoryItemQuantity(inventoryLevelRequest: InventoryLevelRequest)
    suspend fun updateInventoryItem(inventoryItemRequest: InventoryItemRequest,inventoryItemId : String)
    suspend fun getInventoryItem(inventoryItemId : String) : Flow<InventoryItemRequest>
    suspend fun getPriceRules() : Flow<PriceRule>
    suspend fun createPriceRules(priceRuleRequest : PriceRuleRequest)
    suspend fun updatePriceRules(priceRuleId : String ,priceRuleRequest : PriceRuleRequest)
    suspend fun deletePriceRules(priceRuleId : String)
    suspend fun getDiscountCodes(priceRuleId : String) : Flow<DiscountCode>
    suspend fun createDiscountCodes(priceRuleId : String ,discountCodeRequest : DiscountCodeRequest)
    suspend fun deleteDiscountCodes(priceRuleId : String ,discountCodeId : String)
    suspend fun updateDiscountCodes(priceRuleId : String ,discountCodeId : String ,discountCodeRequest : DiscountCodeRequest)
}