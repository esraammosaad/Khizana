package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.DiscountCodeRequestDomain
import com.example.khizana.domain.repository.DiscountCodeRepository
import javax.inject.Inject

class CreateDiscountCodeUseCase @Inject constructor(private val discountCodeRepository: DiscountCodeRepository) {
    suspend fun createDiscountCode(
        priceRuleId: String,
        discountCodeRequest: DiscountCodeRequestDomain
    ) {
        discountCodeRepository.createDiscountCode(priceRuleId, discountCodeRequest)
    }

}