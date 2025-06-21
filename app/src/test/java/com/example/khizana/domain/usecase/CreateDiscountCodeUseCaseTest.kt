package com.example.khizana.domain.usecase

import com.example.DiscountCodeDtoTestFactory
import com.example.khizana.data.repository.FakeDiscountCodeRepository
import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.domain.model.DiscountCodeDomainRequest
import com.example.khizana.domain.model.DiscountCodeRequestDomain
import com.example.khizana.domain.repository.DiscountCodeRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test


class CreateDiscountCodeUseCaseTest {
    private lateinit var discountCodeRepository: DiscountCodeRepository
    private lateinit var createDiscountCodeUseCase: CreateDiscountCodeUseCase
    private var discountCodeList =
        mutableListOf(DiscountCodeDtoTestFactory.createDiscountCodeItemEntity().toDomain())

    @Before
    fun setup() {
        discountCodeRepository = FakeDiscountCodeRepository(
            discountCodeList = discountCodeList
        )
        createDiscountCodeUseCase = CreateDiscountCodeUseCase(discountCodeRepository)
    }

    @Test
    fun createDiscountCode_returnDiscountCode() = runTest {
        //Given
        //When
        createDiscountCodeUseCase.createDiscountCode(
            priceRuleId = "default_id_123",
            discountCodeRequest = DiscountCodeRequestDomain(
                discount_code = DiscountCodeDomainRequest(
                    code = "new code",
                )
            )
        )
        //Then
        assertThat(discountCodeList.size, `is`(2))
        assertThat(discountCodeList.last().code, `is`("new code"))
    }
}