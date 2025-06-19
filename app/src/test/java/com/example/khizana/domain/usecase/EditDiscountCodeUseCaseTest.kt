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


class EditDiscountCodeUseCaseTest{
    private lateinit var discountCodeRepository: DiscountCodeRepository
    private lateinit var editDiscountCodeUseCase: EditDiscountCodeUseCase
    private var discountCodeList =
        mutableListOf(DiscountCodeDtoTestFactory.createDiscountCodeItemEntity().toDomain())

    @Before
    fun setup() {
        discountCodeRepository = FakeDiscountCodeRepository(discountCodeList)
        editDiscountCodeUseCase = EditDiscountCodeUseCase(discountCodeRepository)
    }

    @Test
    fun editDiscountCode_changeDiscountCode() = runTest {
        //Given
        //When
        editDiscountCodeUseCase.editDiscountCode(
            "price_rule_123",
            "discount_123",
            DiscountCodeRequestDomain(
                discount_code = DiscountCodeDomainRequest(
                    code = "new code",
                )
            )
        )
        //Then
        assertThat(discountCodeList.size, `is`(1))
        assertThat(discountCodeList.first().code, `is`("new code"))
    }
}