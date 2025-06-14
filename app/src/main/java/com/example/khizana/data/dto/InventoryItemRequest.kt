package com.example.khizana.data.dto

data class InventoryItemRequest(
    val inventory_item: InventoryItemEntity
)

data class InventoryItemEntity(
    val cost: String?,
    val tracked: Boolean
)

