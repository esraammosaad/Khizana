package com.example.khizana.domain.usecase

import com.example.khizana.domain.model.OrderDomain
import com.example.khizana.domain.model.OrdersCountDomain
import com.example.khizana.domain.repository.OrderRepository

class GetOrdersUseCase (private val orderRepository: OrderRepository) {

    suspend fun getOrders() : OrderDomain {
        return orderRepository.getOrders()
    }

    suspend fun getOrdersCount(minDate: String, maxDate: String) : OrdersCountDomain {
        return orderRepository.getOrdersCount(minDate, maxDate)
    }


}