package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.PriceRuleDomain
import com.example.khizana.domain.repository.PriceRuleRepository
import kotlinx.coroutines.flow.Flow

class GetAllPriceRulesUseCase(private val priceRuleRepository: PriceRuleRepository) {

    suspend fun getAllPriceRules(): Flow<PriceRuleDomain> {
        return priceRuleRepository.getPriceRules()
    }

}