package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(private val productRepository: ProductRepository) {
    suspend fun getProducts(): Flow<ProductDomain> {
        return productRepository.getProducts()
    }
}