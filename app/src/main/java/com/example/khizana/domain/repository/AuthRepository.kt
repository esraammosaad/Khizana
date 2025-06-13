package com.example.khizana.domain.repository

interface AuthRepository {

    suspend fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    )

    suspend fun logout()
    suspend fun saveGetStartedState()
    fun getStartedState(): Boolean

}