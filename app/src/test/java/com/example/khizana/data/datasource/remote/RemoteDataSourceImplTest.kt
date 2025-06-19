package com.example.khizana.data.datasource.remote

import com.example.DiscountCodeDtoTestFactory
import com.example.InventoryItemDtoTestFactory
import com.example.PriceRuleDtoTestFactory
import com.example.PriceRuleRequestDtoTestFactory
import com.example.ProductDtoTestFactory
import com.example.ProductRequestDtoTestFactory
import com.example.khizana.data.dto.Count
import com.example.khizana.data.dto.DiscountCodeEntity
import com.example.khizana.data.dto.DiscountCodeRequest
import com.example.khizana.data.dto.InventoryItemEntity
import com.example.khizana.data.dto.InventoryItemRequest
import com.example.khizana.data.dto.InventoryLevelRequest
import com.example.khizana.data.dto.InventoryLevelsItemEntity
import com.example.khizana.data.dto.Location
import com.example.khizana.data.dto.LocationsItemEntity
import com.example.khizana.data.dto.Order
import com.example.khizana.data.dto.ProductRequest
import com.example.khizana.domain.model.InventoryItem
import com.example.khizana.domain.model.InventoryItemRequestDomain
import com.example.khizana.domain.model.InventoryLevelRequestDomain
import com.example.khizana.domain.model.LocationDomain
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test


class RemoteDataSourceImplTest {

    private lateinit var apiService: ApiService
    private lateinit var auth: AuthService
    private lateinit var remoteDataSourceImpl: RemoteDataSourceImpl
    private var productsList = mutableListOf(ProductDtoTestFactory.createProductItemEntity())
    private var priceRulesList = mutableListOf(PriceRuleDtoTestFactory.createPriceRulesItemEntity())
    private var discountCodesList = mutableListOf(
        DiscountCodeDtoTestFactory.createDiscountCodeItemEntity()
    )
    private var inventoryItemsList =
        mutableListOf(InventoryItemDtoTestFactory.createInventoryItemEntity())
    private var inventoryLocationList = mutableListOf(
        LocationsItemEntity(
            country = "",
            active = true,
            countryCode = "",
            name = "",
            countryName = "",
            id = "123"
        )
    )
    private val inventoryLevelsList: MutableList<InventoryLevelsItemEntity> = mutableListOf(
        InventoryLevelsItemEntity(
            available = 10,
            updatedAt = "",
            inventoryItemId = "",
            locationId = "",
        )
    )

    @Before
    fun setup() {
        apiService = FakeApiService(
            productsList,
            priceRulesList,
            discountCodesList,
            inventoryLocationList,
            inventoryLevelsList,
            inventoryItemsList
        )
        auth = mockk()
        remoteDataSourceImpl = RemoteDataSourceImpl(apiService, auth)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getProducts_returnProducts() = runTest {
        //Given

        //When
        remoteDataSourceImpl.getProducts()
        //Then
        assertThat(productsList.size, `is`(1))
        assertThat(productsList.first().id, `is`("prod_123"))
    }

    @Test
    fun createProduct_flowOfPriceRulesRequest() = runTest {
        //Given

        //When
        remoteDataSourceImpl.createProduct(
            ProductRequestDtoTestFactory.createProductRequest(
                ProductDtoTestFactory.createProductItemEntity(
                    id = "prod_456",
                    title = "new title",
                )
            )
        )

        //Then
        assertThat(productsList.size, `is`(2))
        assertThat(productsList.first().id, `is`("prod_123"))
        assertThat(productsList.last().id, `is`("prod_456"))
        assertThat(productsList.last().title, `is`("new title"))
    }

    @Test
    fun deleteProduct_deleteItemFromProductList() = runTest {
        //Given

        //When
        remoteDataSourceImpl.deleteProduct("prod_123")

        //Then
        assertThat(productsList.size, `is`(0))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getProductById_flowOfProductRequest() = runTest {
        //Given
        val values = mutableListOf<ProductRequest>()

        //When
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            remoteDataSourceImpl.getProductById("prod_123").toList(
                values
            )
        }
        advanceUntilIdle()

        //Then
        assertThat(values.size, `is`(1))
        assertThat(values.first().product?.id, `is`("prod_123"))
    }

    @Test
    fun editProduct_editItemInProductList() = runTest {
        //Given

        //When
        remoteDataSourceImpl.editProduct(
            "prod_123", ProductRequestDtoTestFactory.createProductRequest(
                ProductDtoTestFactory.createProductItemEntity(
                    title = "new title",
                )
            )
        )

        //Then
        assertThat(productsList.size, `is`(1))
        assertThat(productsList.first().title, `is`("new title"))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getProductsCount_flowOfCount() = runTest {
        //Given
        val values = mutableListOf<Count>()

        //When
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            remoteDataSourceImpl.getProductsCount().toList(
                values
            )
        }
        advanceUntilIdle()

        //Then
        assertThat(values.size, `is`(1))
    }

    @Test
    fun getPriceRules_returnPriceRules() = runTest {
        //Given

        //When
        remoteDataSourceImpl.getPriceRules()
        //Then
        assertThat(priceRulesList.size, `is`(1))
        assertThat(priceRulesList.first().id, `is`("default_id_123"))
    }

    @Test
    fun createPriceRules_addPriceRuleToPriceRulesList() = runTest {
        //Given

        //When
        remoteDataSourceImpl.createPriceRules(
            PriceRuleRequestDtoTestFactory.createTestPriceRuleRequest(
                PriceRuleRequestDtoTestFactory.createTestPriceRuleEntity(
                    value = "20",
                    title = "new title",
                )
            )
        )

        //Then
        assertThat(priceRulesList.size, `is`(2))
        assertThat(priceRulesList.first().id, `is`("default_id_123"))
        assertThat(priceRulesList.last().title, `is`("new title"))
        assertThat(priceRulesList.last().value, `is`("20"))
    }

    @Test
    fun deletePriceRules_deletePriceRuleFromPriceRuleList() = runTest {
        //Given

        //When
        remoteDataSourceImpl.deletePriceRules("default_id_123")

        //Then
        assertThat(priceRulesList.size, `is`(0))
    }

    @Test
    fun editPriceRule_editPriceRuleInPriceRuleList() = runTest {
        //Given

        //When
        remoteDataSourceImpl.updatePriceRules(
            "default_id_123",
            PriceRuleRequestDtoTestFactory.createTestPriceRuleRequest(
                PriceRuleRequestDtoTestFactory.createTestPriceRuleEntity(
                    title = "new title",
                )
            )
        )

        //Then
        assertThat(priceRulesList.size, `is`(1))
        assertThat(priceRulesList.first().title, `is`("new title"))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllInventoryLocations_inventoryLocation() = runTest {
        //Given
        val values = mutableListOf<Location>()


        //When
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            remoteDataSourceImpl.getAllInventoryLocations().toList(
                values
            )
        }
        advanceUntilIdle()

        //Then
        assertThat(values.size, `is`(1))
        assertThat(values.first().locations.first().id, `is`("123"))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getOrders_returnOrders() = runTest {
        //Given
        val values = mutableListOf<Order>()
        //When
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            remoteDataSourceImpl.getOrders(
                minDate = "",
                maxDate = ""
            ).toList(
                values
            )
        }
        advanceUntilIdle()

        //Then
        assertThat(values.size, `is`(1))
        assertThat(values.first().orders?.first()?.totalPrice, `is`("90.00"))
        assertThat(values.first().orders?.first()?.totalDiscounts, `is`("10.00"))
        assertThat(
            values.first().orders?.first()?.totalPriceSet?.shopMoney?.amount,
            `is`("90.00")
        )
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getOrdersCountToday_returnCount() = runTest {
        //Given
        val values = mutableListOf<Count>()
        //When
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            remoteDataSourceImpl.getOrdersCount(
                minDate = "",
                maxDate = ""
            ).toList(
                values
            )
        }
        advanceUntilIdle()

        //Then
        assertThat(values.size, `is`(1))
        assertThat(values.first().count, `is`(1))
    }

    @Test
    fun getDiscountCodes_returnDiscountCodes() = runTest {
        //Given

        //When
        remoteDataSourceImpl.getDiscountCodes(
            "price_rule_123"
        )
        //Then
        assertThat(discountCodesList.size, `is`(1))
        assertThat(discountCodesList.first().id, `is`("discount_123"))
        assertThat(discountCodesList.first().priceRuleId, `is`("price_rule_123"))

    }

    @Test
    fun createDiscountCodes_addDiscountCodeToDiscountCodesList() = runTest {
        //Given

        //When
        remoteDataSourceImpl.createDiscountCodes(
            "price_rule_123",
            DiscountCodeRequest(
                discountCode = DiscountCodeEntity(
                    code = "new code",
                )
            )


        )

        //Then
        assertThat(discountCodesList.size, `is`(2))
        assertThat(discountCodesList.first().id, `is`("discount_123"))
        assertThat(discountCodesList.last().code, `is`("new code"))
    }

    @Test
    fun deleteDiscountCodes_deletePDiscountCodesFromDiscountCodesList() = runTest {
        //Given

        //When
        remoteDataSourceImpl.deleteDiscountCodes(
            discountCodeId = "discount_123",
            priceRuleId = "price_rule_123"
        )

        //Then
        assertThat(discountCodesList.size, `is`(0))
    }

    @Test
    fun updateDiscountCodes_editDiscountCodesInDiscountCodesList() = runTest {
        //Given

        //When
        remoteDataSourceImpl.updateDiscountCodes(
            "default_id_123",
            "discount_123",
            DiscountCodeRequest(
                discountCode = DiscountCodeEntity(
                    code = "new code",
                )
            )
        )

        //Then
        assertThat(discountCodesList.size, `is`(1))
        assertThat(discountCodesList.first().code, `is`("new code"))
    }

    @Test
    fun getInventoryItem_inventoryItem() = runTest {

        //Given
        //When
        remoteDataSourceImpl.getInventoryItem("item_123")
        //Then
        assertThat(inventoryItemsList.size, `is`(1))
        assertThat(inventoryItemsList.first().cost, `is`("10.0"))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllInventoryLocations_location() = runTest {
        //Given
        val values = mutableListOf<Location>()
        //When
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            remoteDataSourceImpl.getAllInventoryLocations().toList(values)
        }
        //Then
        assertThat(values.size, `is`(1))
        assertThat(values.first().locations.size, `is`(1))
        assertThat(values.first().locations.first().id, `is`("123"))
    }

    @Test
    fun updateInventoryItem_changeInventoryItem() = runTest {

        //Given
        //When
        remoteDataSourceImpl.updateInventoryItem(
            InventoryItemRequest(
                inventoryItem = InventoryItemEntity(
                    cost = "20.0",
                    tracked = true
                )
            ),
            inventoryItemId = ""
        )
        //Then
        assertThat(inventoryItemsList.size, `is`(1))
        assertThat(inventoryItemsList.first().cost ,`is`("20.0"))
        assertThat(inventoryItemsList.first().tracked ,`is`(true))
    }

    @Test
    fun setInventoryItemQuantity_changeQuantity()= runTest{

        //Given
        //When
        remoteDataSourceImpl.setInventoryItemQuantity(
            InventoryLevelRequest(
                available = 10,
                inventoryItemId = "",
                availableAdjustment = 0,
                locationId = "",
            )
        )
        //Then
        assertThat(inventoryLevelsList.size, `is`(1))
        assertThat(inventoryLevelsList.first().available, `is`(10))
    }
}


