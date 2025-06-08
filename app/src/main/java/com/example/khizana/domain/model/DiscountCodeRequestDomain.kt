package com.example.khizana.domain.model

data class DiscountCodeRequestDomain(
    val discount_code: DiscountCode
)

data class DiscountCode(
    val code: String
)

