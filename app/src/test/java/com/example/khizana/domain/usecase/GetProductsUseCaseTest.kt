package com.example.khizana.domain.usecase

import com.example.ProductTestFactory
import com.example.khizana.data.repository.FakeProductRepository
import com.example.khizana.domain.repository.ProductRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test


class GetProductsUseCaseTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var getProductsUseCase: GetProductsUseCase
    private var productsList = mutableListOf(ProductTestFactory.createProductItem())

    @Before
    fun setup() {
        productRepository = FakeProductRepository(
            productsList = productsList
        )
        getProductsUseCase = GetProductsUseCase(productRepository)
    }


    @Test
    fun getProducts_returnProducts() = runTest {

        //Given
        //When
        getProductsUseCase.getProducts()
        //Then
        assertThat(productsList.size, `is`(1))
        assertThat(productsList[0].id, `is`("sample prod id"))
    }
}