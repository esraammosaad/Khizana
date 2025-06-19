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


class GetAllPriceRulesUseCaseTest{
    private lateinit var priceRuleRepository: PriceRuleRepository
    private lateinit var getAllPriceRulesUseCase: GetAllPriceRulesUseCase
    private var priceRuleList =
        mutableListOf(PriceRuleDtoTestFactory.createPriceRulesItemEntity().toDomain())

    @Before
    fun setup() {
        priceRuleRepository = FakePriceRuleRepository(priceRuleList)
        getAllPriceRulesUseCase = GetAllPriceRulesUseCase(priceRuleRepository)
    }

    @Test
    fun getAllPriceRules_returnPriceRules() = runTest {
        //Given
        //When
        getAllPriceRulesUseCase.getAllPriceRules()
        //Then
        assertThat(priceRuleList.size, `is`(1))
        assertThat(priceRuleList.first().title, `is`("Default Discount"))
    }
}