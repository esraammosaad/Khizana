package com.example.khizana.data.dto

data class InventoryLevelRequest(
    val inventory_item_id: String,
    val available_adjustment: Int,
    val available: Int,
    val location_id: String
)

