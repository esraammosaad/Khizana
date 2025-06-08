package com.example.khizana.di

import com.example.khizana.data.datasource.remote.ApiService
import com.example.khizana.data.datasource.remote.AuthService
import com.example.khizana.data.datasource.remote.RetrofitFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProviderModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService = RetrofitFactory().apiService


    @Provides
    fun  provideAuth(): AuthService = AuthService()

}