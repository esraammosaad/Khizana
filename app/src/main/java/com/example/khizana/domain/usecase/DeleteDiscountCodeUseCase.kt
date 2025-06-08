package com.example.khizana.domain.usecase

import com.example.khizana.domain.repository.DiscountCodeRepository
import javax.inject.Inject

class DeleteDiscountCodeUseCase @Inject constructor(private val discountCodeRepository: DiscountCodeRepository) {
    suspend fun deleteDiscountCode(priceRuleId: String, discountCodeId: String) {
        discountCodeRepository.deleteDiscountCode(priceRuleId, discountCodeId)
    }
}