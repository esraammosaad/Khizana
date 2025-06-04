package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.PriceRuleRequestDomain
import com.example.khizana.domain.repository.PriceRuleRepository

class EditPriceRuleUseCase(private val priceRuleRepository: PriceRuleRepository)  {
    suspend fun editPriceRule(priceRuleId: String, priceRuleRequestDomain: PriceRuleRequestDomain) {
        priceRuleRepository.updatePriceRule(priceRuleId, priceRuleRequestDomain)
    }

}