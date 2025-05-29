package com.example.khizana.domain.repository

import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.domain.model.ProductRequestDomain

interface ProductRepository {

    suspend fun getProducts() : ProductDomain
    suspend fun createProduct(productRequestDomain: ProductRequestDomain)
    suspend fun deleteProduct(productId : String)

}