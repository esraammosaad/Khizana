package com.example.khizana.domain.model

data class DiscountCodeDomain(
    val discount_codes: List<Discount_codesItem>
)

data class Discount_codesItem(
    val usage_count: Int,
    val code: String,
    val updated_at: String,
    val price_rule_id: String,
    val created_at: String,
    val id: String
)

