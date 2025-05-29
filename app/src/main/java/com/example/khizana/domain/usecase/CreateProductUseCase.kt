package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.repository.ProductRepository

class CreateProductUseCase (private val productRepository: ProductRepository) {
    suspend fun createProduct(productRequestDomain: ProductRequestDomain)  {
         productRepository.createProduct(productRequestDomain)
    }
}