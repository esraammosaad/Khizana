package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.PriceRuleDomain
import com.example.khizana.domain.repository.PriceRuleRepository

class CreatePriceRuleUseCase(private val priceRuleRepository: PriceRuleRepository)  {
    suspend fun createPriceRule(priceRule: PriceRuleDomain) {
        priceRuleRepository.createPriceRules(priceRule)
    }
}