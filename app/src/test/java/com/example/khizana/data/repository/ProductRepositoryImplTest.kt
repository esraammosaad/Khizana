package com.example.khizana.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.FakeRemoteDataSource
import com.example.ProductDtoTestFactory
import com.example.ProductRequestDtoTestFactory
import com.example.khizana.domain.model.CountDomain
import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ProductRepositoryImplTest {

    private lateinit var productRepositoryImpl: ProductRepository
    private lateinit var remoteDataSourceImpl: RemoteDataSource
    private var productsList = mutableListOf(ProductDtoTestFactory.createProductItemEntity())


    @get:Rule
    val myRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        remoteDataSourceImpl = FakeRemoteDataSource(productsList)
        productRepositoryImpl = ProductRepositoryImpl(
            remoteDataSourceImpl = remoteDataSourceImpl
        )
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getProducts_flowOfProducts() = runTest {
        //Given

        //When
        remoteDataSourceImpl.getProducts()

        //Then
        assertThat(productsList.size, `is`(1))
    }

    @Test
    fun createProduct_flowOfProductRequest() = runTest {
        //Given

        //When
        remoteDataSourceImpl.createProduct(ProductRequestDtoTestFactory.createProductRequest())

        //Then
        assertThat(productsList.size, `is`(2))
    }

    @Test
    fun deleteProduct_deleteItemFromProductList() = runTest {
        //Given

        //When
        remoteDataSourceImpl.deleteProduct("prod_123")

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
        remoteDataSourceImpl.editProduct(
            "prod_123", ProductRequestDtoTestFactory.createProductRequest(
                ProductDtoTestFactory.createProductItemEntity(
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