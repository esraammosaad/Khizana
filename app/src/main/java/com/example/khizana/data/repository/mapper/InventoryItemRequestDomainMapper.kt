package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.InventoryItemRequest
import com.example.khizana.data.dto.InventoryItemEntity
import com.example.khizana.domain.model.InventoryItemRequestDomain
import com.example.khizana.domain.model.InventoryItem

fun InventoryItemRequest.toDomain(): InventoryItemRequestDomain {
    return InventoryItemRequestDomain(
        inventoryItem = inventoryItem.toDomain()
    )
}

fun InventoryItemEntity.toDomain(): InventoryItem {
    return InventoryItem(
        cost = cost ?: "null",
        tracked = tracked
    )
}

fun InventoryItemRequestDomain.toDto(): InventoryItemRequest {
    return InventoryItemRequest(
        inventoryItem = inventoryItem.toDto()
    )
}

fun InventoryItem.toDto(): InventoryItemEntity {
    return InventoryItemEntity(
        cost = cost,
        tracked = tracked
    )
}

