package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.InventoryItemRequestDomain
import com.example.khizana.domain.repository.InventoryRepository

class UpdateInventoryItemUseCase(private val inventoryRepository: InventoryRepository) {
    suspend fun updateInventoryItem(
        inventoryItemRequest: InventoryItemRequestDomain,
        inventoryItemId: String
    ) {
        inventoryRepository.updateInventoryItem(inventoryItemRequest, inventoryItemId)
    }
}