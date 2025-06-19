package com.example.khizana.domain.usecase

import com.example.PriceRuleDtoTestFactory
import com.example.khizana.data.repository.FakePriceRuleRepository
import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.domain.repository.PriceRuleRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test


class DeletePriceRuleUseCaseTest{
    private lateinit var priceRuleRepository: PriceRuleRepository
    private lateinit var deletePriceRuleUseCase: DeletePriceRuleUseCase
    private var priceRuleList =
        mutableListOf(PriceRuleDtoTestFactory.createPriceRulesItemEntity().toDomain())

    @Before
    fun setup(){
        priceRuleRepository = FakePriceRuleRepository(priceRuleList)
        deletePriceRuleUseCase = DeletePriceRuleUseCase(priceRuleRepository)
    }

    @Test
    fun deletePriceRule_changePriceRulesState() = runTest {
        //Given
        //When
        deletePriceRuleUseCase.deletePriceRule(
            "default_id_123"
        )
        //Then
        assertThat(priceRuleList.size, `is`(0))
    }
}