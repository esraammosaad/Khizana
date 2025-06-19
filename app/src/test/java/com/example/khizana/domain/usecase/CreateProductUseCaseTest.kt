package com.example.khizana.domain.usecase

import com.example.ProductTestFactory
import com.example.khizana.data.repository.FakeProductRepository
import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.repository.ProductRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test


class CreateProductUseCaseTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var createProductUseCase: CreateProductUseCase
    private var productsList = mutableListOf(ProductTestFactory.createProductItem())


    @Before
    fun setup() {
        productRepository = FakeProductRepository(
            productsList = productsList,
        )
        createProductUseCase = CreateProductUseCase(productRepository)
    }

    @Test
    fun createProduct_returnProduct() = runTest {
        //Given
        //When
        createProductUseCase.createProduct(
            ProductRequestDomain(
                product = ProductTestFactory.createProductItem(
                    id = "sample prod id"
                )
            )
        )
        //Then
        assertThat(productsList.size, `is`(2))
        assertThat(productsList.last().id, `is`("sample prod id"))
    }
}