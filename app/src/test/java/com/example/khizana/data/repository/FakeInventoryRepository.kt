package com.example.khizana.data.repository

import com.example.khizana.domain.model.InventoryItem
import com.example.khizana.domain.model.InventoryItemRequestDomain
import com.example.khizana.domain.model.InventoryLevelRequestDomain
import com.example.khizana.domain.model.InventoryLevelsItem
import com.example.khizana.domain.model.LocationDomain
import com.example.khizana.domain.model.LocationsItem
import com.example.khizana.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class FakeInventoryRepository(
    private var inventoryItemsList: MutableList<InventoryItem> = mutableListOf(),
    private var inventoryLocationList: MutableList<LocationsItem> = mutableListOf() ,
    private val inventoryLevelsList: MutableList<InventoryLevelsItem> = mutableListOf()
) : InventoryRepository {

    override suspend fun setInventoryItemQuantity(inventoryLevelRequest: InventoryLevelRequestDomain) {
        inventoryLevelsList.first().available = inventoryLevelRequest.available
    }

    override suspend fun getAllInventoryLocations(): Flow<LocationDomain> {
        return flowOf( LocationDomain(locations = inventoryLocationList))

    }

    override suspend fun updateInventoryItem(
        inventoryItemRequest: InventoryItemRequestDomain,
        inventoryItemId: String
    ) {
        inventoryItemsList.first().cost = inventoryItemRequest.inventoryItem.cost
        inventoryItemsList.first().tracked = inventoryItemRequest.inventoryItem.tracked
    }

    override suspend fun getInventoryItem(inventoryItemId: String): Flow<InventoryItemRequestDomain> {
        return flowOf( InventoryItemRequestDomain(inventoryItem = inventoryItemsList.first()))

    }

}