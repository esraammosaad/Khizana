package com.example.khizana.domain.usecase

import com.example.ProductTestFactory
import com.example.khizana.data.repository.FakeProductRepository
import com.example.khizana.domain.repository.ProductRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test


class GetProductsCountUseCaseTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var getProductsCountUseCase: GetProductsCountUseCase
    private var productsList = mutableListOf(ProductTestFactory.createProductItem())


    @Before
    fun setup() {
        productRepository = FakeProductRepository(
            productsList = productsList
        )
        getProductsCountUseCase = GetProductsCountUseCase(productRepository)
    }

    @Test
    fun getProductsCount_returnCount() = runTest {
        //Given
        //When
        getProductsCountUseCase.getProductsCount()
        //Then
        assertThat(productsList.size, `is`(1))
    }
}