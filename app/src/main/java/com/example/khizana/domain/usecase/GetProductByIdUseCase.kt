package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.repository.ProductRepository

class GetProductByIdUseCase(private val productRepository: ProductRepository) {
    suspend fun getProductById(productId: String): ProductRequestDomain {
        return productRepository.getProductById(productId)
    }
}