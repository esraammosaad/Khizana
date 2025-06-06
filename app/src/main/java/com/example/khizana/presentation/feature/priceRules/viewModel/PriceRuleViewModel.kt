package com.example.khizana.presentation.feature.priceRules.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.khizana.domain.model.PriceRuleDomain
import com.example.khizana.domain.model.PriceRuleRequestDomain
import com.example.khizana.domain.usecase.CreatePriceRuleUseCase
import com.example.khizana.domain.usecase.DeletePriceRuleUseCase
import com.example.khizana.domain.usecase.EditPriceRuleUseCase
import com.example.khizana.domain.usecase.GetAllPriceRulesUseCase
import kotlinx.coroutines.launch

class PriceRuleViewModel(
    private val getAllPriceRulesUseCase: GetAllPriceRulesUseCase,
    private val createPriceRuleUseCase: CreatePriceRuleUseCase,
    private val editPriceRuleUseCase: EditPriceRuleUseCase,
    private val deletePriceRuleUseCase: DeletePriceRuleUseCase
) :
    ViewModel() {

    private var _priceRules = MutableLiveData<PriceRuleDomain>()
    val priceRules: LiveData<PriceRuleDomain> = _priceRules


    fun getAllPriceRules() {
        viewModelScope.launch {
            val response = getAllPriceRulesUseCase.getAllPriceRules()
            response.collect {
                _priceRules.postValue(it)
            }
        }
    }

    fun createPriceRule(priceRule: PriceRuleRequestDomain) {
        viewModelScope.launch {
            createPriceRuleUseCase.createPriceRule(priceRule)
            getAllPriceRules()
        }
    }

    fun editPriceRule(priceRuleId: String, priceRule: PriceRuleRequestDomain) {
        viewModelScope.launch {
            editPriceRuleUseCase.editPriceRule(priceRuleId, priceRule)
            getAllPriceRules()
        }
    }

    fun deletePriceRule(priceRuleId: String) {
        viewModelScope.launch {
            deletePriceRuleUseCase.deletePriceRule(priceRuleId)
            getAllPriceRules()


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

