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


class DeleteDiscountCodeUseCaseTest{
    private lateinit var discountCodeRepository: DiscountCodeRepository
    private lateinit var deleteDiscountCodeUseCase: DeleteDiscountCodeUseCase
    private var discountCodeList =
        mutableListOf(DiscountCodeDtoTestFactory.createDiscountCodeItemEntity().toDomain())

    @Before
    fun setup() {
        discountCodeRepository = FakeDiscountCodeRepository(discountCodeList)
        deleteDiscountCodeUseCase = DeleteDiscountCodeUseCase(discountCodeRepository)
    }

    @Test
    fun deleteDiscountCode_changeDiscountCodesState() = runTest {
        //Given
        //When
        deleteDiscountCodeUseCase.deleteDiscountCode(
            "price_rule_123",
            "discount_123"
        )
        //Then
        assertThat(discountCodeList.size, `is`(0))
    }
}