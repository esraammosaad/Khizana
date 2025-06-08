package com.example.khizana.data.repository

import com.example.khizana.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        remoteDataSource.login(email, password, onSuccess, onFailure)
    }
}