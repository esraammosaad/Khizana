package com.example.khizana.utilis.internet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InternetConnectivityViewModel @Inject constructor(private val internetObserver: InternetObserver) : ViewModel() {

    private val _isConnected = MutableStateFlow(false)
    val isConnected = _isConnected.asStateFlow()

    fun getInternetConnectivity() {
        viewModelScope.launch {
            internetObserver.isConnected.collect {
                _isConnected.emit(it)

            }
        }
    }
}
