package com.example.khizana.domain.usecase

import com.example.khizana.domain.repository.PriceRuleRepository
import javax.inject.Inject

class DeletePriceRuleUseCase @Inject constructor(private val priceRuleRepository: PriceRuleRepository) {
    suspend fun deletePriceRule(priceRuleId: String) {
        priceRuleRepository.deletePriceRule(priceRuleId)
    }
}