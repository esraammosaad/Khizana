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

    override suspend fun createProduct(product: ProductRequestDomain) {
        remoteDataSourceImpl.createProduct(product.toDto())
    }

    override suspend fun deleteProduct(productId : String) {
        remoteDataSourceImpl.deleteProduct(productId)
    }

    override suspend fun getProductById(productId : String) : ProductRequestDomain {
        return remoteDataSourceImpl.getProductById(productId).toDomain()
    }

    override suspend fun editProduct(productId : String, product : ProductRequestDomain) {
        remoteDataSourceImpl.editProduct(productId, product.toDto())
    }

}