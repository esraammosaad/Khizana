package com.example.khizana.data.datasource.remote


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
import com.example.khizana.data.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val auth: AuthService
) :
    RemoteDataSource {

    override suspend fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.login(email, password, onSuccess, onFailure)
    }


    override suspend fun getProducts(): Flow<Product> {
        return flowOf(apiService.getProducts())
    }

    override suspend fun createProduct(product: ProductRequest): Flow<ProductRequest> {
        return flowOf(apiService.createProduct(product))
    }

    override suspend fun getOrders(minDate: String, maxDate: String): Flow<Order> {
        return flowOf(apiService.getOrders(minDate, maxDate))
    }

    override suspend fun getOrdersCount(minDate: String, maxDate: String): Flow<OrdersCount> {
        return flowOf(apiService.getOrdersCountToday(minDate, maxDate))
    }

    override suspend fun deleteProduct(productId: String) {
        apiService.deleteProduct(productId)
    }

    override suspend fun getProductById(productId: String): Flow<ProductRequest> {
        return flowOf(apiService.getProductById(productId))
    }

    override suspend fun editProduct(productId: String, product: ProductRequest) {
        apiService.editProduct(productId, product)
    }

    override suspend fun getAllInventoryLocations(): Flow<Location> {
        return flowOf(apiService.getAllInventoryLocations())
    }

    override suspend fun getInventoryLevels(locationId: String): Flow<InventoryLevel> {
        return flowOf(apiService.getInventoryLevels(locationId))
    }

    override suspend fun adjustInventory(inventoryLevelRequest: InventoryLevelRequest) {
        apiService.adjustInventory(inventoryLevelRequest)
    }

    override suspend fun getPriceRules(): Flow<PriceRule> {
        return flowOf(apiService.getPriceRules())
    }

    override suspend fun createPriceRules(priceRuleRequest: PriceRuleRequest) {
        apiService.createPriceRules(priceRuleRequest)
    }

    override suspend fun updatePriceRules(priceRuleId: String, priceRuleRequest: PriceRuleRequest) {
        apiService.updatePriceRules(priceRuleId, priceRuleRequest)
    }

    override suspend fun deletePriceRules(priceRuleId: String) {
        apiService.deletePriceRules(priceRuleId)
    }

    override suspend fun getDiscountCodes(priceRuleId: String): Flow<DiscountCode> {
        return flowOf(apiService.getDiscountCodes(priceRuleId))
    }

    override suspend fun createDiscountCodes(priceRuleId: String,discountCodeRequest: DiscountCodeRequest) {
        apiService.createDiscountCodes(priceRuleId,discountCodeRequest)
    }

    override suspend fun deleteDiscountCodes(priceRuleId: String, discountCodeId: String) {
        apiService.deleteDiscountCodes(priceRuleId, discountCodeId)
    }

    override suspend fun updateDiscountCodes(
        priceRuleId: String,
        discountCodeId: String,
        discountCodeRequest: DiscountCodeRequest
    ) {
        apiService.updateDiscountCodes(priceRuleId, discountCodeId, discountCodeRequest)
    }
}