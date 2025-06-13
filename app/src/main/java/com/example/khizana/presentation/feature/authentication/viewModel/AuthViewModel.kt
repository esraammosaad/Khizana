package com.example.khizana.presentation.feature.authentication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.khizana.domain.usecase.GetStartedStateUseCase
import com.example.khizana.domain.usecase.LoginUseCase
import com.example.khizana.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val getStartedStateUseCase: GetStartedStateUseCase
) : ViewModel() {

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            loginUseCase.login(email, password, onSuccess, onFailure)
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.logout()
        }
    }

    fun saveGetStartedState() {
        viewModelScope.launch {
            getStartedStateUseCase.saveGetStartedState()
        }
    }

    fun getStartedState(): Boolean {
        return getStartedStateUseCase.getStartedState()
    }
}

