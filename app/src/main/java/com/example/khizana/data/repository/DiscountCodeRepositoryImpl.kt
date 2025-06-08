package com.example.khizana.data.repository


import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.data.repository.mapper.toDto
import com.example.khizana.domain.model.DiscountCodeDomain
import com.example.khizana.domain.model.DiscountCodeRequestDomain
import com.example.khizana.domain.repository.DiscountCodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DiscountCodeRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) :
    DiscountCodeRepository {

    override suspend fun getDiscountCodes(priceRuleId: String): Flow<DiscountCodeDomain> {
        return remoteDataSource.getDiscountCodes(priceRuleId).map { it.toDomain() }
    }

    override suspend fun createDiscountCode(
        priceRuleId: String,
        discountCodeRequest: DiscountCodeRequestDomain
    ) {
        remoteDataSource.createDiscountCodes(priceRuleId,discountCodeRequest.toDto())
    }

    override suspend fun deleteDiscountCode(priceRuleId: String, discountCodeId: String) {
        remoteDataSource.deleteDiscountCodes(priceRuleId, discountCodeId)
    }

    override suspend fun updateDiscountCode(
        priceRuleId: String,
        discountCodeId: String,
        discountCodeRequest: DiscountCodeRequestDomain
    ) {
        remoteDataSource.updateDiscountCodes(
            priceRuleId,
            discountCodeId,
            discountCodeRequest.toDto()
        )

    }
}