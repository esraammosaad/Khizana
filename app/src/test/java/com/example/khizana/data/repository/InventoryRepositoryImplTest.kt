package com.example.khizana.data.repository

import com.example.InventoryItemDtoTestFactory
import com.example.khizana.data.datasource.remote.FakeRemoteDataSource
import com.example.khizana.data.dto.InventoryLevelsItemEntity
import com.example.khizana.data.dto.LocationsItemEntity
import com.example.khizana.domain.model.InventoryItem
import com.example.khizana.domain.model.InventoryItemRequestDomain
import com.example.khizana.domain.model.InventoryLevelRequestDomain
import com.example.khizana.domain.model.LocationDomain
import com.example.khizana.domain.repository.InventoryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test


class InventoryRepositoryImplTest {

    private lateinit var inventoryRepositoryImpl: InventoryRepository
    private lateinit var remoteDataSourceImpl: RemoteDataSource
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
        remoteDataSourceImpl = FakeRemoteDataSource(
            inventoryItemsList = inventoryItemsList,
            inventoryLocationsList = inventoryLocationList,
            inventoryLevelsList = inventoryLevelsList
        )
        inventoryRepositoryImpl = InventoryRepositoryImpl(
            remoteDataSource = remoteDataSourceImpl
        )
    }

    @Test
    fun getInventoryItem_flowOfInventoryItem() = runTest {

        //Given
        //When
        inventoryRepositoryImpl.getInventoryItem("item_123")
        //Then
        assertThat(inventoryItemsList.size, `is`(1))
        assertThat(inventoryItemsList.first().cost, `is`("10.0"))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getAllInventoryLocations_flowOfLocation() = runTest {
        //Given
        val values = mutableListOf<LocationDomain>()
        //When
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            inventoryRepositoryImpl.getAllInventoryLocations().toList(values)
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
        inventoryRepositoryImpl.updateInventoryItem(
            InventoryItemRequestDomain(
                inventoryItem = InventoryItem(
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
        inventoryRepositoryImpl.setInventoryItemQuantity(InventoryLevelRequestDomain(
            available = 10,
            inventory_item_id = "",
            available_adjustment = 0,
            location_id = ""
        ))
        //Then
        assertThat(inventoryLevelsList.size, `is`(1))
        assertThat(inventoryLevelsList.first().available, `is`(10))
    }
}