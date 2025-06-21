package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.DiscountCode
import com.example.khizana.data.dto.DiscountCodesItemEntity
import com.example.khizana.domain.model.DiscountCodeDomain
import com.example.khizana.domain.model.DiscountCodesItem

fun DiscountCode.toDomain(): DiscountCodeDomain {
    return DiscountCodeDomain(
        discountCodes = discountCodes.map { it.toDomain() }
    )
}

fun DiscountCodesItemEntity.toDomain(): DiscountCodesItem {
    return DiscountCodesItem(
        usageCount = usageCount,
        code = code,
        updatedAt = updatedAt,
        priceRuleId = priceRuleId,
        createdAt = createdAt,
        id = id
    )
}

