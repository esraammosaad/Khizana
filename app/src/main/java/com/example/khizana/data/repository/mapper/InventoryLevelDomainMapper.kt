package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.InventoryLevel
import com.example.khizana.data.dto.InventoryLevelsItemEntity
import com.example.khizana.domain.model.InventoryLevelDomain
import com.example.khizana.domain.model.Inventory_levelsItem

fun InventoryLevel.toDomain(): InventoryLevelDomain {
    return InventoryLevelDomain(
        inventory_levels = inventoryLevels.map { it.toDomain() }
    )
}

fun InventoryLevelsItemEntity.toDomain(): Inventory_levelsItem {
    return Inventory_levelsItem(
        updated_at = updatedAt,
        inventory_item_id = inventoryItemId,
        available = available,
        location_id = locationId
    )
}

