package com.example.khizana.data.repository

import com.example.PriceRuleDtoTestFactory
import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.domain.model.PriceRuleDomain
import com.example.khizana.domain.model.PriceRuleItem
import com.example.khizana.domain.model.PriceRuleRequestDomain
import com.example.khizana.domain.repository.PriceRuleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class FakePriceRuleRepository(private val priceRuleList: MutableList<PriceRuleItem>) :
    PriceRuleRepository {
    override suspend fun getPriceRules(): Flow<PriceRuleDomain> {
        return flowOf(PriceRuleDomain(
            priceRules = priceRuleList
        ))
    }

    override suspend fun createPriceRules(priceRuleRequestDomain: PriceRuleRequestDomain) {
        priceRuleList.add(
           PriceRuleDtoTestFactory.createPriceRulesItemEntity().toDomain().apply {
               title = priceRuleRequestDomain.priceRule.title
               value = priceRuleRequestDomain.priceRule.value
           }
        )
    }

    override suspend fun updatePriceRule(
        priceRuleId: String,
        priceRuleRequestDomain: PriceRuleRequestDomain
    ) {
        val index = priceRuleList.indexOfFirst { it.id == priceRuleId }
        if (index != -1) {
            priceRuleList[index] = PriceRuleDtoTestFactory.createPriceRulesItemEntity(
                title = priceRuleRequestDomain.priceRule.title,
                value = priceRuleRequestDomain.priceRule.value
            ).toDomain()
        }
    }

    override suspend fun deletePriceRule(priceRuleId: String) {
        priceRuleList.removeIf { it.id == priceRuleId }
    }

}