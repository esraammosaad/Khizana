package com.example.khizana.presentation.feature.home.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.khizana.domain.model.OrderDomain
import com.example.khizana.domain.model.OrdersCountDomain
import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.domain.usecase.GetOrdersUseCase
import com.example.khizana.domain.usecase.GetProductsUseCase
import com.example.khizana.utilis.Strings
import com.example.khizana.utilis.getShopifyOrderCountDatesForLastSevenDays
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val getOrdersUseCase: GetOrdersUseCase
) : ViewModel() {

    private var _products: MutableLiveData<ProductDomain> = MutableLiveData()
    val products: LiveData<ProductDomain> = _products

    private var _orders: MutableLiveData<OrderDomain> = MutableLiveData()
    val orders: LiveData<OrderDomain> = _orders

    private var _totalOrdersPrice: MutableLiveData<Float> = MutableLiveData()
    val totalOrdersPrice: LiveData<Float> = _totalOrdersPrice

    private var _ordersCount: MutableLiveData<List<OrdersCountDomain>> = MutableLiveData()
    val ordersCount: LiveData<List<OrdersCountDomain>> = _ordersCount

    fun getProducts() {
        viewModelScope.launch {
            val response = getProductsUseCase.getProducts()
            _products.postValue(response)
            Log.i("TAG", "getProducts: $response")

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getOrders() {
        viewModelScope.launch {
            val list = mutableListOf<Float>()
            val response = getOrdersUseCase.getOrders(getShopifyOrderCountDatesForLastSevenDays()[0]+Strings.START_OF_THE_DAY, getShopifyOrderCountDatesForLastSevenDays()[6]+Strings.END_OF_THE_DAY)

            response.orders?.forEach { order ->
                val amount = order?.current_total_price_set?.shop_money?.amount?.toFloatOrNull() ?: 0f
                val currency = order?.current_total_price_set?.shop_money?.currency_code

                if (currency == "EUR") {
                    list.add(amount * 50f)
                } else {
                    list.add(amount)
                }
            }

            _orders.postValue(response)
            _totalOrdersPrice.postValue(list.sum())
            Log.i("TAG", "getOrders: $response")
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getOrdersCount() {
        viewModelScope.launch {
            val list = mutableListOf<OrdersCountDomain>()
            for (i in 0..6) {
                val response = getOrdersUseCase.getOrdersCount(
                    minDate = getShopifyOrderCountDatesForLastSevenDays()[i] + Strings.START_OF_THE_DAY,
                    maxDate = getShopifyOrderCountDatesForLastSevenDays()[i] + Strings.END_OF_THE_DAY
                )
                list.add(response)
                Log.i("TAG", "getOrdersCount: $response")
            }
            _ordersCount.postValue(list)
        }
    }
}

class HomeViewModelFactory(
    private val getProductsUseCase: GetProductsUseCase,
    private val getOrdersUseCase: GetOrdersUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(getProductsUseCase, getOrdersUseCase) as T
    }
}