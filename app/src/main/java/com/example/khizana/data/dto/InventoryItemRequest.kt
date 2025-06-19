package com.example.khizana.data.dto

import com.google.gson.annotations.SerializedName

data class InventoryItemRequest(
    @SerializedName("inventory_item")
    val inventoryItem: InventoryItemEntity
)

data class InventoryItemEntity(
    var cost: String?,
    var tracked: Boolean
)

