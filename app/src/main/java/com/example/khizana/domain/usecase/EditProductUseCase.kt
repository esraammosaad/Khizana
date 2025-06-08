package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.repository.ProductRepository
import javax.inject.Inject

class EditProductUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend fun editProduct(productId: String, product: ProductRequestDomain) {
        productRepository.editProduct(productId, product)
    }
}