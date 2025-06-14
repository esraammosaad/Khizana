package com.example.khizana.domain.usecase

import com.example.khizana.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteProductUseCase (private val productRepository: ProductRepository) {
    suspend fun deleteProduct(productId: String) {
        productRepository.deleteProduct(productId)
    }
}