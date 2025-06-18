package com.example.khizana.data.repository

import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.data.repository.mapper.toDto
import com.example.khizana.domain.model.InventoryItemRequestDomain
import com.example.khizana.domain.model.InventoryLevelDomain
import com.example.khizana.domain.model.InventoryLevelRequestDomain
import com.example.khizana.domain.model.LocationDomain
import com.example.khizana.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class InventoryRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    InventoryRepository {
    override suspend fun setInventoryItemQuantity(inventoryLevelRequest: InventoryLevelRequestDomain) {
        remoteDataSource.setInventoryItemQuantity(inventoryLevelRequest.toDto())
    }

    override suspend fun getAllInventoryLocations(): Flow<LocationDomain> {
        return remoteDataSource.getAllInventoryLocations().map { it.toDomain() }
    }

    override suspend fun updateInventoryItem(
        inventoryItemRequest: InventoryItemRequestDomain,
        inventoryItemId: String
    ) {
        remoteDataSource.updateInventoryItem(inventoryItemRequest.toDto(), inventoryItemId)
    }

    override suspend fun getInventoryItem(inventoryItemId: String): Flow<InventoryItemRequestDomain> {
        return remoteDataSource.getInventoryItem(inventoryItemId).map {  it.toDomain()}
    }
}