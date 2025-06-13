package com.example.khizana.domain.usecase

import com.example.khizana.domain.repository.AuthRepository

class GetStartedStateUseCase (private val authRepository: AuthRepository) {
    suspend fun saveGetStartedState() {
        authRepository.saveGetStartedState()
    }

    fun getStartedState(): Boolean {
        return authRepository.getStartedState()
    }
}