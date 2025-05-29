package com.example.khizana.presentation.feature.products.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.usecase.CreateProductUseCase
import com.example.khizana.domain.usecase.DeleteProductUseCase
import com.example.khizana.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase
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

    fun createProduct(productRequestDomain: ProductRequestDomain) {
        viewModelScope.launch {
            createProductUseCase.createProduct(productRequestDomain)
            getProducts()
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            deleteProductUseCase.deleteProduct(productId)
            getProducts()
        }
    }
}

class ProductsViewModelFactory(
    private val getProductsUseCase: GetProductsUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductsViewModel(
            getProductsUseCase,
            createProductUseCase,
            deleteProductUseCase
        ) as T
    }
}