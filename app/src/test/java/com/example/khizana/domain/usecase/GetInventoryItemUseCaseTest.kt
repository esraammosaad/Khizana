package com.example.khizana.domain.usecase

import com.example.khizana.data.repository.FakeInventoryRepository
import com.example.khizana.domain.model.InventoryItem
import com.example.khizana.domain.repository.InventoryRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test


class GetInventoryItemUseCaseTest{
    private lateinit var inventoryRepository: InventoryRepository
    private lateinit var getInventoryItemUseCase: GetInventoryItemUseCase
    private var inventoryItemsList = mutableListOf(
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
        getInventoryItemUseCase = GetInventoryItemUseCase(inventoryRepository)
    }

    @Test
    fun getInventoryItem_returnInventoryItem() = runTest {
        //Given
        //When
        getInventoryItemUseCase.getInventoryItem("sample inv id")
        //Then
        assertThat(inventoryItemsList.size, `is`(1))
        assertThat(inventoryItemsList[0].cost, `is`("10.0"))
    }
}
