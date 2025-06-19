package com.example.khizana.domain.usecase

import com.example.khizana.data.repository.FakeInventoryRepository
import com.example.khizana.domain.model.InventoryLevelRequestDomain
import com.example.khizana.domain.model.InventoryLevelsItem
import com.example.khizana.domain.repository.InventoryRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test


class SetInventoryItemQuantityUseCaseTest{
    private lateinit var inventoryRepository: InventoryRepository
    private lateinit var setInventoryItemQuantityUseCase : SetInventoryItemQuantityUseCase
    private var inventoryLevelsList = mutableListOf(
        InventoryLevelsItem(
            updatedAt = "",
            inventoryItemId = "",
            available = 0,
            locationId = ""
        )
    )

    @Before
    fun setup() {
        inventoryRepository = FakeInventoryRepository(
            inventoryLevelsList = inventoryLevelsList
        )
        setInventoryItemQuantityUseCase = SetInventoryItemQuantityUseCase(inventoryRepository)
    }

    @Test
    fun setInventoryItemQuantity_returnInventoryItem() = runTest {
        //Given
        //When
        setInventoryItemQuantityUseCase.setInventoryItemQuantity(
            InventoryLevelRequestDomain(
            available = 10,
            inventory_item_id = "",
            available_adjustment = 0,
            location_id = ""
        )
        )
        //Then
        assertThat(inventoryLevelsList.size, `is`(1))
        assertThat(inventoryLevelsList.first().available, `is`(10))
    }

}