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
import com.example.khizana.domain.usecase.EditProductUseCase
import com.example.khizana.domain.usecase.GetProductByIdUseCase
import com.example.khizana.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val editProductUseCase: EditProductUseCase
) : ViewModel() {

    private var _products: MutableLiveData<ProductDomain> = MutableLiveData()
    val products: LiveData<ProductDomain> = _products

    private var _product: MutableLiveData<ProductRequestDomain> = MutableLiveData()
    val product: LiveData<ProductRequestDomain> = _product


    fun getProducts() {
        viewModelScope.launch {
            val response = getProductsUseCase.getProducts()
            _products.postValue(response)
            Log.i("TAG", "getProducts: $response")

        }
    }

    fun createProduct(productRequestDomain: ProductRequestDomain) {
        viewModelScope.launch {
            val response = createProductUseCase.createProduct(productRequestDomain)
            if (response.product?.id != null) {
                getProducts()
            }
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            deleteProductUseCase.deleteProduct(productId)
            getProducts()
        }
    }

    fun getProductById(productId: String) {
        viewModelScope.launch {
            val response = getProductByIdUseCase.getProductById(productId)
            _product.postValue(response)
            Log.i("TAG", "getProductById: $response")
        }
    }

    fun editProduct(productId: String, product: ProductRequestDomain) {
        viewModelScope.launch {
            editProductUseCase.editProduct(productId, product)
            getProducts()
        }
    }
}

class ProductsViewModelFactory(
    private val getProductsUseCase: GetProductsUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val editProductUseCase: EditProductUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductsViewModel(
            getProductsUseCase,
            createProductUseCase,
            deleteProductUseCase,
            getProductByIdUseCase,
            editProductUseCase
        ) as T
    }
}