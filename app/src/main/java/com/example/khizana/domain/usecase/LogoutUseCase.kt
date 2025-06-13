package com.example.khizana.domain.usecase

import com.example.khizana.domain.repository.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {
    suspend fun logout() {
        authRepository.logout()
    }
}