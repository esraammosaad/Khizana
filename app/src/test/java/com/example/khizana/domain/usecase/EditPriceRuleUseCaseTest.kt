package com.example.khizana.domain.usecase

import com.example.PriceRuleDtoTestFactory
import com.example.PriceRuleRequestDtoTestFactory
import com.example.khizana.data.repository.FakePriceRuleRepository
import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.domain.model.PriceRuleDomainRequest
import com.example.khizana.domain.model.PriceRuleRequestDomain
import com.example.khizana.domain.repository.PriceRuleRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test


class EditPriceRuleUseCaseTest{

    private lateinit var priceRuleRepository: PriceRuleRepository
    private lateinit var editPriceRuleUseCase: EditPriceRuleUseCase
    private var priceRuleList =
        mutableListOf(PriceRuleDtoTestFactory.createPriceRulesItemEntity().toDomain())

    @Before
    fun setup() {
        priceRuleRepository = FakePriceRuleRepository(priceRuleList)
        editPriceRuleUseCase = EditPriceRuleUseCase(priceRuleRepository)
    }

    @Test
    fun editPriceRule_changePriceRule() = runTest {
        //Given
        //When
        editPriceRuleUseCase.editPriceRule(
            "default_id_123", PriceRuleRequestDtoTestFactory.createTestPriceRuleRequest(
                PriceRuleRequestDtoTestFactory.createTestPriceRuleEntity(
                    title = "new title",
                )
            ).toDomain()
        )
        //Then
        assertThat(priceRuleList.size, `is`(1))
        assertThat(priceRuleList.first().title, `is`("new title"))

    }

}