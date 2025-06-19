package com.example.khizana.domain.model

data class PriceRuleRequestDomain(
    val priceRule: PriceRuleDomainRequest
)

data class PriceRuleDomainRequest(
    val valueType: String,
    val startsAt: String,
    val targetType: String,
    val title: String,
    val endsAt: String,
    val value: String,
)

