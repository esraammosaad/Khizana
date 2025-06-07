package com.example.khizana.data.dto

data class DiscountCode(
    val discount_codes: List<Discount_codesItemEntity>
)

data class Discount_codesItemEntity(
    val usage_count: Int,
    val code: String,
    val updated_at: String,
    val price_rule_id: String,
    val created_at: String,
    val id: String
)

