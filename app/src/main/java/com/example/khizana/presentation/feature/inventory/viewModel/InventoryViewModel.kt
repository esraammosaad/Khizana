package com.example.khizana.presentation.feature.inventory.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khizana.domain.model.InventoryItemRequestDomain
import com.example.khizana.domain.model.InventoryLevelRequestDomain
import com.example.khizana.domain.usecase.GetInventoryItemUseCase
import com.example.khizana.domain.usecase.GetProductsUseCase
import com.example.khizana.domain.usecase.SetInventoryItemQuantityUseCase
import com.example.khizana.domain.usecase.UpdateInventoryItemUseCase
import com.example.khizana.utilis.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val setInventoryItemQuantityUseCase: SetInventoryItemQuantityUseCase,
    private val updateInventoryItemUseCase: UpdateInventoryItemUseCase,
    private val getInventoryItemUseCase: GetInventoryItemUseCase
) : ViewModel() {

    private var _products = MutableStateFlow<Response>(Response.Loading)
    val products = _products.asStateFlow()

    private var _inventoryItem = MutableStateFlow<Response>(Response.Loading)
    val inventoryItem = _inventoryItem.asStateFlow()

    private var _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()

    fun getProducts() {
        viewModelScope.launch {
            try {
                val response = getProductsUseCase.getProducts()
                response.catch {
                    _products.emit(Response.Failure(it.message.toString()))
                    _message.emit(it.message.toString())
                }
                    .collect {
                        _products.emit(Response.Success(it))
                    }
            } catch (e: Exception) {
                _products.emit(Response.Failure(e.message.toString()))
                _message.emit(e.message.toString())
            }
        }
    }

    fun getInventoryItem(inventoryItemId: String) {
        viewModelScope.launch {
            try {
                val response = getInventoryItemUseCase.getInventoryItem(inventoryItemId)
                response.catch {
                    _inventoryItem.emit(Response.Failure(it.message.toString()))
                    _message.emit(it.message.toString())
                }
                    .collect {
                        _inventoryItem.emit(Response.Success(it))
                    }
            } catch (e: Exception) {
                _inventoryItem.emit(Response.Failure(e.message.toString()))
                _message.emit(e.message.toString())
            }
        }

    }

    fun updateInventoryItemWithQuantity(
        inventoryLevelRequestDomain: InventoryLevelRequestDomain,
        inventoryItemRequestDomain: InventoryItemRequestDomain,
    ) {
        viewModelScope.launch {
            try {
                updateInventoryItemUseCase.updateInventoryItem(
                    inventoryItemRequestDomain,
                    inventoryLevelRequestDomain.inventory_item_id
                )
                setInventoryItemQuantityUseCase.setInventoryItemQuantity(inventoryLevelRequestDomain)
                delay(5000)
                getProducts()
                _message.emit("Inventory item updated successfully")
            } catch (e: Exception) {
                _message.emit(e.message.toString())
            }

        }
    }
}