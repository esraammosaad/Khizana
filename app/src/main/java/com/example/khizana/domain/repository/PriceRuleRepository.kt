package com.example.khizana.domain.repository

import com.example.khizana.domain.model.PriceRuleDomain
import com.example.khizana.domain.model.PriceRuleRequestDomain

interface PriceRuleRepository {

    suspend fun getPriceRules(): PriceRuleDomain
    suspend fun createPriceRules(priceRuleRequestDomain: PriceRuleRequestDomain)
    suspend fun updatePriceRule(priceRuleId: String, priceRuleRequestDomain: PriceRuleRequestDomain)
    suspend fun deletePriceRule(priceRuleId: String)

}