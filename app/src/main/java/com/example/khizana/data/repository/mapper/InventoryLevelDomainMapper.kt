package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.InventoryLevel
import com.example.khizana.data.dto.Inventory_levelsItemEntity
import com.example.khizana.domain.model.InventoryLevelDomain
import com.example.khizana.domain.model.Inventory_levelsItem

fun InventoryLevel.toDomain(): InventoryLevelDomain {
    return InventoryLevelDomain(
        inventory_levels = inventory_levels.map { it.toDomain() }
    )
}

fun Inventory_levelsItemEntity.toDomain(): Inventory_levelsItem {
    return Inventory_levelsItem(
        updated_at = updated_at,
        inventory_item_id = inventory_item_id,
        available = available,
        location_id = location_id
    )
}

