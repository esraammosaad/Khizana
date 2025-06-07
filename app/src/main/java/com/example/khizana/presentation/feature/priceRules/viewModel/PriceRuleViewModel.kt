package com.example.khizana.presentation.feature.priceRules.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.khizana.domain.model.PriceRuleRequestDomain
import com.example.khizana.domain.usecase.CreatePriceRuleUseCase
import com.example.khizana.domain.usecase.DeletePriceRuleUseCase
import com.example.khizana.domain.usecase.EditPriceRuleUseCase
import com.example.khizana.domain.usecase.GetAllPriceRulesUseCase
import com.example.khizana.utilis.Response
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class PriceRuleViewModel(
    private val getAllPriceRulesUseCase: GetAllPriceRulesUseCase,
    private val createPriceRuleUseCase: CreatePriceRuleUseCase,
    private val editPriceRuleUseCase: EditPriceRuleUseCase,
    private val deletePriceRuleUseCase: DeletePriceRuleUseCase
) :
    ViewModel() {

    private var _priceRules = MutableStateFlow<Response>(Response.Loading)
    val priceRules = _priceRules.asStateFlow()

    private var _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()


    fun getAllPriceRules() {
        viewModelScope.launch {
            try {
                val response = getAllPriceRulesUseCase.getAllPriceRules()
                response.catch {
                    _priceRules.emit(Response.Failure(it.message.toString()))
                    _message.emit(it.message.toString())
                }.collect {
                    _priceRules.emit(Response.Success(it))
                }
            } catch (e: Exception) {
                _priceRules.emit(Response.Failure(e.message.toString()))
                _message.emit(e.message.toString())
            }
        }
    }

    fun createPriceRule(priceRule: PriceRuleRequestDomain) {
        viewModelScope.launch {
            try {
                createPriceRuleUseCase.createPriceRule(priceRule)
                getAllPriceRules()
                _message.emit("Price rule created successfully")
            } catch (e: Exception) {
                _message.emit(e.message.toString())
            }
        }
    }

    fun editPriceRule(priceRuleId: String, priceRule: PriceRuleRequestDomain) {
        viewModelScope.launch {
            try {
                editPriceRuleUseCase.editPriceRule(priceRuleId, priceRule)
                getAllPriceRules()
                _message.emit("Price rule edited successfully")
            } catch (e: Exception) {
                _message.emit(e.message.toString())
            }
        }
    }

    fun deletePriceRule(priceRuleId: String) {
        viewModelScope.launch {
            try {
                deletePriceRuleUseCase.deletePriceRule(priceRuleId)
                getAllPriceRules()
                _message.emit("Price rule deleted successfully")
            } catch (e: Exception) {
                _message.emit(e.message.toString())
            }


        }
    }
}

class PriceRuleViewModelFactory(
    private val getAllPriceRulesUseCase: GetAllPriceRulesUseCase,
    private val createPriceRuleUseCase: CreatePriceRuleUseCase,
    private val editPriceRuleUseCase: EditPriceRuleUseCase,
    private val deletePriceRuleUseCase: DeletePriceRuleUseCase
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PriceRuleViewModel(
            getAllPriceRulesUseCase,
            createPriceRuleUseCase,
            editPriceRuleUseCase,
            deletePriceRuleUseCase
        ) as T
    }
}

