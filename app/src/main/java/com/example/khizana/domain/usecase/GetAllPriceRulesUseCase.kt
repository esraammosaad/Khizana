package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.PriceRuleDomain
import com.example.khizana.domain.repository.PriceRuleRepository

class GetAllPriceRulesUseCase(private val priceRuleRepository: PriceRuleRepository) {

    suspend fun getAllPriceRules(): PriceRuleDomain {
        return priceRuleRepository.getPriceRules()
    }

}