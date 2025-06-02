package com.example.khizana.domain.usecase

import com.example.khizana.domain.repository.PriceRuleRepository

class DeletePriceRuleUseCase(private val priceRuleRepository: PriceRuleRepository) {
    suspend fun deletePriceRule(priceRuleId: String) {
        priceRuleRepository.deletePriceRule(priceRuleId)
    }
}