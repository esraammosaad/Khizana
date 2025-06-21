package com.example.khizana.data.dto

import com.google.gson.annotations.SerializedName

data class InventoryLevelRequest(
    @SerializedName("inventory_item_id")
    var inventoryItemId: String,
    @SerializedName("available_adjustment")
    val availableAdjustment: Int,
    val available: Int,
    @SerializedName("location_id")
    val locationId: String
)

