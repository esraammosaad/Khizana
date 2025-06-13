package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.InventoryItemRequestDomain
import com.example.khizana.domain.repository.InventoryRepository
import kotlinx.coroutines.flow.Flow

class GetInventoryItemUseCase(private val inventoryRepository: InventoryRepository) {
    suspend fun getInventoryItem(inventoryItemId: String): Flow<InventoryItemRequestDomain> {
        return if (inventoryItemId.isNotEmpty()) inventoryRepository.getInventoryItem(
            inventoryItemId
        ) else throw Exception("Inventory item id is empty")
    }
}