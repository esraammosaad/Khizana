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
import com.example.khizana.utilis.getShopifyOrderCountDatesForLastSevenDays
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val getOrdersUseCase: GetOrdersUseCase
) : ViewModel() {

//    private var _collects : MutableLiveData<GetCollectsResponse> = MutableLiveData()
//    val collects : LiveData<GetCollectsResponse> = _collects
//
//    private var _collectDetails : MutableLiveData<GetCollectByIdResponse> = MutableLiveData()
//    val collectDetails : LiveData<GetCollectByIdResponse> = _collectDetails

    private var _products: MutableLiveData<ProductDomain> = MutableLiveData()
    val products: LiveData<ProductDomain> = _products

    private var _orders: MutableLiveData<OrderDomain> = MutableLiveData()
    val orders: LiveData<OrderDomain> = _orders

    private var _ordersCount: MutableLiveData<List<OrdersCountDomain>> = MutableLiveData()
    val ordersCount: LiveData<List<OrdersCountDomain>> = _ordersCount

//    fun getCollects(){
//        viewModelScope.launch {
//
//            val response = repository.getCollects()
//            if(response.isSuccessful){
//                _collects.postValue(response.body())
//                Log.i("TAG", "getCollects: ${response.body()}")
//            }
//        }
//    }
//
//    fun getCollectById(collectId : Long){
//        viewModelScope.launch {
//            val response = repository.getCollectsById(collectId)
//            if(response.isSuccessful){
//                _collectDetails.postValue(response.body())
//                Log.i("TAG", "getCollectById: ${response.body()}")
//            }
//        }
//    }

    fun getProducts() {
        viewModelScope.launch {
            val response = getProductsUseCase.getProducts()
            _products.postValue(response)
            Log.i("TAG", "getProducts: $response")

        }
    }

    fun getOrders() {
        viewModelScope.launch {
            val response = getOrdersUseCase.getOrders()
            _orders.postValue(response)
            Log.i("TAG", "getOrders: $response")

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getOrdersCount() {
        viewModelScope.launch {

            val list = mutableListOf<OrdersCountDomain>()

            for (i in 0..6) {
                val response = getOrdersUseCase.getOrdersCount(
                    minDate = getShopifyOrderCountDatesForLastSevenDays()[i] + "T00:00:00Z",
                    maxDate = getShopifyOrderCountDatesForLastSevenDays()[i] + "T23:59:59Z"
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