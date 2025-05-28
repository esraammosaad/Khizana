package com.example.khizana.data.repository

import com.example.khizana.data.dto.OrdersCount
import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.domain.model.OrderDomain
import com.example.khizana.domain.model.OrdersCountDomain
import com.example.khizana.domain.repository.OrderRepository

class OrderRepositoryImpl(private val remoteDataSourceImpl: RemoteDataSource) : OrderRepository {

    override suspend fun getOrders(): OrderDomain {
        return remoteDataSourceImpl.getOrders().toDomain()
    }

    override suspend fun getOrdersCount(minDate: String, maxDate: String): OrdersCountDomain {
        return remoteDataSourceImpl.getOrdersCount(minDate, maxDate).toDomain()
    }


}