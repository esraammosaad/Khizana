package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.InventoryLevelRequest
import com.example.khizana.domain.model.InventoryLevelRequestDomain

fun InventoryLevelRequest.toDomain(): InventoryLevelRequestDomain {
    return InventoryLevelRequestDomain(
        inventory_item_id = inventory_item_id,
        available_adjustment = available_adjustment,
        available = available,
        location_id = location_id
    )
}


fun InventoryLevelRequestDomain.toDto(): InventoryLevelRequest {
    return InventoryLevelRequest(
        inventory_item_id = inventory_item_id,
        available_adjustment = available_adjustment,
        available = available,
        location_id = location_id
    )
}

