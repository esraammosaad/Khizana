package com.example.khizana.data.datasource.remote

import com.example.DiscountCodeDtoTestFactory
import com.example.InventoryItemDtoTestFactory
import com.example.InventoryLevelDtoTestFactory
import com.example.LocationTestFactory
import com.example.OrderDtoTestFactory
import com.example.PriceRuleDtoTestFactory
import com.example.ProductDtoTestFactory
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
import com.example.khizana.data.dto.PriceRule
import com.example.khizana.data.dto.PriceRuleRequest
import com.example.khizana.data.dto.PriceRulesItemEntity
import com.example.khizana.data.dto.Product
import com.example.khizana.data.dto.ProductRequest
import com.example.khizana.data.dto.ProductsItemEntity
import kotlinx.coroutines.flow.flowOf
import retrofit2.Response


class FakeApiService(
    private val productsList: MutableList<ProductsItemEntity>,
    private val priceRulesList: MutableList<PriceRulesItemEntity>,
    private val discountCodesList: MutableList<DiscountCodesItemEntity>,
    private val inventoryLocationsList: MutableList<LocationsItemEntity> = mutableListOf(),
    private val inventoryLevelsList: MutableList<InventoryLevelsItemEntity> = mutableListOf(),
    private val inventoryItemsList: MutableList<InventoryItemEntity> = mutableListOf(),
) : ApiService {

    override suspend fun getProducts(): Product {
        return ProductDtoTestFactory.createProductDto(productsList)
    }

    override suspend fun createProduct(product: ProductRequest): ProductRequest {
        productsList.add(
            ProductDtoTestFactory.createProductItemEntity(
                id = product.product?.id ?: "prod_123",
                title = product.product?.title ?: "Product 1",
            )
        )
        return product
    }

    override suspend fun getOrders(
        minDate: String,
        maxDate: String,
        status: String,
        financialStatus: String
    ): Order {
        return Order(
            orders = listOf(OrderDtoTestFactory.createOrderItem())
        )
    }

    override suspend fun getOrdersCountToday(
        minDate: String,
        maxDate: String,
        status: String
    ): Count {
        return Count(
            count = listOf(OrderDtoTestFactory.createOrderItem()).size
        )
    }

    override suspend fun deleteProduct(productId: String): Response<Unit> {
        productsList.removeIf { it.id == productId }
        return Response.success(Unit)
    }

    override suspend fun getProductById(productId: String): ProductRequest {
        return ProductRequest(product = productsList.find { it.id == productId })
    }

    override suspend fun getProductsCount(): Count {
        return Count(count = productsList.size)
    }

    override suspend fun editProduct(productId: String, product: ProductRequest) {
        val index = productsList.indexOfFirst { it.id == productId }
        if (index != -1) {
            productsList[index] = product.product!!
        }
    }

    override suspend fun getAllInventoryLocations(): Location {
        return Location(locations = inventoryLocationsList)
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

    override suspend fun getInventoryItem(inventoryItemId: String): InventoryItemRequest {
        return InventoryItemRequest(inventoryItem = inventoryItemsList.first())
    }

    override suspend fun getPriceRules(): PriceRule {
        return PriceRuleDtoTestFactory.createPriceRule(
            priceRules = priceRulesList
        )
    }

    override suspend fun createPriceRules(priceRuleRequest: PriceRuleRequest) {
        priceRulesList.add(
            PriceRuleDtoTestFactory.createPriceRulesItemEntity(
                value = priceRuleRequest.priceRule.value,
                title = priceRuleRequest.priceRule.title,
            )
        )
    }

    override suspend fun updatePriceRules(priceRuleId: String, priceRuleRequest: PriceRuleRequest) {
        val index = priceRulesList.indexOfFirst { it.id == priceRuleId }
        if (index != -1) {
            priceRulesList[index].title = priceRuleRequest.priceRule.title
            priceRulesList[index].value = priceRuleRequest.priceRule.value
        }
    }

    override suspend fun deletePriceRules(priceRuleId: String): Response<Unit> {
        priceRulesList.removeIf { it.id == priceRuleId }
        return Response.success(Unit)
    }

    override suspend fun getDiscountCodes(priceRuleId: String): DiscountCode {
        val discountCode = discountCodesList.find { it.priceRuleId == priceRuleId }
            ?: DiscountCodeDtoTestFactory.createDiscountCodeItemEntity()
        return DiscountCode(
            discountCodes = listOf(
                discountCode
            )
        )
    }

    override suspend fun createDiscountCodes(
        priceRuleId: String,
        discountCode: DiscountCodeRequest
    ) {
        discountCodesList.add(
            DiscountCodeDtoTestFactory.createDiscountCodeItemEntity(
                code = discountCode.discountCode.code,
            )
        )
    }

    override suspend fun deleteDiscountCodes(
        priceRuleId: String,
        discountCodeId: String
    ): Response<Unit> {
        discountCodesList.removeIf { it.id == discountCodeId }
        return Response.success(Unit)
    }

    override suspend fun updateDiscountCodes(
        priceRuleId: String,
        discountCodeId: String,
        discountCode: DiscountCodeRequest
    ) {
        val index = discountCodesList.indexOfFirst { it.id == discountCodeId }
        if (index != -1) {
            discountCodesList[index].code = discountCode.discountCode.code
        }
    }
}