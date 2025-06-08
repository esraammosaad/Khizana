package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.CountDomain
import com.example.khizana.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductsCountUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend fun getProductsCount(): Flow<CountDomain> {
        return productRepository.getProductsCount()
    }
}