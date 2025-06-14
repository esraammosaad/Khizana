package com.example.khizana.domain.model

data class InventoryItemRequestDomain(
    val inventory_item: InventoryItem
)

data class InventoryItem(
    val cost: String,
    val tracked: Boolean
)

