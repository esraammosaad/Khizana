package com.example.khizana.data.repository

import com.example.DiscountCodeDtoTestFactory
import com.example.khizana.data.datasource.remote.FakeRemoteDataSource
import com.example.khizana.domain.model.DiscountCodeDomainRequest
import com.example.khizana.domain.model.DiscountCodeRequestDomain
import com.example.khizana.domain.repository.DiscountCodeRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test


class DiscountCodeRepositoryImplTest {

    private lateinit var discountCodeRepositoryImpl: DiscountCodeRepository
    private lateinit var remoteDataSourceImpl: RemoteDataSource
    private var discountCodesList = mutableListOf(DiscountCodeDtoTestFactory.createDiscountCodeItemEntity())

    @Before
    fun setup() {
        remoteDataSourceImpl = FakeRemoteDataSource(discountCodesList = discountCodesList)
        discountCodeRepositoryImpl = DiscountCodeRepositoryImpl(
            remoteDataSource = remoteDataSourceImpl
        )
    }

    @After
    fun tearDown() {
    }


    @Test
    fun getDiscountCodes_flowOfDiscountCodes() = runTest {
        //Given

        //When
        discountCodeRepositoryImpl.getDiscountCodes(
            priceRuleId = "price_rule_123"
        )

        //Then
        assertThat(discountCodesList.size, `is`(1))
    }

    @Test
    fun createDiscountCode_addDiscountCodeToDiscountCodesList() = runTest {
        //Given

        //When
        discountCodeRepositoryImpl.createDiscountCode(
            priceRuleId = "price_rule_123",
            discountCodeRequest = DiscountCodeRequestDomain(
                discount_code = DiscountCodeDomainRequest(
                    code = "new code",
                )
            )
        )

        //Then
        assertThat(discountCodesList.size, `is`(2))
        assertThat(discountCodesList.first().code, `is`("DISCOUNT20"))
        assertThat(discountCodesList.last().code, `is`("new code"))
    }

    @Test
    fun deleteDiscountCode_deleteDiscountCodeFromDiscountCodesList() = runTest {
        //Given

        //When
        discountCodeRepositoryImpl.deleteDiscountCode(priceRuleId = "price_rule_123", discountCodeId = "discount_123")

        //Then
        assertThat(discountCodesList.size, `is`(0))
    }


    @Test
    fun editDiscountCode_editDiscountCodeInDiscountCodesList() = runTest {
        //Given

        //When
        discountCodeRepositoryImpl.updateDiscountCode(
            priceRuleId = "price_rule_123",
            discountCodeId = "discount_123",
            discountCodeRequest = DiscountCodeRequestDomain(
                discount_code = DiscountCodeDomainRequest(
                    code = "new code",
                )
            )
        )

        //Then
        assertThat(discountCodesList.size, `is`(1))
        assertThat(discountCodesList.first().code, `is`("new code"))
    }
}