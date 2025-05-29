package com.example.khizana.domain.usecase

import com.example.khizana.domain.repository.ProductRepository

class DeleteProductUseCase(private val productRepository: ProductRepository) {

    suspend fun deleteProduct(productId: String) {
        productRepository.deleteProduct(productId)
    }

}