package com.example.khizana.domain.repository

import com.example.khizana.domain.model.ProductDomain

interface ProductRepository {

    suspend fun getProducts() : ProductDomain

}