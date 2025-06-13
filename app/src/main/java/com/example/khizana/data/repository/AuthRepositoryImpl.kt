package com.example.khizana.data.repository

import com.example.khizana.data.datasource.local.LocalStorageDataSource
import com.example.khizana.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localStorageDataSource: LocalStorageDataSource
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        remoteDataSource.login(email, password, onSuccess, onFailure)
    }

    override suspend fun logout() {
        remoteDataSource.logout()
    }
    override suspend fun saveGetStartedState() {
        localStorageDataSource.saveGetStartedStateState()
    }

    override fun getStartedState(): Boolean {
        return localStorageDataSource.getStartedState
    }
}