package com.example.khizana.domain.usecase

import com.example.ProductDtoTestFactory
import com.example.ProductTestFactory
import com.example.khizana.data.repository.FakeProductRepository
import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.repository.ProductRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test


class EditProductUseCaseTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var editProductUseCase: EditProductUseCase
    private var productList =
        mutableListOf(ProductDtoTestFactory.createProductItemEntity().toDomain())

    @Before
    fun setup() {
        productRepository = FakeProductRepository(productList)
        editProductUseCase = EditProductUseCase(productRepository)
    }

    @Test
    fun editProduct_changeProduct() = runTest {
        //Given
        //When
        editProductUseCase.editProduct(
            "prod_123", ProductRequestDomain(
                product = ProductTestFactory.createProductItem(
                    title = "new title",
                )
            )
        )
        //Then
        assertThat(productList.size, `is`(1))
        assertThat(productList.first().title, `is`("new title"))
    }
}