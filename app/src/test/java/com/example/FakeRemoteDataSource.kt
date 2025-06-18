package com.example

import com.example.khizana.data.dto.Count
import com.example.khizana.data.dto.DiscountCode
import com.example.khizana.data.dto.DiscountCodeRequest
import com.example.khizana.data.dto.DiscountCodesItemEntity
import com.example.khizana.data.dto.InventoryItemEntity
import com.example.khizana.data.dto.InventoryItemRequest
import com.example.khizana.data.dto.InventoryLevelRequest
import com.example.khizana.data.dto.InventoryLevelsItemEntity
import com.example.khizana.data.dto.Location
import com.example.khizana.data.dto.LocationsItemEntity
import com.example.khizana.data.dto.Order
import com.example.khizana.data.dto.OrdersItemEntity
import com.example.khizana.data.dto.PriceRule
import com.example.khizana.data.dto.PriceRuleRequest
import com.example.khizana.data.dto.PriceRulesItemEntity
import com.example.khizana.data.dto.Product
import com.example.khizana.data.dto.ProductRequest
import com.example.khizana.data.dto.ProductsItemEntity
import com.example.khizana.data.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRemoteDataSource(
    private val productsList: MutableList<ProductsItemEntity> = mutableListOf(),
    private val priceRulesList: MutableList<PriceRulesItemEntity> = mutableListOf(),
    private val discountCodesList: MutableList<DiscountCodesItemEntity> = mutableListOf(),
    private val ordersList: MutableList<OrdersItemEntity> = mutableListOf(),
    private val inventoryLocationsList: MutableList<LocationsItemEntity> = mutableListOf(),
    private val inventoryLevelsList: MutableList<InventoryLevelsItemEntity> = mutableListOf(),
    private val inventoryItemsList: MutableList<InventoryItemEntity> = mutableListOf(),

    ) : RemoteDataSource {
    override suspend fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {

    }

    override suspend fun logout() {

    }

    override suspend fun getProducts(): Flow<Product> {
        return flowOf(ProductDtoTestFactory.createProductDto(productsList))
    }

    override suspend fun createProduct(product: ProductRequest): Flow<ProductRequest> {
        productsList.add(
            ProductDtoTestFactory.createProductItemEntity(
                id = product.product?.id ?: "prod_123",
                title = product.product?.title ?: "Product 1",
            )
        )
        return flowOf(product)
    }

    override suspend fun getOrders(minDate: String, maxDate: String): Flow<Order> {
        return flowOf(
            Order(
                orders = ordersList
            )
        )
    }

    override suspend fun getOrdersCount(minDate: String, maxDate: String): Flow<Count> {
        return flowOf(Count(count = ordersList.size))
    }

    override suspend fun deleteProduct(productId: String) {
        productsList.removeIf { it.id == productId }
    }

    override suspend fun getProductsCount(): Flow<Count> {
        return flowOf(Count(count = productsList.size))
    }

    override suspend fun getProductById(productId: String): Flow<ProductRequest> {
        return flowOf(ProductRequest(product = productsList.find { it.id == productId }))
    }

    override suspend fun editProduct(productId: String, product: ProductRequest) {
        val index = productsList.indexOfFirst { it.id == productId }
        if (index != -1) {
            productsList[index] = product.product!!
        }
    }

    override suspend fun getAllInventoryLocations(): Flow<Location> {
        return flowOf(Location(locations = inventoryLocationsList))
    }

    override suspend fun setInventoryItemQuantity(inventoryLevelRequest: InventoryLevelRequest) {

        inventoryLevelsList.first().available = inventoryLevelRequest.available

    }

    override suspend fun updateInventoryItem(
        inventoryItemRequest: InventoryItemRequest,
        inventoryItemId: String
    ) {

        inventoryItemsList.first().cost = inventoryItemRequest.inventoryItem.cost
        inventoryItemsList.first().tracked = inventoryItemRequest.inventoryItem.tracked

    }

    override suspend fun getInventoryItem(inventoryItemId: String): Flow<InventoryItemRequest> {
        return flowOf(InventoryItemRequest(inventoryItem = inventoryItemsList.first()))
    }

    override suspend fun getPriceRules(): Flow<PriceRule> {
        return flowOf(PriceRule(priceRules = priceRulesList))
    }

    override suspend fun createPriceRules(priceRuleRequest: PriceRuleRequest) {

        priceRulesList.add(
            PriceRuleDtoTestFactory.createPriceRulesItemEntity(
                title = priceRuleRequest.priceRule.title,
                value = priceRuleRequest.priceRule.value
            )
        )
    }

    override suspend fun updatePriceRules(priceRuleId: String, priceRuleRequest: PriceRuleRequest) {

        val index = priceRulesList.indexOfFirst { it.id == priceRuleId }
        if (index != -1) {
            priceRulesList[index] = PriceRuleDtoTestFactory.createPriceRulesItemEntity(
                title = priceRuleRequest.priceRule.title,
                value = priceRuleRequest.priceRule.value
            )
        }

    }

    override suspend fun deletePriceRules(priceRuleId: String) {
        priceRulesList.removeIf { it.id == priceRuleId }
    }

    override suspend fun getDiscountCodes(priceRuleId: String): Flow<DiscountCode> {
        return flowOf(DiscountCode(discountCodes = discountCodesList))
    }

    override suspend fun createDiscountCodes(
        priceRuleId: String,
        discountCodeRequest: DiscountCodeRequest
    ) {
        discountCodesList.add(
            DiscountCodeDtoTestFactory.createDiscountCodeItemEntity(
                code = discountCodeRequest.discountCode.code
            )
        )
    }

    override suspend fun deleteDiscountCodes(priceRuleId: String, discountCodeId: String) {
        discountCodesList.removeIf { it.id == discountCodeId }
    }

    override suspend fun updateDiscountCodes(
        priceRuleId: String,
        discountCodeId: String,
        discountCodeRequest: DiscountCodeRequest
    ) {
        val index = discountCodesList.indexOfFirst { it.id == discountCodeId }
        if (index != -1) {
            discountCodesList[index] = DiscountCodeDtoTestFactory.createDiscountCodeItemEntity(
                code = discountCodeRequest.discountCode.code
            )
        }
    }
}

