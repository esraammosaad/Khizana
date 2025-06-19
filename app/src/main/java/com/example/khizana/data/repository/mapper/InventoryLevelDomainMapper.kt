package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.InventoryLevel
import com.example.khizana.data.dto.InventoryLevelsItemEntity
import com.example.khizana.domain.model.InventoryLevelDomain
import com.example.khizana.domain.model.InventoryLevelsItem

fun InventoryLevel.toDomain(): InventoryLevelDomain {
    return InventoryLevelDomain(
        inventoryLevels = inventoryLevels.map { it.toDomain() }
    )
}

fun InventoryLevelsItemEntity.toDomain(): InventoryLevelsItem {
    return InventoryLevelsItem(
        updatedAt = updatedAt,
        inventoryItemId = inventoryItemId,
        available = available,
        locationId = locationId
    )
}

