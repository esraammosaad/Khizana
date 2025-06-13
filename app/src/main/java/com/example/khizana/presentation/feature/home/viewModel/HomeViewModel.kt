package com.example.khizana.presentation.feature.home.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khizana.domain.model.CountDomain
import com.example.khizana.domain.usecase.GetInventoryLocationsUseCase
import com.example.khizana.domain.usecase.GetOrdersUseCase
import com.example.khizana.domain.usecase.GetProductsCountUseCase
import com.example.khizana.utilis.Response
import com.example.khizana.utilis.getShopifyOrderCountDatesForLastSevenDays
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val getProductsCountUseCase: GetProductsCountUseCase,
    private val getInventoryLocationsUseCase: GetInventoryLocationsUseCase
) : ViewModel() {

    private var _products = MutableStateFlow<Response>(Response.Loading)
    val products = _products.asStateFlow()

    private var _totalOrdersPrice = MutableStateFlow<Response>(Response.Loading)
    val totalOrdersPrice = _totalOrdersPrice.asStateFlow()

    private var _revenue = MutableStateFlow<Response>(Response.Loading)
    val revenue = _revenue.asStateFlow()

    private var _ordersCount = MutableStateFlow<Response>(Response.Loading)
    val ordersCount = _ordersCount.asStateFlow()

    private var _productsCount = MutableStateFlow<Response>(Response.Loading)
    val productsCount = _productsCount.asStateFlow()

    private var _inventoryLocationsCount = MutableStateFlow<Response>(Response.Loading)
    val inventoryLocationsCount = _inventoryLocationsCount.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTotalOrdersPrice() {
        viewModelScope.launch {
            try {
                val list = mutableListOf<Float>()
                val response = getOrdersUseCase.getOrders(
                    getShopifyOrderCountDatesForLastSevenDays()[0] + Strings.START_OF_THE_DAY,
                    getShopifyOrderCountDatesForLastSevenDays()[6] + Strings.END_OF_THE_DAY
                )
                response.catch {
                    _totalOrdersPrice.emit(Response.Failure(it.message.toString()))
                }.collect {
                    it.orders?.forEach { order ->
                        val amount =
                            order?.current_total_price_set?.shop_money?.amount?.toFloatOrNull()
                                ?: 0f
                        val currency = order?.current_total_price_set?.shop_money?.currency_code
                        if (currency == "EUR") {
                            list.add(amount * 50f)
                        } else {
                            list.add(amount)
                        }
                    }
                    _totalOrdersPrice.emit(Response.Success(list.sum()))
                }
            } catch (e: Exception) {
                _totalOrdersPrice.emit(Response.Failure(e.message.toString()))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTotalRevenue() {
        viewModelScope.launch {
            try {
                val revenueList = mutableListOf<Double>()
                val response = getOrdersUseCase.getOrders(
                    getShopifyOrderCountDatesForLastSevenDays()[0] + Strings.START_OF_THE_DAY,
                    getShopifyOrderCountDatesForLastSevenDays()[6] + Strings.END_OF_THE_DAY,
                )
                response
                    .catch {
                        _revenue.emit(Response.Failure(it.message.toString()))
                    }
                    .collect {
                        it.orders?.forEach { order ->
                            val subtotal = order?.subtotal_price?.toDoubleOrNull() ?: 0.0
                            val discounts = order?.total_discounts?.toDoubleOrNull() ?: 0.0

                            val revenuePerOrder = subtotal - discounts
                            val currency = order?.currency
                            if (currency == "EUR") {
                                revenueList.add(revenuePerOrder * 50.0)
                            } else {
                                revenueList.add(revenuePerOrder)
                            }
                        }
                        _revenue.emit(Response.Success(revenueList.sum()))
                    }

            } catch (e: Exception) {
                _revenue.emit(Response.Failure(e.message.toString()))
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getOrdersCount() {
        viewModelScope.launch {
            try {
                val list = mutableListOf<CountDomain>()
                for (i in 0..6) {
                    val response = getOrdersUseCase.getOrdersCount(
                        minDate = getShopifyOrderCountDatesForLastSevenDays()[i] + Strings.START_OF_THE_DAY,
                        maxDate = getShopifyOrderCountDatesForLastSevenDays()[i] + Strings.END_OF_THE_DAY
                    )
                    response.catch {
                        _ordersCount.emit(Response.Failure(it.message.toString()))
                    }.collect {
                        list.add(it)
                    }
                }
                _ordersCount.emit(Response.Success(list))
            } catch (e: Exception) {
                _ordersCount.emit(Response.Failure(e.message.toString()))
            }
        }
    }

    fun getProductsCount() {
        viewModelScope.launch {
            try {
                val response = getProductsCountUseCase.getProductsCount()
                response.catch {
                    _productsCount.emit(Response.Failure(it.message.toString()))
                }.collect {
                    _productsCount.emit(Response.Success(it.count))
                }
            } catch (e: Exception) {
                _productsCount.emit(Response.Failure(e.message.toString()))

            }
        }
    }

    fun getInventoryLocationsCount() {
        viewModelScope.launch {
            try {
                val response = getInventoryLocationsUseCase.getAllInventoryLocations()
                response.catch {
                    _inventoryLocationsCount.emit(Response.Failure(it.message.toString()))
                }.collect {
                    _inventoryLocationsCount.emit(Response.Success(it.locations.size))
                }
            } catch (e: Exception) {
                _inventoryLocationsCount.emit(Response.Failure(e.message.toString()))
            }
        }
    }
}

object Strings {
    const val START_OF_THE_DAY = "T00:00:00Z"
    const val END_OF_THE_DAY = "T23:59:59Z"
}

