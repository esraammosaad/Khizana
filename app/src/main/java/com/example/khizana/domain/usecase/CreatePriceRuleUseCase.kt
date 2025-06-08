package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.PriceRuleRequestDomain
import com.example.khizana.domain.repository.PriceRuleRepository
import javax.inject.Inject

class CreatePriceRuleUseCase @Inject constructor(private val priceRuleRepository: PriceRuleRepository)  {
    suspend fun createPriceRule(priceRuleRequestDomain: PriceRuleRequestDomain) {
        priceRuleRepository.createPriceRules(priceRuleRequestDomain)
    }
}