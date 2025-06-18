package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.PriceRuleRequest
import com.example.khizana.data.dto.PriceRuleEntity
import com.example.khizana.domain.model.PriceRuleRequestDomain
import com.example.khizana.domain.model.PriceRuleDomainRequest

fun PriceRuleRequest.toDomain(): PriceRuleRequestDomain {
    return PriceRuleRequestDomain(
        priceRule = priceRule.toDomain()
    )
}

fun PriceRuleEntity.toDomain(): PriceRuleDomainRequest {
    return PriceRuleDomainRequest(
        valueType = valueType,
        startsAt = startsAt,
        targetType = targetType,
        title = title,
        endsAt = endsAt,
        value = value,
    )
}

fun PriceRuleRequestDomain.toDto(): PriceRuleRequest {
    return PriceRuleRequest(
        priceRule = priceRule.toDto()
    )
}

fun PriceRuleDomainRequest.toDto(): PriceRuleEntity {
    return PriceRuleEntity(
        valueType = valueType,
        startsAt = startsAt,
        targetType = targetType,
        title = title,
        endsAt = endsAt,
        value = value,
    )
}

