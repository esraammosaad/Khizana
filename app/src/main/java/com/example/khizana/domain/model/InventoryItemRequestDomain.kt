package com.example.khizana.domain.model

data class InventoryItemRequestDomain(
    val inventoryItem: InventoryItem
)

data class InventoryItem(
    var cost: String,
    var tracked: Boolean
)

