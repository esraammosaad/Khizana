package com.example.khizana.data.dto

import com.google.gson.annotations.SerializedName

data class DiscountCode(
    @SerializedName("discount_codes")
    val discountCodes: List<DiscountCodesItemEntity>
)

data class DiscountCodesItemEntity(
    @SerializedName("usage_count")
    val usageCount: Int,
    var code: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("price_rule_id")
    val priceRuleId: String,
    @SerializedName("created_at")
    val createdAt: String,
    val id: String
)

