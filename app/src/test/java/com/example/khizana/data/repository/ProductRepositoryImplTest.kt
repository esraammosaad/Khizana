package com.example.khizana.data.repository

import com.example.khizana.data.datasource.remote.FakeRemoteDataSource
import com.example.ProductDtoTestFactory
import com.example.ProductRequestTestFactory
import com.example.ProductTestFactory
import com.example.khizana.domain.model.CountDomain
import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.repository.ProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test


class ProductRepositoryImplTest {

    private lateinit var productRepositoryImpl: ProductRepository
    private lateinit var remoteDataSourceImpl: RemoteDataSource
    private var productsList = mutableListOf(ProductDtoTestFactory.createProductItemEntity())


    @Before
    fun setup() {
        remoteDataSourceImpl = FakeRemoteDataSource(productsList = productsList)
        productRepositoryImpl = ProductRepositoryImpl(
            remoteDataSourceImpl = remoteDataSourceImpl
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getProducts_flowOfProducts() = runTest {
        //Given

        //When
        productRepositoryImpl.getProducts()

        //Then
        assertThat(productsList.size, `is`(1))
    }

    @Test
    fun createProduct_flowOfProductRequest() = runTest {
        //Given

        //When
        productRepositoryImpl.createProduct(ProductRequestDomain(
            product = ProductTestFactory.createProductItem(
                id = "prod_456",
                title = "new title",
            )
        ))

        //Then
        assertThat(productsList.size, `is`(2))
        assertThat(productsList.first().id, `is`("prod_123"))
        assertThat(productsList.last().id, `is`("prod_456"))
        assertThat(productsList.last().title, `is`("new title"))
    }

    @Test
    fun deleteProduct_deleteItemFromProductList() = runTest {
        //Given

        //When
        productRepositoryImpl.deleteProduct("prod_123")

        //Then
        assertThat(productsList.size, `is`(0))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getProductById_flowOfProductRequest() = runTest {
        //Given
        val values = mutableListOf<ProductRequestDomain>()

        //When
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            productRepositoryImpl.getProductById("prod_123").toList(
                values
            )
        }
        advanceUntilIdle()

        //Then
        assertThat(values.size, `is`(1))
        assertThat(values.first().product?.id, `is`("prod_123"))
    }


    @Test
    fun editProduct_editItemInProductList() = runTest {
        //Given

        //When
        productRepositoryImpl.editProduct(
            "prod_123", ProductRequestTestFactory.createProductRequest(
                ProductTestFactory.createProductItem(
                    title = "new title",
                )
            )
        )

        //Then
        assertThat(productsList.size, `is`(1))
        assertThat(productsList.first().title, `is`("new title"))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getProductsCount_flowOfCount() = runTest {
        //Given
        val values = mutableListOf<CountDomain>()

        //When
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            productRepositoryImpl.getProductsCount().toList(
                values
            )
        }
        advanceUntilIdle()

        //Then
        assertThat(values.size, `is`(1))
    }
}