package com.example.khizana.data.dto

import com.google.gson.annotations.SerializedName

data class InventoryLevel(
    @SerializedName("inventory_levels")
    val inventoryLevels: List<InventoryLevelsItemEntity>
)

data class InventoryLevelsItemEntity(
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("inventory_item_id")
    val inventoryItemId: String,
    var available: Int,
    @SerializedName("location_id")
    val locationId: String
)

