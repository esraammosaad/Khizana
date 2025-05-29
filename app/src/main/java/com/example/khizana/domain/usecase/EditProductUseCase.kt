package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.repository.ProductRepository

class EditProductUseCase(private val productRepository: ProductRepository) {
    suspend fun editProduct(productId: String, product: ProductRequestDomain) {
        productRepository.editProduct(productId, product)
    }
}