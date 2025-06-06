package com.example.khizana.domain.repository

import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.domain.model.ProductRequestDomain
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun getProducts(): Flow<ProductDomain>
    suspend fun createProduct(product: ProductRequestDomain) : Flow<ProductRequestDomain>
    suspend fun deleteProduct(productId: String)
    suspend fun getProductById(productId: String): Flow<ProductRequestDomain>
    suspend fun editProduct(productId: String, product: ProductRequestDomain)
}