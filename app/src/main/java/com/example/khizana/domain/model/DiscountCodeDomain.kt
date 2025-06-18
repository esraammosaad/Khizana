package com.example.khizana.domain.model

data class DiscountCodeDomain(
    val discountCodes: List<DiscountCodesItem>
)

data class DiscountCodesItem(
    val usageCount: Int,
    val code: String,
    val updatedAt: String,
    val priceRuleId: String,
    val createdAt: String,
    val id: String
)

