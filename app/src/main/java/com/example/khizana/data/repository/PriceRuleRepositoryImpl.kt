package com.example.khizana.data.repository

import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.data.repository.mapper.toDto
import com.example.khizana.domain.model.PriceRuleDomain
import com.example.khizana.domain.repository.PriceRuleRepository

class PriceRuleRepositoryImpl(private val remoteDataSource: RemoteDataSource) :
    PriceRuleRepository {
    override suspend fun getPriceRules(): PriceRuleDomain {
        return remoteDataSource.getPriceRules().toDomain()
    }

    override suspend fun createPriceRules(priceRule: PriceRuleDomain) {
        remoteDataSource.createPriceRules(priceRule.toDto())
    }

    override suspend fun updatePriceRule(priceRuleId: String, priceRule: PriceRuleDomain) {
        remoteDataSource.updatePriceRules(priceRuleId, priceRule.toDto())
    }

    override suspend fun deletePriceRule(priceRuleId: String) {
        remoteDataSource.deletePriceRules(priceRuleId)
    }


}