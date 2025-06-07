package com.example.khizana.domain.repository

import com.example.khizana.domain.model.DiscountCodeDomain
import com.example.khizana.domain.model.DiscountCodeRequestDomain
import kotlinx.coroutines.flow.Flow

interface DiscountCodeRepository {
    suspend fun getDiscountCodes(priceRuleId: String): Flow<DiscountCodeDomain>

    suspend fun createDiscountCode(
        priceRuleId: String,
        discountCodeRequest: DiscountCodeRequestDomain
    )

    suspend fun deleteDiscountCode(priceRuleId: String, discountCodeId: String)

    suspend fun updateDiscountCode(
        priceRuleId: String,
        discountCodeId: String,
        discountCodeRequest: DiscountCodeRequestDomain
    )

}