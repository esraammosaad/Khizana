package com.example.khizana.data.repository

import com.example.DiscountCodeDtoTestFactory
import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.domain.model.DiscountCodeDomain
import com.example.khizana.domain.model.DiscountCodeRequestDomain
import com.example.khizana.domain.model.DiscountCodesItem
import com.example.khizana.domain.repository.DiscountCodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class FakeDiscountCodeRepository(private val discountCodeList: MutableList<DiscountCodesItem>) :
    DiscountCodeRepository {
    override suspend fun getDiscountCodes(priceRuleId: String): Flow<DiscountCodeDomain> {
        return flowOf(DiscountCodeDomain(discountCodes = discountCodeList))
    }

    override suspend fun createDiscountCode(
        priceRuleId: String,
        discountCodeRequest: DiscountCodeRequestDomain
    ) {
        discountCodeList.add(
            DiscountCodeDtoTestFactory.createDiscountCodeItemEntity().toDomain().apply {
                code = discountCodeRequest.discount_code.code
            }
        )
    }

    override suspend fun deleteDiscountCode(priceRuleId: String, discountCodeId: String) {
        discountCodeList.removeIf { it.id == discountCodeId }
    }

    override suspend fun updateDiscountCode(
        priceRuleId: String,
        discountCodeId: String,
        discountCodeRequest: DiscountCodeRequestDomain
    ) {
        val index = discountCodeList.indexOfFirst { it.id == discountCodeId }
        if (index != -1) {
            discountCodeList[index] = DiscountCodeDtoTestFactory.createDiscountCodeItemEntity(
                code = discountCodeRequest.discount_code.code
            ).toDomain()
        }
    }
}