package com.example.khizana.domain.usecase

import com.example.khizana.data.repository.FakeInventoryRepository
import com.example.khizana.domain.model.InventoryItem
import com.example.khizana.domain.model.InventoryItemRequestDomain
import com.example.khizana.domain.repository.InventoryRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test


class UpdateInventoryItemUseCaseTest{
    private lateinit var inventoryRepository: InventoryRepository
    private lateinit var updateInventoryItemUseCase : UpdateInventoryItemUseCase
    private var inventoryItemsList =
        mutableListOf(
            InventoryItem(
                cost = "10.0",
                tracked = true
            )
        )

    @Before
    fun setup() {
        inventoryRepository = FakeInventoryRepository(
            inventoryItemsList = inventoryItemsList
        )
        updateInventoryItemUseCase = UpdateInventoryItemUseCase(inventoryRepository)
    }



    @Test
    fun updateInventoryItem_changeInventoryItem() = runTest {

        //Given
        //When
        updateInventoryItemUseCase.updateInventoryItem(
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
        assertThat(inventoryItemsList.first().cost , `is`("20.0"))
        assertThat(inventoryItemsList.first().tracked , `is`(true))
    }
}