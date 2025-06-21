package com.example.khizana.domain.model

data class InventoryLevelDomain(
    val inventoryLevels: List<InventoryLevelsItem>
)

data class InventoryLevelsItem(
    val updatedAt: String,
    val inventoryItemId: String,
    var available: Int,
    val locationId: String
)

