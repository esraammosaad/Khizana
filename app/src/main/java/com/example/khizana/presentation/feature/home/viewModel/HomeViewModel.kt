package com.example.khizana.presentation.feature.home.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khizana.domain.model.OrdersCountDomain
import com.example.khizana.domain.usecase.GetOrdersUseCase
import com.example.khizana.domain.usecase.GetProductsUseCase
import com.example.khizana.utilis.Response
import com.example.khizana.utilis.Strings
import com.example.khizana.utilis.getShopifyOrderCountDatesForLastSevenDays
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getOrdersUseCase: GetOrdersUseCase
) : ViewModel() {

    private var _products = MutableStateFlow<Response>(Response.Loading)
    val products = _products.asStateFlow()

    private var _orders = MutableStateFlow<Response>(Response.Loading)
    val orders = _orders.asStateFlow()

    private var _totalOrdersPrice = MutableStateFlow<Response>(Response.Loading)
    val totalOrdersPrice = _totalOrdersPrice.asStateFlow()

    private var _ordersCount = MutableStateFlow<Response>(Response.Loading)
    val ordersCount = _ordersCount.asStateFlow()

    fun getProducts() {
        viewModelScope.launch {
            try {
                val response = getProductsUseCase.getProducts()
                response.catch {
                    _products.emit(Response.Failure(it.message.toString()))
                }.collect {
                    _products.emit(Response.Success(it))
                }
            } catch (e: Exception) {
                _products.emit(Response.Failure(e.message.toString()))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getOrders() {
        viewModelScope.launch {
            try {
                val list = mutableListOf<Float>()
                val response = getOrdersUseCase.getOrders(
                    getShopifyOrderCountDatesForLastSevenDays()[0] + Strings.START_OF_THE_DAY,
                    getShopifyOrderCountDatesForLastSevenDays()[6] + Strings.END_OF_THE_DAY
                )
                response.catch {
                    _orders.emit(Response.Failure(it.message.toString()))
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
                    _orders.emit(Response.Success(it))
                    _totalOrdersPrice.emit(Response.Success(list.sum()))
                    Log.i("TAG", "getOrders: $response")
                }
            } catch (e: Exception) {
                _orders.emit(Response.Failure(e.message.toString()))
                _totalOrdersPrice.emit(Response.Failure(e.message.toString()))
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getOrdersCount() {
        viewModelScope.launch {
            try {
                val list = mutableListOf<OrdersCountDomain>()
                for (i in 0..6) {
                    val response = getOrdersUseCase.getOrdersCount(
                        minDate = getShopifyOrderCountDatesForLastSevenDays()[i] + Strings.START_OF_THE_DAY,
                        maxDate = getShopifyOrderCountDatesForLastSevenDays()[i] + Strings.END_OF_THE_DAY
                    )
                    response.catch {
                        _ordersCount.emit(Response.Failure(it.message.toString()))
                    }.collect {
                        list.add(it)
                        Log.i("TAG", "getOrdersCount: $response")
                    }
                }
                _ordersCount.emit(Response.Success(list))
            } catch (e: Exception) {
                _ordersCount.emit(Response.Failure(e.message.toString()))

            }
        }

    }
}

