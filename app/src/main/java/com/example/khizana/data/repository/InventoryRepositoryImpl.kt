package com.example.khizana.data.repository

import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.data.repository.mapper.toDto
import com.example.khizana.domain.model.InventoryLevelDomain
import com.example.khizana.domain.model.InventoryLevelRequestDomain
import com.example.khizana.domain.model.LocationDomain
import com.example.khizana.domain.repository.InventoryRepository

class InventoryRepositoryImpl(private val remoteDataSource: RemoteDataSource) : InventoryRepository {
    override suspend fun getInventoryLevels(locationId: String): InventoryLevelDomain {
        return remoteDataSource.getInventoryLevels(locationId).toDomain()
    }

    override suspend fun adjustInventory(inventoryLevelRequest: InventoryLevelRequestDomain) {
        remoteDataSource.adjustInventory(inventoryLevelRequest.toDto())
    }

    override suspend fun getAllInventoryLocations(): LocationDomain {
        return remoteDataSource.getAllInventoryLocations().toDomain()
    }
}