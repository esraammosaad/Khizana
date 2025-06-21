package com.example.khizana.domain.usecase

import com.example.khizana.data.repository.FakeInventoryRepository
import com.example.khizana.domain.model.LocationsItem
import com.example.khizana.domain.repository.InventoryRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test


class GetInventoryLocationsUseCaseTest {
    private lateinit var inventoryRepository: InventoryRepository
    private lateinit var getInventoryLocationsUseCase: GetInventoryLocationsUseCase
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

    @Before
    fun setup() {
        inventoryRepository = FakeInventoryRepository(
            inventoryLocationList = inventoryLocationList
        )
        getInventoryLocationsUseCase = GetInventoryLocationsUseCase(inventoryRepository)
    }

    @Test
    fun getAllInventoryLocations_returnLocations() = runTest {
        //Given
        //When
        getInventoryLocationsUseCase.getAllInventoryLocations()
        //Then
        assertThat(inventoryLocationList.size, `is`(1))
        assertThat(inventoryLocationList[0].id, `is`("loc_123"))
    }
}