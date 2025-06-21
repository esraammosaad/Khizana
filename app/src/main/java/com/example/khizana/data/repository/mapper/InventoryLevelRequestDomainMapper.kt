package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.InventoryLevelRequest
import com.example.khizana.domain.model.InventoryLevelRequestDomain

fun InventoryLevelRequest.toDomain(): InventoryLevelRequestDomain {
    return InventoryLevelRequestDomain(
        inventory_item_id = inventoryItemId,
        available_adjustment = availableAdjustment,
        available = available,
        location_id = locationId
    )
}


fun InventoryLevelRequestDomain.toDto(): InventoryLevelRequest {
    return InventoryLevelRequest(
        inventoryItemId = inventory_item_id,
        availableAdjustment = available_adjustment,
        available = available,
        locationId = location_id
    )
}

