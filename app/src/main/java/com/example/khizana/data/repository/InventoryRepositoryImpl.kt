package com.example.khizana.data.repository

import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.data.repository.mapper.toDto
import com.example.khizana.domain.model.InventoryLevelDomain
import com.example.khizana.domain.model.InventoryLevelRequestDomain
import com.example.khizana.domain.model.LocationDomain
import com.example.khizana.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InventoryRepositoryImpl(private val remoteDataSource: RemoteDataSource) : InventoryRepository {
    override suspend fun getInventoryLevels(locationId: String): Flow<InventoryLevelDomain> {
        return remoteDataSource.getInventoryLevels(locationId).map { it.toDomain() }
    }

    override suspend fun adjustInventory(inventoryLevelRequest: InventoryLevelRequestDomain) {
        remoteDataSource.adjustInventory(inventoryLevelRequest.toDto())
    }

    override suspend fun getAllInventoryLocations(): Flow<LocationDomain> {
        return remoteDataSource.getAllInventoryLocations().map { it.toDomain() }
    }
}