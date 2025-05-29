package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.domain.repository.ProductRepository

class GetProductsUseCase(private val productRepository: ProductRepository) {

    suspend fun getProducts() : ProductDomain {
        return productRepository.getProducts()
    }

}