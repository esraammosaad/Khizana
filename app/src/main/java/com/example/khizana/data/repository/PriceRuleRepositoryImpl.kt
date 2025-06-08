package com.example.khizana.data.repository

import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.data.repository.mapper.toDto
import com.example.khizana.domain.model.PriceRuleDomain
import com.example.khizana.domain.model.PriceRuleRequestDomain
import com.example.khizana.domain.repository.PriceRuleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PriceRuleRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    PriceRuleRepository {
    override suspend fun getPriceRules(): Flow<PriceRuleDomain> {
        return remoteDataSource.getPriceRules().map { it.toDomain() }
    }

    override suspend fun createPriceRules(priceRuleRequestDomain: PriceRuleRequestDomain) {
        remoteDataSource.createPriceRules(priceRuleRequestDomain.toDto())
    }

    override suspend fun updatePriceRule(priceRuleId: String, priceRuleRequestDomain: PriceRuleRequestDomain) {
        remoteDataSource.updatePriceRules(priceRuleId, priceRuleRequestDomain.toDto())
    }

    override suspend fun deletePriceRule(priceRuleId: String) {
        remoteDataSource.deletePriceRules(priceRuleId)
    }


}