package com.example.khizana.data.repository

import com.example.khizana.data.repository.mapper.toDomain
import com.example.khizana.domain.model.OrderDomain
import com.example.khizana.domain.model.CountDomain
import com.example.khizana.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val remoteDataSourceImpl: RemoteDataSource) : OrderRepository {

    override suspend fun getOrders(minDate: String, maxDate: String): Flow<OrderDomain> {
        return remoteDataSourceImpl.getOrders(minDate, maxDate).map { it.toDomain() }
    }

    override suspend fun getOrdersCount(minDate: String, maxDate: String): Flow<CountDomain> {
        return remoteDataSourceImpl.getOrdersCount(minDate, maxDate).map { it.toDomain() }
    }
}