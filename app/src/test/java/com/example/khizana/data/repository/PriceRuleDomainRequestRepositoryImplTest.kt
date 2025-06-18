package com.example.khizana.data.repository

import com.example.khizana.data.datasource.remote.FakeRemoteDataSource
import com.example.PriceRuleDtoTestFactory
import com.example.PriceRuleRequestTestFactory
import com.example.khizana.domain.model.PriceRuleRequestDomain
import com.example.khizana.domain.repository.PriceRuleRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test


class PriceRuleDomainRequestRepositoryImplTest {
    private lateinit var priceRuleRepositoryImpl: PriceRuleRepository
    private lateinit var remoteDataSourceImpl: RemoteDataSource
    private var priceRulesList = mutableListOf(PriceRuleDtoTestFactory.createPriceRulesItemEntity())

    @Before
    fun setup() {
        remoteDataSourceImpl = FakeRemoteDataSource(priceRulesList = priceRulesList)
        priceRuleRepositoryImpl = PriceRuleRepositoryImpl(
            remoteDataSource = remoteDataSourceImpl
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getPriceRules_flowOfPriceRules() = runTest {
        //Given

        //When
        priceRuleRepositoryImpl.getPriceRules()

        //Then
        assertThat(priceRulesList.size, `is`(1))
    }

    @Test
    fun createPriceRule_addPriceRuleToPriceRulesList() = runTest {
        //Given

        //When
        priceRuleRepositoryImpl.createPriceRules(
            PriceRuleRequestDomain(
                priceRule = PriceRuleRequestTestFactory.createPriceRule(
                    title = "new title",
                )
            )
        )

        //Then
        assertThat(priceRulesList.size, `is`(2))
        assertThat(priceRulesList.first().title, `is`("Default Discount"))
        assertThat(priceRulesList.last().title, `is`("new title"))
    }

    @Test
    fun deletePriceRule_deletePriceRuleFromPriceRuleList() = runTest {
        //Given

        //When
        priceRuleRepositoryImpl.deletePriceRule("default_id_123")

        //Then
        assertThat(priceRulesList.size, `is`(0))
    }


    @Test
    fun updatePriceRule_updatePriceRuleInPriceRuleList() = runTest {
        //Given

        //When
        priceRuleRepositoryImpl.updatePriceRule(
            "default_id_123", PriceRuleRequestDomain(
                priceRule = PriceRuleRequestTestFactory.createPriceRule(
                    title = "new title",
                )
            )
        )

        //Then
        assertThat(priceRulesList.size, `is`(1))
        assertThat(priceRulesList.first().title, `is`("new title"))
    }
}