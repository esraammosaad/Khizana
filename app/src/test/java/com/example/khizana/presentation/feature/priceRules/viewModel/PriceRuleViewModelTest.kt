package com.example.khizana.presentation.feature.priceRules.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.DiscountCodeDtoTestFactory
import com.example.PriceRuleDtoTestFactory
import com.example.PriceRuleRequestDtoTestFactory
import com.example.PriceRuleRequestTestFactory
import com.example.khizana.data.repository.FakeDiscountCodeRepository
import com.example.khizana.data.repository.FakePriceRuleRepository
import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.domain.model.DiscountCodeDomain
import com.example.khizana.domain.model.DiscountCodeDomainRequest
import com.example.khizana.domain.model.DiscountCodeRequestDomain
import com.example.khizana.domain.model.PriceRuleDomain
import com.example.khizana.domain.model.PriceRuleRequestDomain
import com.example.khizana.domain.repository.DiscountCodeRepository
import com.example.khizana.domain.repository.PriceRuleRepository
import com.example.khizana.domain.usecase.CreateDiscountCodeUseCase
import com.example.khizana.domain.usecase.CreatePriceRuleUseCase
import com.example.khizana.domain.usecase.DeleteDiscountCodeUseCase
import com.example.khizana.domain.usecase.DeletePriceRuleUseCase
import com.example.khizana.domain.usecase.EditDiscountCodeUseCase
import com.example.khizana.domain.usecase.EditPriceRuleUseCase
import com.example.khizana.domain.usecase.GetAllPriceRulesUseCase
import com.example.khizana.domain.usecase.GetDiscountCodeUseCase
import com.example.khizana.utilis.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PriceRuleViewModelTest {
    private lateinit var priceRuleViewModel: PriceRuleViewModel
    private lateinit var priceRuleRepository: PriceRuleRepository
    private lateinit var discountCodeRepository: DiscountCodeRepository
    private lateinit var getAllPriceRulesUseCase: GetAllPriceRulesUseCase
    private lateinit var createPriceRuleUseCase: CreatePriceRuleUseCase
    private lateinit var editPriceRuleUseCase: EditPriceRuleUseCase
    private lateinit var deletePriceRuleUseCase: DeletePriceRuleUseCase
    private lateinit var getDiscountCodeUseCase: GetDiscountCodeUseCase
    private lateinit var createDiscountCodeUseCase: CreateDiscountCodeUseCase
    private lateinit var deleteDiscountCodeUseCase: DeleteDiscountCodeUseCase
    private lateinit var editDiscountCodeUseCase: EditDiscountCodeUseCase


    private var priceRuleList =
        mutableListOf(PriceRuleDtoTestFactory.createPriceRulesItemEntity().toDomain())
    private var discountCodeList =
        mutableListOf(DiscountCodeDtoTestFactory.createDiscountCodeItemEntity().toDomain())


    @get:Rule
    val myRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        priceRuleRepository = FakePriceRuleRepository(priceRuleList)
        discountCodeRepository = FakeDiscountCodeRepository(discountCodeList)
        getAllPriceRulesUseCase = GetAllPriceRulesUseCase(priceRuleRepository)
        createPriceRuleUseCase = CreatePriceRuleUseCase(priceRuleRepository)
        editPriceRuleUseCase = EditPriceRuleUseCase(priceRuleRepository)
        deletePriceRuleUseCase = DeletePriceRuleUseCase(priceRuleRepository)
        getDiscountCodeUseCase = GetDiscountCodeUseCase(discountCodeRepository)
        createDiscountCodeUseCase = CreateDiscountCodeUseCase(discountCodeRepository)
        editDiscountCodeUseCase = EditDiscountCodeUseCase(discountCodeRepository)
        deleteDiscountCodeUseCase = DeleteDiscountCodeUseCase(discountCodeRepository)
        priceRuleViewModel = PriceRuleViewModel(
            getAllPriceRulesUseCase = getAllPriceRulesUseCase,
            createPriceRuleUseCase = createPriceRuleUseCase,
            editPriceRuleUseCase = editPriceRuleUseCase,
            deletePriceRuleUseCase = deletePriceRuleUseCase,
            getDiscountCodeUseCase = getDiscountCodeUseCase,
            createDiscountCodeUseCase = createDiscountCodeUseCase,
            editDiscountCodeUseCase = editDiscountCodeUseCase,
            deleteDiscountCodeUseCase = deleteDiscountCodeUseCase
        )
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllPriceRules_changePriceRulesState() = runTest {
        //Given
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            priceRuleViewModel.priceRules.toList(
                values
            )
        }

        //When
        priceRuleViewModel.getAllPriceRules()
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<PriceRuleDomain>
        assertThat(result.result?.priceRules?.size, `is`(1))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createPriceRule_changePriceRules() = runTest {
        //Given
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            priceRuleViewModel.priceRules.toList(
                values
            )
        }

        //When
        priceRuleViewModel.createPriceRule(
            PriceRuleRequestDomain(
                priceRule = PriceRuleRequestTestFactory.createPriceRule()
            )
        )
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<PriceRuleDomain>
        assertThat(result.result?.priceRules?.size, `is`(2))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deletePriceRule_changePriceRulesState() = runTest {
        //Given
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            priceRuleViewModel.priceRules.toList(
                values
            )
        }

        //When
        priceRuleViewModel.deletePriceRule("default_id_123")
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<PriceRuleDomain>
        assertThat(result.result?.priceRules?.size, `is`(0))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun editPriceRule_changePriceRulesState() = runTest {
        //Given
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            priceRuleViewModel.priceRules.toList(
                values
            )
        }

        //When
        priceRuleViewModel.editPriceRule(
            "default_id_123", PriceRuleRequestDtoTestFactory.createTestPriceRuleRequest(
                PriceRuleRequestDtoTestFactory.createTestPriceRuleEntity(
                    title = "new title",
                )
            ).toDomain()
        )
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<PriceRuleDomain>
        assertThat(result.result?.priceRules?.first()?.title, `is`("new title"))
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getDiscountCodes_changeDiscountCodesState() = runTest {
        //Given
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            priceRuleViewModel.discountCodes.toList(
                values
            )
        }

        //When
        priceRuleViewModel.getDiscountCodes(
            priceRuleId = "default_id_123"
        )
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<DiscountCodeDomain>
        assertThat(result.result?.discountCodes?.size, `is`(1))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createDiscountCode_changeDiscountCodes() = runTest {
        //Given
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            priceRuleViewModel.discountCodes.toList(
                values
            )
        }

        //When
        priceRuleViewModel.createDiscountCode(
            priceRuleId = "default_id_123",
            discountCodeRequest = DiscountCodeRequestDomain(
                discount_code = DiscountCodeDomainRequest(
                    code = "new code",
                )
            )
        )
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<DiscountCodeDomain>
        assertThat(result.result?.discountCodes?.size, `is`(2))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteDiscountCode_changeDiscountCodesState() = runTest {
        //Given
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            priceRuleViewModel.discountCodes.toList(
                values
            )
        }

        //When
        priceRuleViewModel.deleteDiscountCode(
            "price_rule_123",
            "discount_123"
        )
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<DiscountCodeDomain>
        assertThat(result.result?.discountCodes?.size, `is`(0))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun editDiscountCode_changeDiscountCodesState() = runTest {
        //Given
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            priceRuleViewModel.discountCodes.toList(
                values
            )
        }

        //When
        priceRuleViewModel.editDiscountCode(
            "price_rule_123",
            "discount_123",
            DiscountCodeRequestDomain(
                discount_code = DiscountCodeDomainRequest(
                    code = "new code",
                )
            )
        )
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<DiscountCodeDomain>
        assertThat(result.result?.discountCodes?.first()?.code, `is`("new code"))
    }
}