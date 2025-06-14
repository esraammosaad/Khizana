package com.example.khizana.presentation.feature.home.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.khizana.domain.model.CountDomain
import com.example.khizana.domain.model.CurrentSubtotalPriceSet
import com.example.khizana.domain.model.CurrentTotalPriceSet
import com.example.khizana.domain.model.LocationDomain
import com.example.khizana.domain.model.LocationsItem
import com.example.khizana.domain.model.OrderDomain
import com.example.khizana.domain.model.OrdersItem
import com.example.khizana.domain.model.PresentmentMoneySet
import com.example.khizana.domain.model.ShopMoneySet
import com.example.khizana.domain.model.TotalDiscountsSet
import com.example.khizana.domain.usecase.GetInventoryLocationsUseCase
import com.example.khizana.domain.usecase.GetOrdersUseCase
import com.example.khizana.domain.usecase.GetProductsCountUseCase
import com.example.khizana.utilis.Response
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var getOrdersUseCase: GetOrdersUseCase
    private lateinit var getProductsCountUseCase: GetProductsCountUseCase
    private lateinit var getInventoryLocationsUseCase: GetInventoryLocationsUseCase

    @get:Rule
    val myRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        getOrdersUseCase = mockk(relaxed = true)
        getProductsCountUseCase = mockk(relaxed = true)
        getInventoryLocationsUseCase = mockk(relaxed = true)
        homeViewModel = HomeViewModel(
            getOrdersUseCase = getOrdersUseCase,
            getProductsCountUseCase = getProductsCountUseCase,
            getInventoryLocationsUseCase = getInventoryLocationsUseCase
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
    fun getTotalOrdersPrice_totalOrderPriceStateSuccessWithZeroResult() = runTest {
        //Given
        coEvery { getOrdersUseCase.getOrders(any(), any()) } returns flowOf(
            OrderTestFactory.createOrderDomain()
        )
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            homeViewModel.totalOrdersPrice.toList(
                values
            )
        }

        //When
        homeViewModel.getTotalOrdersPrice()
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<*>
        assertThat(result.result, `is`(0.0f))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getTotalOrdersPrice_totalOrderPriceStateSuccessWithNull() = runTest {
        //Given
        coEvery { getOrdersUseCase.getOrders(any(), any()) } returns flowOf(
            OrderTestFactory.createNullOrderDomain()
        )
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            homeViewModel.totalOrdersPrice.toList(
                values
            )
        }

        //When
        homeViewModel.getTotalOrdersPrice()
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<*>
        assertThat(result.result, `is`(0.0f))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getTotalOrdersPrice_totalOrderPriceStateSuccessWithEmptyOrderList() = runTest {
        //Given
        coEvery { getOrdersUseCase.getOrders(any(), any()) } returns flowOf(
            OrderTestFactory.createEmptyOrderDomain()
        )
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            homeViewModel.totalOrdersPrice.toList(
                values
            )
        }

        //When
        homeViewModel.getTotalOrdersPrice()
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<*>
        assertThat(result.result, `is`(0.0f))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getTotalRevenue_totalOrderPriceStateSuccessWithZeroResult() = runTest {
        //Given
        coEvery { getOrdersUseCase.getOrders(any(), any()) } returns flowOf(
            OrderTestFactory.createOrderDomain()
        )
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            homeViewModel.revenue.toList(
                values
            )
        }

        //When
        homeViewModel.getTotalRevenue()
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<*>
        assertThat(result.result, `is`(0.0))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getTotalRevenue_totalOrderPriceStateSuccessWithNull() = runTest {
        //Given
        coEvery { getOrdersUseCase.getOrders(any(), any()) } returns flowOf(
            OrderTestFactory.createNullOrderDomain()
        )
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            homeViewModel.revenue.toList(
                values
            )
        }

        //When
        homeViewModel.getTotalRevenue()
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<*>
        assertThat(result.result, `is`(0.0))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getTotalRevenue_totalOrderPriceStateSuccessWithEmptyOrderList() = runTest {
        //Given
        coEvery { getOrdersUseCase.getOrders(any(), any()) } returns flowOf(
            OrderTestFactory.createEmptyOrderDomain()
        )
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            homeViewModel.revenue.toList(
                values
            )
        }

        //When
        homeViewModel.getTotalRevenue()
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<*>
        assertThat(result.result, `is`(0.0))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getOrdersCount_orderCountStateSuccessWithZero() = runTest {
        //Given
        val orderCount = CountDomain(
            count = 0
        )
        coEvery { getOrdersUseCase.getOrdersCount(any(), any()) } returns flowOf(
            orderCount
        )
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            homeViewModel.ordersCount.toList(
                values
            )
        }

        //When
        homeViewModel.getOrdersCount()
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<List<CountDomain>>
        assertThat(result.result?.first()?.count, `is`(0))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getProductsCount_productsCountStateSuccessWithZero() = runTest {
        //Given
        val productsCount = CountDomain(
            count = 0
        )
        coEvery { getProductsCountUseCase.getProductsCount() } returns flowOf(
            productsCount
        )
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            homeViewModel.productsCount.toList(
                values
            )
        }

        //When
        homeViewModel.getProductsCount()
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<*>
        assertThat(result.result, `is`(0))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getInventoryLocationsCount_locationCountStateSuccessWithOne() = runTest {
        //Given
        val locations = LocationDomain(
            locations = listOf(
                LocationsItem(
                    id = "",
                    country = "",
                    active = false,
                    country_code = "",
                    name = "",
                    country_name = "",
                )
            )
        )
        coEvery { getInventoryLocationsUseCase.getAllInventoryLocations() } returns flowOf(
            locations
        )
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            homeViewModel.inventoryLocationsCount.toList(
                values
            )
        }

        //When
        homeViewModel.getInventoryLocationsCount()
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<*>
        assertThat(result.result, `is`(1))
    }
}


object OrderTestFactory {

    private fun createShopMoneySet(
        amount: String = "0.0",
        currencyCode: String = "EGP"
    ): ShopMoneySet = ShopMoneySet(
        amount = amount,
        currency_code = currencyCode
    )

    private fun createPresentmentMoneySet(
        amount: String = "0.0",
        currencyCode: String = "USD"
    ): PresentmentMoneySet = PresentmentMoneySet(
        amount = amount,
        currency_code = currencyCode
    )

    private fun createOrderItem(
        subtotalPrice: String = "0.0",
        currentSubtotalPrice: String = "0.0",
        currency: String = "EGP",
        totalPrice: String = "0.0",
        totalDiscounts: String = "0.0",
        shopMoneyAmount: String = "0.0",
        presentmentMoneyAmount: String = "0.0"
    ): OrdersItem = OrdersItem(
        subtotal_price = subtotalPrice,
        current_subtotal_price = currentSubtotalPrice,
        currency = currency,
        total_price = totalPrice,
        total_discounts = totalDiscounts,
        total_discounts_set = TotalDiscountsSet(
            shop_money = createShopMoneySet(totalDiscounts),
            presentment_money = createPresentmentMoneySet(totalDiscounts)
        ),
        current_subtotal_price_set = CurrentSubtotalPriceSet(
            shop_money = createShopMoneySet(currentSubtotalPrice),
            presentment_money = createPresentmentMoneySet(currentSubtotalPrice)
        ),
        current_total_price_set = CurrentTotalPriceSet(
            shop_money = createShopMoneySet(shopMoneyAmount),
            presentment_money = createPresentmentMoneySet(presentmentMoneyAmount)
        )
    )

    fun createOrderDomain(
        orders: List<OrdersItem> = listOf(createOrderItem())
    ): OrderDomain = OrderDomain(
        orders = orders
    )

    fun createEmptyOrderDomain(): OrderDomain = OrderDomain(orders = emptyList())

    fun createNullOrderDomain(): OrderDomain = OrderDomain(orders = null)
}