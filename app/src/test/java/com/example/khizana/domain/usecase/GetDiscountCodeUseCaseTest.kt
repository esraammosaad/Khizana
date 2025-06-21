package com.example.khizana.domain.usecase

import com.example.DiscountCodeDtoTestFactory
import com.example.khizana.data.repository.FakeDiscountCodeRepository
import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.domain.repository.DiscountCodeRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test


class GetDiscountCodeUseCaseTest {
    private lateinit var discountCodeRepository: DiscountCodeRepository
    private lateinit var getDiscountCodeUseCase: GetDiscountCodeUseCase
    private var discountCodeList =
        mutableListOf(DiscountCodeDtoTestFactory.createDiscountCodeItemEntity().toDomain())

    @Before
    fun setup() {
        discountCodeRepository = FakeDiscountCodeRepository(
            discountCodeList = discountCodeList
        )
        getDiscountCodeUseCase = GetDiscountCodeUseCase(discountCodeRepository)
    }

    @Test
    fun getDiscountCode_returnDiscountCode() = runTest {
        //Given
        //When
        getDiscountCodeUseCase.getDiscountCode(
            priceRuleId = "default_id_123",
        )
        //Then
        assertThat(discountCodeList.size, `is`(1))
        assertThat(discountCodeList.first().code, `is`("DISCOUNT20"))
    }
}