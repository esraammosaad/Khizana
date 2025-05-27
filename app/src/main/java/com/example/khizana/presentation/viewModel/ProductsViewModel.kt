package com.example.khizana.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.khizana.data.model.GetCollectByIdResponse
import com.example.khizana.data.model.GetCollectsResponse
import com.example.khizana.data.model.GetProductsResponse
import com.example.khizana.data.repo.RepositoryImpl
import kotlinx.coroutines.launch

class ProductsViewModel(private val repository: RepositoryImpl)  : ViewModel(){

    private var _collects : MutableLiveData<GetCollectsResponse> = MutableLiveData()
    val collects : LiveData<GetCollectsResponse> = _collects

    private var _collectDetails : MutableLiveData<GetCollectByIdResponse> = MutableLiveData()
    val collectDetails : LiveData<GetCollectByIdResponse> = _collectDetails

    private var _products : MutableLiveData<GetProductsResponse> = MutableLiveData()
    val products : LiveData<GetProductsResponse> = _products

    fun getCollects(){
        viewModelScope.launch {

            val response = repository.getCollects()
            if(response.isSuccessful){
                _collects.postValue(response.body())
                Log.i("TAG", "getCollects: ${response.body()}")
            }
        }
    }

    fun getCollectById(collectId : Long){
        viewModelScope.launch {
            val response = repository.getCollectsById(collectId)
            if(response.isSuccessful){
                _collectDetails.postValue(response.body())
                Log.i("TAG", "getCollectById: ${response.body()}")
            }
        }
    }

    fun getProducts(){
        viewModelScope.launch {
            val response = repository.getProducts()
            if(response.isSuccessful){
                _products.postValue(response.body())
                Log.i("TAG", "getProducts: ${response.body()}")
            }
        }
    }
}

class ProductsViewModelFactory(private val repository: RepositoryImpl) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductsViewModel(repository) as T
    }
}