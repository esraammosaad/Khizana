package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateProductUseCase  @Inject constructor(private val productRepository: ProductRepository) {
    suspend fun createProduct(productRequestDomain: ProductRequestDomain) : Flow<ProductRequestDomain> {
        return productRepository.createProduct(productRequestDomain)
    }
}