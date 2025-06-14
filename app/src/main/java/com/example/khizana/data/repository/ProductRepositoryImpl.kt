package com.example.khizana.data.repository


import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.data.repository.mapper.toDto
import com.example.khizana.domain.model.CountDomain
import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val remoteDataSourceImpl: RemoteDataSource) :
    ProductRepository {

    override suspend fun getProducts(): Flow<ProductDomain> {
        return remoteDataSourceImpl.getProducts().map { it.toDomain() }
    }

    override suspend fun createProduct(product: ProductRequestDomain): Flow<ProductRequestDomain> {
        return remoteDataSourceImpl.createProduct(product.toDto()).map { it.toDomain() }
    }

    override suspend fun deleteProduct(productId: String) {
        remoteDataSourceImpl.deleteProduct(productId)
    }

    override suspend fun getProductById(productId: String): Flow<ProductRequestDomain> {
        return remoteDataSourceImpl.getProductById(productId).map { it.toDomain() }
    }

    override suspend fun editProduct(productId: String, product: ProductRequestDomain) {
        remoteDataSourceImpl.editProduct(productId, product.toDto())
    }

    override suspend fun getProductsCount(): Flow<CountDomain> {
        return remoteDataSourceImpl.getProductsCount().map { it.toDomain() }
    }
}