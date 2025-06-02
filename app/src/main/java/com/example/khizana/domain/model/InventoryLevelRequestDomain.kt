package com.example.khizana.domain.model

data class InventoryLevelRequestDomain(
    val inventory_item_id: String,
    val available_adjustment: Int,
    val available: Int,
    val location_id: String
)

