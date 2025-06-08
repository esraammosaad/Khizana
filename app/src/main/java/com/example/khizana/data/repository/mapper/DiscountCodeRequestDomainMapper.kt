package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.DiscountCodeRequest
import com.example.khizana.data.dto.DiscountCodeEntity
import com.example.khizana.domain.model.DiscountCodeRequestDomain
import com.example.khizana.domain.model.DiscountCode

fun DiscountCodeRequest.toDomain(): DiscountCodeRequestDomain {
    return DiscountCodeRequestDomain(
        discount_code = discount_code.toDomain()
    )
}

fun DiscountCodeEntity.toDomain(): DiscountCode {
    return DiscountCode(
        code = code
    )
}

fun DiscountCodeRequestDomain.toDto(): DiscountCodeRequest {
    return DiscountCodeRequest(
        discount_code = discount_code.toDto()
    )
}

fun DiscountCode.toDto(): DiscountCodeEntity {
    return DiscountCodeEntity(
        code = code
    )
}

