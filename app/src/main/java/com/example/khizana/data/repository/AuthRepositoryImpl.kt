package com.example.khizana.data.repository

import com.example.khizana.domain.repository.AuthRepository

class AuthRepositoryImpl(private val remoteDataSource: RemoteDataSource) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        remoteDataSource.login(email, password, onSuccess, onFailure)
    }
}