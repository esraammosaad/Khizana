package com.example.khizana.domain.repository

import com.example.khizana.domain.model.PriceRuleDomain

interface PriceRuleRepository {

    suspend fun getPriceRules(): PriceRuleDomain
    suspend fun createPriceRules(priceRule: PriceRuleDomain)
    suspend fun updatePriceRule(priceRuleId: String, priceRule: PriceRuleDomain)
    suspend fun deletePriceRule(priceRuleId: String)

}