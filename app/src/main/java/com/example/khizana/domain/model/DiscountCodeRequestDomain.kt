package com.example.khizana.domain.model

data class DiscountCodeRequestDomain(
    val discount_code: DiscountCodeDomainRequest
)

data class DiscountCodeDomainRequest(
    val code: String
)

