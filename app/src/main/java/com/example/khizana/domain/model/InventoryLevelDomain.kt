package com.example.khizana.domain.model

data class InventoryLevelDomain(
    val inventory_levels: List<Inventory_levelsItem>
)

data class Inventory_levelsItem(
    val updated_at: String,
    val inventory_item_id: String,
    val available: Int,
    val location_id: String
)

