package com.example.khizana.domain.usecase

import com.example.khizana.domain.repository.AuthRepository

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        authRepository.login(email, password, onSuccess, onFailure)
    }
}
