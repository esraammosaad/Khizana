package com.example.khizana.presentation.feature.products.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.khizana.data.repository.FakeProductRepository
import com.example.ProductTestFactory
import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.repository.ProductRepository
import com.example.khizana.domain.usecase.CreateProductUseCase
import com.example.khizana.domain.usecase.DeleteProductUseCase
import com.example.khizana.domain.usecase.EditProductUseCase
import com.example.khizana.domain.usecase.GetProductByIdUseCase
import com.example.khizana.domain.usecase.GetProductsUseCase
import com.example.khizana.utilis.Response
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
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductsViewModelTest {

    private lateinit var productsViewModel: ProductsViewModel
    private lateinit var productRepository: ProductRepository
    private lateinit var getProductsUseCase: GetProductsUseCase
    private lateinit var createProductUseCase: CreateProductUseCase
    private lateinit var deleteProductUseCase: DeleteProductUseCase
    private lateinit var getProductByIdUseCase: GetProductByIdUseCase
    private lateinit var editProductUseCase: EditProductUseCase
    private var productsList = mutableListOf(ProductTestFactory.createProductItem())


    @get:Rule
    val myRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        productRepository = FakeProductRepository(productsList = productsList)
        getProductsUseCase = GetProductsUseCase(productRepository)
        createProductUseCase = CreateProductUseCase(productRepository)
        deleteProductUseCase = DeleteProductUseCase(productRepository)
        getProductByIdUseCase = GetProductByIdUseCase(productRepository)
        editProductUseCase = EditProductUseCase(productRepository)
        productsViewModel = ProductsViewModel(
            getProductsUseCase = getProductsUseCase,
            createProductUseCase = createProductUseCase,
            deleteProductUseCase = deleteProductUseCase,
            getProductByIdUseCase = getProductByIdUseCase,
            editProductUseCase = editProductUseCase
        )
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getProducts_changeProductsState() = runTest {
        //Given
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            productsViewModel.products.toList(
                values
            )
        }

        //When
        productsViewModel.getProducts()
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<ProductDomain>
        assertThat(result.result?.products?.size, `is`(1))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun createProduct_changeProductsState() = runTest {
        //Given
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            productsViewModel.products.toList(
                values
            )
        }

        //When
        productsViewModel.createProduct(
            ProductRequestDomain(
                product = ProductTestFactory.createProductItem()
            )
        )
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<ProductDomain>
        assertThat(result.result?.products?.size, `is`(2))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun deleteProduct_changeProductsState() = runTest {
        //Given
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            productsViewModel.products.toList(
                values
            )
        }

        //When
        productsViewModel.deleteProduct("sample prod id")
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<ProductDomain>
        assertThat(result.result?.products?.size, `is`(0))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getProductById_changeProductState() = runTest {
        //Given
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            productsViewModel.product.toList(
                values
            )
        }

        //When
        productsViewModel.getProductById("sample prod id")
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<ProductRequestDomain>
        assertThat(result.result?.product?.id, `is`("sample prod id"))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun editProduct_changeProductsState() = runTest {
        //Given
        val values = mutableListOf<Response>()
        backgroundScope.launch((UnconfinedTestDispatcher(testScheduler))) {
            productsViewModel.product.toList(
                values
            )
        }

        //When
        productsViewModel.editProduct(
            "sample prod id", ProductRequestDomain(
                product = ProductTestFactory.createProductItem(
                    title = "new title",
                )
            )
        )
        advanceUntilIdle()

        //Then
        assertThat(values.first(), `is`(Response.Loading))
        assertThat(values.last(), `is`(instanceOf(Response.Success::class.java)))
        val result = values.last() as Response.Success<ProductRequestDomain>
        assertThat(result.result?.product?.title, `is`("new title"))
    }
}


