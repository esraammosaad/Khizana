package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.PriceRuleDomain
import com.example.khizana.domain.repository.PriceRuleRepository

class EditPriceRuleUseCase(private val priceRuleRepository: PriceRuleRepository)  {
    suspend fun editPriceRule(priceRuleId: String, priceRule: PriceRuleDomain) {
        priceRuleRepository.updatePriceRule(priceRuleId, priceRule)
    }

}