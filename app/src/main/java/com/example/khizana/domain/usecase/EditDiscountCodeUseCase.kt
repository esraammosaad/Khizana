package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.DiscountCodeRequestDomain
import com.example.khizana.domain.repository.DiscountCodeRepository
import javax.inject.Inject

class EditDiscountCodeUseCase @Inject constructor(private val discountCodeRepository: DiscountCodeRepository)  {
    suspend fun editDiscountCode(
        priceRuleId: String,
        discountCodeId: String,
        discountCodeRequest: DiscountCodeRequestDomain
    ){
        discountCodeRepository.updateDiscountCode(priceRuleId, discountCodeId, discountCodeRequest)

    }
}