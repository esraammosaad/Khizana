package com.example.khizana.domain.usecase

import com.example.ProductDtoTestFactory
import com.example.khizana.data.repository.FakeProductRepository
import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.domain.repository.ProductRepository
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test


class DeleteProductUseCaseTest {
    private lateinit var productRepository: ProductRepository
    private lateinit var deleteProductUseCase: DeleteProductUseCase
    private var productList =
        mutableListOf(ProductDtoTestFactory.createProductItemEntity().toDomain())

    @Before
    fun setup() {
        productRepository = FakeProductRepository(productList)
        deleteProductUseCase = DeleteProductUseCase(productRepository)
    }

    @Test
    fun deleteProduct_changeProductsState() = runTest {
        //Given
        //When
        deleteProductUseCase.deleteProduct(
            "prod_123"
        )
        //Then
        assertThat(productList.size, `is`(0))
    }
}