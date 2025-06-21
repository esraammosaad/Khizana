package com.example.khizana.domain.usecase

import com.example.PriceRuleDtoTestFactory
import com.example.PriceRuleRequestTestFactory
import com.example.khizana.data.repository.FakePriceRuleRepository
import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.domain.model.PriceRuleRequestDomain
import com.example.khizana.domain.repository.PriceRuleRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test


class CreatePriceRuleUseCaseTest {
    private lateinit var priceRuleRepository: PriceRuleRepository
    private lateinit var createPriceRuleUseCase: CreatePriceRuleUseCase
    private var priceRuleList =
        mutableListOf(PriceRuleDtoTestFactory.createPriceRulesItemEntity().toDomain())

    @Before
    fun setup() {
        priceRuleRepository = FakePriceRuleRepository(
            priceRuleList = priceRuleList
        )
        createPriceRuleUseCase = CreatePriceRuleUseCase(priceRuleRepository)
    }

    @Test
    fun createPriceRule_returnPriceRule() = runTest {
        //Given
        //When
        createPriceRuleUseCase.createPriceRule(
            PriceRuleRequestDomain(
                priceRule = PriceRuleRequestTestFactory.createPriceRule(
                    title = "new price rule"
                )
            )
        )
        //Then
        assertThat(priceRuleList.size, `is`(2))
        assertThat(priceRuleList.last().title, `is`("new price rule"))
    }
}