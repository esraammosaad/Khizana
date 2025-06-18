package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.DiscountCodeRequest
import com.example.khizana.data.dto.DiscountCodeEntity
import com.example.khizana.domain.model.DiscountCodeRequestDomain
import com.example.khizana.domain.model.DiscountCodeDomainRequest

fun DiscountCodeRequest.toDomain(): DiscountCodeRequestDomain {
    return DiscountCodeRequestDomain(
        discount_code = discountCode.toDomain()
    )
}

fun DiscountCodeEntity.toDomain(): DiscountCodeDomainRequest {
    return DiscountCodeDomainRequest(
        code = code
    )
}

fun DiscountCodeRequestDomain.toDto(): DiscountCodeRequest {
    return DiscountCodeRequest(
        discountCode = discount_code.toDto()
    )
}

fun DiscountCodeDomainRequest.toDto(): DiscountCodeEntity {
    return DiscountCodeEntity(
        code = code
    )
}

