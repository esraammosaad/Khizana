package com.example.khizana.presentation.feature.home.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.khizana.data.dto.Product
import com.example.khizana.data.repository.ProductRepositoryImpl
import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.launch

class ProductsViewModel(private val getProductsUseCase: GetProductsUseCase)  : ViewModel(){

//    private var _collects : MutableLiveData<GetCollectsResponse> = MutableLiveData()
//    val collects : LiveData<GetCollectsResponse> = _collects
//
//    private var _collectDetails : MutableLiveData<GetCollectByIdResponse> = MutableLiveData()
//    val collectDetails : LiveData<GetCollectByIdResponse> = _collectDetails

    private var _products : MutableLiveData<ProductDomain> = MutableLiveData()
    val products : LiveData<ProductDomain> = _products

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

    fun getProducts(){
        viewModelScope.launch {
            val response = getProductsUseCase.getProducts()

                _products.postValue(response)
                Log.i("TAG", "getProducts: $response")

        }
    }
}

class ProductsViewModelFactory(private val getProductsUseCase: GetProductsUseCase) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductsViewModel(getProductsUseCase) as T
    }
}