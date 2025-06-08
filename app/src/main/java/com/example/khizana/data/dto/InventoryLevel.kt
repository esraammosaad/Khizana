package com.example.khizana.data.dto

data class InventoryLevel(
    val inventory_levels: List<Inventory_levelsItemEntity>
)

data class Inventory_levelsItemEntity(
    val updated_at: String,
    val inventory_item_id: String,
    val available: Int,
    val location_id: String
)

