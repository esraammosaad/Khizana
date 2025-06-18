package com.example.khizana.domain.model

data class InventoryItemRequestDomain(
    val inventoryItem: InventoryItem
)

data class InventoryItem(
    val cost: String,
    val tracked: Boolean
)

