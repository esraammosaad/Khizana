package com.example.khizana.presentation.feature.priceRules.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.khizana.domain.model.PriceRuleDomain
import com.example.khizana.domain.usecase.GetAllPriceRulesUseCase
import kotlinx.coroutines.launch

class PriceRuleViewModel(private val getAllPriceRulesUseCase: GetAllPriceRulesUseCase) :
    ViewModel() {

    private var _priceRules = MutableLiveData<PriceRuleDomain>()
    val priceRules: LiveData<PriceRuleDomain> = _priceRules


    fun getAllPriceRules() {

        viewModelScope.launch {

            _priceRules.postValue(getAllPriceRulesUseCase.getAllPriceRules())

        }

    }


}

class PriceRuleViewModelFactory(private val getAllPriceRulesUseCase: GetAllPriceRulesUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PriceRuleViewModel(getAllPriceRulesUseCase) as T
    }


}