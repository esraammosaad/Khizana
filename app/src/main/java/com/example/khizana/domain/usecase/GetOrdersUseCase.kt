package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.OrderDomain
import com.example.khizana.domain.model.CountDomain
import com.example.khizana.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    suspend fun getOrders(minDate: String, maxDate: String): Flow<OrderDomain> {
        return orderRepository.getOrders(minDate, maxDate)
    }

    suspend fun getOrdersCount(minDate: String, maxDate: String): Flow<CountDomain> {
        return orderRepository.getOrdersCount(minDate, maxDate)
    }
}