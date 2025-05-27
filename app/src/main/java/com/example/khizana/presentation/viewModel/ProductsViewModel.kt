package com.example.khizana.presentation.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.khizana.data.model.GetCollectsResponse
import com.example.khizana.data.repo.RepositoryImpl
import kotlinx.coroutines.launch

class ProductsViewModel(private val repository: RepositoryImpl)  : ViewModel(){

    private var _collects : MutableLiveData<GetCollectsResponse> = MutableLiveData()
    val collects : LiveData<GetCollectsResponse> = _collects

    fun getCollects(){

        viewModelScope.launch {

            val response = repository.getCollects()
            if(response.isSuccessful){

                _collects.postValue(response.body())
                Log.i("TAG", "getCollects: ${response.body()}")

            }



        }

    }
}

class ProductsViewModelFactory(private val repository: RepositoryImpl) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductsViewModel(repository) as T
    }
}