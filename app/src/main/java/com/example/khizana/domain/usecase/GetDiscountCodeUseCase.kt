package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.DiscountCodeDomain
import com.example.khizana.domain.repository.DiscountCodeRepository
import kotlinx.coroutines.flow.Flow

class GetDiscountCodeUseCase (private val discountCodeRepository: DiscountCodeRepository) {

    suspend fun getDiscountCode(priceRuleId: String): Flow<DiscountCodeDomain> {
        return discountCodeRepository.getDiscountCodes(priceRuleId)
    }

}