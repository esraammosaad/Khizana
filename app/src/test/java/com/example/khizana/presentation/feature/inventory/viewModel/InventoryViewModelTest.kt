package com.example.khizana.presentation.feature.inventory.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ProductTestFactory
import com.example.khizana.data.repository.FakeInventoryRepository
import com.example.khizana.data.repository.FakeProductRepository
import com.example.khizana.domain.model.InventoryItem
import com.example.khizana.domain.model.InventoryItemRequestDomain
import com.example.khizana.domain.model.InventoryLevelRequestDomain
import com.example.khizana.domain.model.InventoryLevelsItem
import com.example.khizana.domain.model.LocationsItem
import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.domain.repository.InventoryRepository
import com.example.khizana.domain.repository.ProductRepository
import com.example.khizana.domain.usecase.GetInventoryItemUseCase
import com.example.khizana.domain.usecase.GetProductsUseCase
import com.example.khizana.domain.usecase.SetInventoryItemQuantityUseCase
import com.example.khizana.domain.usecase.UpdateInventoryItemUseCase
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
class InventoryViewModelTest {

    @get:Rule
    val myRule = InstantTaskExecutorRule()

    private lateinit var inventoryViewModel: InventoryViewModel
    private lateinit var productRepository: ProductRepository
    private lateinit var inventoryRepository: InventoryRepository
    private lateinit var getProductsUseCase: GetProductsUseCase
    private lateinit var setInventoryItemQuantityUseCase: SetInventoryItemQuantityUseCase
    private lateinit var updateInventoryItemUseCase: UpdateInventoryItemUseCase
    private lateinit var getInventoryItemUseCase: GetInventoryItemUseCase
    private var inventoryItemsList = mutableListOf(
        InventoryItem(
            cost = "10.0",
            tracked = true
        )
    )
    private var inventoryLocationList = mutableListOf(
        LocationsItem(
            id = "loc_123",
            country = "",
            active = true,
            country_code = "",
            name = "",
            country_name = "",
        )
    )
    private val inventoryLevelsList = mutableListOf(
        InventoryLevelsItem(
            updatedAt = "",
            inventoryItemId = "",
            available = 0,
            locationId = ""
        )
    )
    private var productsList = mutableListOf(ProductTestFactory.createProductItem())


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        inventoryRepository = FakeInventoryRepository(
            inventoryItemsList = inventoryItemsList,
            inventoryLocationList = inventoryLocationList,
            inventoryLevelsList = inventoryLevelsList
        )
        productRepository = FakeProductRepository(
            productsList = productsList
        )
        getProductsUseCase = GetProductsUseCase(productRepository)
        setInventoryItemQuantityUseCase = SetInventoryItemQuantityUseCase(inventoryRepository)
        updateInventoryItemUseCase = UpdateInventoryItemUseCase(inventoryRepository)
        getInventoryItemUseCase = GetInventoryItemUseCase(inventoryRepository)
        inventoryViewModel = InventoryViewModel(
            getProductsUseCase = getProductsUseCase,
            setInventoryItemQuantityUseCase = setInventoryItemQuantityUseCase,
            updateInventoryItemUseCase = updateInventoryItemUseCase,
            getInventoryItemUseCase = getInventoryItemUseCase
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
    fun getProducts_changeProductsState() = runTest {
        //Given
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            inventoryViewModel.products.toList(
                values
            )
        }

        //When
        inventoryViewModel.getProducts()
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<ProductDomain>
        assertThat(result.result?.products?.size, `is`(1))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getInventoryItem_changeInventoryItemState() = runTest {
        //Given
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            inventoryViewModel.inventoryItem.toList(
                values
            )
        }
        //When
        inventoryViewModel.getInventoryItem("sample inv id")
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<InventoryItemRequestDomain>
        assertThat(result.result?.inventoryItem?.cost, `is`("10.0"))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun updateInventoryItemWithQuantity_changeInventoryItemState() = runTest {
        //Given
        val values = mutableListOf<Response>()
        val message = mutableListOf<String>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            inventoryViewModel.products.toList(
                values
            )
        }
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            inventoryViewModel.message.toList(
                message
            )
        }

        //When
        inventoryViewModel.updateInventoryItemWithQuantity(
            InventoryLevelRequestDomain(
                inventory_item_id = "sample inv id",
                available = 10,
                available_adjustment = 0,
                location_id = ""
            ),
            inventoryItemRequestDomain = InventoryItemRequestDomain(
                inventoryItem = InventoryItem(
                    cost = "10.0",
                    tracked = true
                )
            ),
        )
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<ProductDomain>
        assertThat(result.result?.products?.size, `is`(1))
        assertThat(message.last(), `is`("Inventory item updated successfully"))
    }
}