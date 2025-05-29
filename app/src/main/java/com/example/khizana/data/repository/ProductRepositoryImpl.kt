package com.example.khizana.data.repository


import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.data.repository.mapper.toDto
import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.repository.ProductRepository

class ProductRepositoryImpl(private val remoteDataSourceImpl: RemoteDataSource) : ProductRepository{

    override suspend fun getProducts() : ProductDomain {
        return remoteDataSourceImpl.getProducts().toDomain()
    }

    override suspend fun createProduct(productRequestDomain: ProductRequestDomain) {
        remoteDataSourceImpl.createProduct(productRequestDomain.toDto())
    }

    override suspend fun deleteProduct(productId : String) {
        remoteDataSourceImpl.deleteProduct(productId)
    }

}