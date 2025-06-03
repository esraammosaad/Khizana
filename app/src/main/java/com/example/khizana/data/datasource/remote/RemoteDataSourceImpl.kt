package com.example.khizana.data.datasource.remote


import com.example.khizana.data.dto.InventoryLevel
import com.example.khizana.data.dto.InventoryLevelRequest
import com.example.khizana.data.dto.Location
import com.example.khizana.data.dto.Order
import com.example.khizana.data.dto.OrdersCount
import com.example.khizana.data.dto.PriceRule
import com.example.khizana.data.dto.Product
import com.example.khizana.data.dto.ProductRequest
import com.example.khizana.data.repository.RemoteDataSource

class RemoteDataSourceImpl(private val apiService: ApiService) : RemoteDataSource{

    override suspend fun getProducts() : Product {
        return apiService.getProducts()
    }

    override suspend fun createProduct(product: ProductRequest) : ProductRequest  {
       return apiService.createProduct(product)
    }

    override suspend fun getOrders() : Order {
        return apiService.getOrders()
    }

    override suspend fun getOrdersCount(minDate: String, maxDate: String) : OrdersCount {
        return apiService.getOrdersCountToday(minDate, maxDate)
    }

    override suspend fun deleteProduct(productId : String) {
        apiService.deleteProduct(productId)
    }

    override suspend fun getProductById(productId : String) : ProductRequest {
        return apiService.getProductById(productId)
    }

    override suspend fun editProduct(productId : String, product : ProductRequest) {
        apiService.editProduct(productId, product)
    }

    override suspend fun getAllInventoryLocations() : Location {
        return apiService.getAllInventoryLocations()
    }

    override suspend fun getInventoryLevels(locationId : String) : InventoryLevel {
        return apiService.getInventoryLevels(locationId)
    }

    override suspend fun adjustInventory(inventoryLevelRequest: InventoryLevelRequest) {
        apiService.adjustInventory(inventoryLevelRequest)
    }

    override suspend fun getPriceRules() : PriceRule {
        return apiService.getPriceRules()
    }

    override suspend fun createPriceRules(priceRule : PriceRule) {
        apiService.createPriceRules(priceRule)
    }

    override suspend fun updatePriceRules(priceRuleId : String ,priceRule : PriceRule) {
        apiService.updatePriceRules(priceRuleId, priceRule)
    }

    override suspend fun deletePriceRules(priceRuleId : String)  {
        apiService.deletePriceRules(priceRuleId)
    }
}