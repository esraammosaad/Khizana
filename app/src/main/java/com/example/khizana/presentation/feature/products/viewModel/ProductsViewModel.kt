package com.example.khizana.presentation.feature.products.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private var _products: MutableLiveData<ProductDomain> = MutableLiveData()
    val products: LiveData<ProductDomain> = _products


    fun getProducts() {
        viewModelScope.launch {
            val response = getProductsUseCase.getProducts()
            _products.postValue(response)
            Log.i("TAG", "getProducts: $response")

        }
    }
}

class ProductsViewModelFactory(
    private val getProductsUseCase: GetProductsUseCase,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductsViewModel(getProductsUseCase) as T
    }
}