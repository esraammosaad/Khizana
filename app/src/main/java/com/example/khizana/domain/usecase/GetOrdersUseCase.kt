package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.OrderDomain
import com.example.khizana.domain.model.OrdersCountDomain
import com.example.khizana.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow

class GetOrdersUseCase(private val orderRepository: OrderRepository) {
    suspend fun getOrders(minDate: String, maxDate: String): Flow<OrderDomain> {
        return orderRepository.getOrders(minDate, maxDate)
    }

    suspend fun getOrdersCount(minDate: String, maxDate: String): Flow<OrdersCountDomain> {
        return orderRepository.getOrdersCount(minDate, maxDate)
    }
}