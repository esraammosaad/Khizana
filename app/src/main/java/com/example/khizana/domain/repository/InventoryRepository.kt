package com.example.khizana.domain.repository

import com.example.khizana.domain.model.InventoryItemRequestDomain
import com.example.khizana.domain.model.InventoryLevelDomain
import com.example.khizana.domain.model.InventoryLevelRequestDomain
import com.example.khizana.domain.model.LocationDomain
import kotlinx.coroutines.flow.Flow

interface InventoryRepository {
    suspend fun getInventoryLevels(locationId : String) : Flow<InventoryLevelDomain>
    suspend fun adjustInventoryItemQuantity(inventoryLevelRequest: InventoryLevelRequestDomain)
    suspend fun setInventoryItemQuantity(inventoryLevelRequest: InventoryLevelRequestDomain)
    suspend fun getAllInventoryLocations() : Flow<LocationDomain>
    suspend fun updateInventoryItem(inventoryItemRequest: InventoryItemRequestDomain, inventoryItemId : String)
    suspend fun getInventoryItem(inventoryItemId : String) : Flow<InventoryItemRequestDomain>
}