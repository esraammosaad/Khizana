package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.DiscountCodeDomain
import com.example.khizana.domain.repository.DiscountCodeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDiscountCodeUseCase @Inject constructor(private val discountCodeRepository: DiscountCodeRepository) {

    suspend fun getDiscountCode(priceRuleId: String): Flow<DiscountCodeDomain> {
        return discountCodeRepository.getDiscountCodes(priceRuleId)
    }

}