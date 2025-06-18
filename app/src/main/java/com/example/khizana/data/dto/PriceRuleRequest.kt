package com.example.khizana.data.dto

import com.google.gson.annotations.SerializedName

data class PriceRuleRequest(
    @SerializedName("price_rule")
    val priceRule: PriceRuleEntity
)

data class PriceRuleEntity(
    @SerializedName("value_type")
    val valueType: String,
    @SerializedName("starts_at")
    val startsAt: String,
    @SerializedName("target_type")
    val targetType: String = "line_item",
    @SerializedName("target_selection")
    val targetSelection: String = "all",
    @SerializedName("allocation_method")
    val allocationMethod: String = "across",
    @SerializedName("customer_selection")
    val customerSelection: String = "all",
    val title: String,
    @SerializedName("ends_at")
    val endsAt: String,
    val value: String,
)

