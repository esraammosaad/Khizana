package com.example.khizana.domain.repository

import com.example.khizana.domain.model.InventoryLevelDomain
import com.example.khizana.domain.model.InventoryLevelRequestDomain
import com.example.khizana.domain.model.LocationDomain

interface InventoryRepository {

    suspend fun getInventoryLevels(locationId : String) : InventoryLevelDomain
    suspend fun adjustInventory(inventoryLevelRequest: InventoryLevelRequestDomain)
    suspend fun getAllInventoryLocations() : LocationDomain


}