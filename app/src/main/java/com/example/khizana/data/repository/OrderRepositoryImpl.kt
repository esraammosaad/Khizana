package com.example.khizana.data.repository

import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.domain.model.OrderDomain
import com.example.khizana.domain.repository.OrderRepository

class OrderRepositoryImpl(private val remoteDataSourceImpl: RemoteDataSource) : OrderRepository{

    override suspend fun getOrders() : OrderDomain {
        return remoteDataSourceImpl.getOrders().toDomain()
    }


}