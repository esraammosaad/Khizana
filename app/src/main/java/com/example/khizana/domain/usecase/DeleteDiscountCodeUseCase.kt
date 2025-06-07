package com.example.khizana.domain.usecase

import com.example.khizana.domain.repository.DiscountCodeRepository

class DeleteDiscountCodeUseCase(private val discountCodeRepository: DiscountCodeRepository) {
    suspend fun deleteDiscountCode(priceRuleId: String, discountCodeId: String) {
        discountCodeRepository.deleteDiscountCode(priceRuleId, discountCodeId)
    }
}