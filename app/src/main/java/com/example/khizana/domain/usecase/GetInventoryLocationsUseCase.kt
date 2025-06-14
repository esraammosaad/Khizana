package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.LocationDomain
import com.example.khizana.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow

class GetInventoryLocationsUseCase (private val inventoryRepository: InventoryRepository) {
    suspend fun getAllInventoryLocations(): Flow<LocationDomain> {
        return inventoryRepository.getAllInventoryLocations()
    }
}