package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend fun getProductById(productId: String): Flow<ProductRequestDomain> {
        return productRepository.getProductById(productId)
    }
}