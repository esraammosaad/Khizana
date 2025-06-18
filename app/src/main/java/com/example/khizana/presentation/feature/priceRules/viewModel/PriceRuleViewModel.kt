package com.example.khizana.presentation.feature.priceRules.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khizana.domain.model.DiscountCodeRequestDomain
import com.example.khizana.domain.model.PriceRuleRequestDomain
import com.example.khizana.domain.usecase.CreateDiscountCodeUseCase
import com.example.khizana.domain.usecase.CreatePriceRuleUseCase
import com.example.khizana.domain.usecase.DeleteDiscountCodeUseCase
import com.example.khizana.domain.usecase.DeletePriceRuleUseCase
import com.example.khizana.domain.usecase.EditDiscountCodeUseCase
import com.example.khizana.domain.usecase.EditPriceRuleUseCase
import com.example.khizana.domain.usecase.GetAllPriceRulesUseCase
import com.example.khizana.domain.usecase.GetDiscountCodeUseCase
import com.example.khizana.utilis.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log


@HiltViewModel
class PriceRuleViewModel @Inject constructor(
    private val getAllPriceRulesUseCase: GetAllPriceRulesUseCase,
    private val createPriceRuleUseCase: CreatePriceRuleUseCase,
    private val editPriceRuleUseCase: EditPriceRuleUseCase,
    private val deletePriceRuleUseCase: DeletePriceRuleUseCase,
    private val getDiscountCodeUseCase: GetDiscountCodeUseCase,
    private val createDiscountCodeUseCase: CreateDiscountCodeUseCase,
    private val deleteDiscountCodeUseCase: DeleteDiscountCodeUseCase,
    private val editDiscountCodeUseCase: EditDiscountCodeUseCase
) :
    ViewModel() {

    private var _priceRules = MutableStateFlow<Response>(Response.Loading)
    val priceRules = _priceRules.asStateFlow()

    private var _discountCodes = MutableStateFlow<Response>(Response.Loading)
    val discountCodes = _discountCodes.asStateFlow()

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
            Log.i("TAG", "createPriceRule: $priceRule========")
            try {
                createPriceRuleUseCase.createPriceRule(priceRule)
                getAllPriceRules()
                _message.emit("Price rule created successfully")
            } catch (e: Exception) {
                _message.emit(e.message.toString())
                Log.i("TAG", "createPriceRule: ${e.message}")
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

    fun getDiscountCodes(priceRuleId: String) {
        viewModelScope.launch {
            try {
                val response = getDiscountCodeUseCase.getDiscountCode(priceRuleId)
                response.catch {
                    _discountCodes.emit(Response.Failure(it.message.toString()))
                    _message.emit(it.message.toString())
                }.collect {
                    _discountCodes.emit(Response.Success(it))
                }
            } catch (e: Exception) {
                _discountCodes.emit(Response.Failure(e.message.toString()))
                _message.emit(e.message.toString())
            }
        }
    }

    fun deleteDiscountCode(priceRuleId: String, discountCodeId: String) {
        viewModelScope.launch {
            try {
                deleteDiscountCodeUseCase.deleteDiscountCode(priceRuleId, discountCodeId)
                getDiscountCodes(priceRuleId)
                _message.emit("Discount code deleted successfully")
            } catch (e: Exception) {
                _message.emit(e.message.toString())
            }
        }
    }

    fun createDiscountCode(priceRuleId: String, discountCodeRequest: DiscountCodeRequestDomain) {
        viewModelScope.launch {
            try {
                createDiscountCodeUseCase.createDiscountCode(
                    priceRuleId,
                    discountCodeRequest
                )
                getDiscountCodes(priceRuleId)
                _message.emit("Discount code created successfully")
            } catch (e: Exception) {
                _message.emit(e.message.toString())
            }
        }
    }

    fun editDiscountCode(
        priceRuleId: String,
        discountCodeId: String,
        discountCodeRequest: DiscountCodeRequestDomain
    ) {
        viewModelScope.launch {
            try {
                editDiscountCodeUseCase.editDiscountCode(
                    priceRuleId,
                    discountCodeId,
                    discountCodeRequest
                )
                getDiscountCodes(priceRuleId)
                _message.emit("Discount code edited successfully")
            } catch (e: Exception) {
                _message.emit(e.message.toString())
            }
        }
    }
}


