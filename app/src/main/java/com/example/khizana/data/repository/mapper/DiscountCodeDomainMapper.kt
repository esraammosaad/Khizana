package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.DiscountCode
import com.example.khizana.data.dto.Discount_codesItemEntity
import com.example.khizana.domain.model.DiscountCodeDomain
import com.example.khizana.domain.model.Discount_codesItem

fun DiscountCode.toDomain(): DiscountCodeDomain {
    return DiscountCodeDomain(
        discount_codes = discount_codes.map { it.toDomain() }
    )
}

fun Discount_codesItemEntity.toDomain(): Discount_codesItem {
    return Discount_codesItem(
        usage_count = usage_count,
        code = code,
        updated_at = updated_at,
        price_rule_id = price_rule_id,
        created_at = created_at,
        id = id
    )
}

