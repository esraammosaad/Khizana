package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.InventoryLevelRequestDomain
import com.example.khizana.domain.repository.InventoryRepository

class SetInventoryItemQuantityUseCase(private val inventoryRepository: InventoryRepository) {
    suspend fun setInventoryItemQuantity(inventoryLevelRequest: InventoryLevelRequestDomain) {
        inventoryRepository.setInventoryItemQuantity(inventoryLevelRequest)
    }
}