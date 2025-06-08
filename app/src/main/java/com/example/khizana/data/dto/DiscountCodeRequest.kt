package com.example.khizana.data.dto

data class DiscountCodeRequest(
    val discount_code: DiscountCodeEntity
)

data class DiscountCodeEntity(
    val code: String
)

