package com.example

import com.example.khizana.domain.model.CountDomain
import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.model.ProductsItem
import com.example.khizana.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeProductRepository(private val productsList: MutableList<ProductsItem>) :
    ProductRepository {
    override suspend fun getProducts(): Flow<ProductDomain> {
        return flowOf(ProductTestFactory.createProductDomain(productsList))
    }

    override suspend fun createProduct(product: ProductRequestDomain): Flow<ProductRequestDomain> {
        productsList.add(ProductTestFactory.createProductItem(
            id = product.product?.id ?: "prod_123",
            title = product.product?.title ?: "Product 1",
        ))
        return flowOf(product)
    }

    override suspend fun deleteProduct(productId: String) {
        productsList.removeIf { it.id == productId }
    }

    override suspend fun getProductById(productId: String): Flow<ProductRequestDomain> {
        return flowOf(ProductRequestDomain(product = productsList.find { it.id == productId }))
    }

    override suspend fun editProduct(productId: String, product: ProductRequestDomain) {
        val index = productsList.indexOfFirst { it.id == productId }
        if (index != -1) {
            productsList[index] = product.product!!
        }
    }

    override suspend fun getProductsCount(): Flow<CountDomain> {
        return flowOf(CountDomain(count = productsList.size))
    }
}

