package com.example.khizana.data.repository


import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.domain.repository.ProductRepository

class ProductRepositoryImpl(private val remoteDataSourceImpl: RemoteDataSource) : ProductRepository{

//    suspend fun getCollects() : Response<GetCollectsResponse> {
//        return remoteDataSourceImpl.getCollects()
//    }
//
//    suspend fun getCollectsById(collectsId : Long) : Response<GetCollectByIdResponse> {
//        return remoteDataSourceImpl.getCollectsById(collectsId)
//    }

    override suspend fun getProducts() : ProductDomain {
        return remoteDataSourceImpl.getProducts().toDomain()
    }

}