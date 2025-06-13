package com.example.khizana.di

import com.example.khizana.data.datasource.remote.RemoteDataSourceImpl
import com.example.khizana.data.repository.AuthRepositoryImpl
import com.example.khizana.data.repository.DiscountCodeRepositoryImpl
import com.example.khizana.data.repository.InventoryRepositoryImpl
import com.example.khizana.data.repository.OrderRepositoryImpl
import com.example.khizana.data.repository.PriceRuleRepositoryImpl
import com.example.khizana.data.repository.ProductRepositoryImpl
import com.example.khizana.data.repository.RemoteDataSource
import com.example.khizana.domain.repository.AuthRepository
import com.example.khizana.domain.repository.DiscountCodeRepository
import com.example.khizana.domain.repository.InventoryRepository
import com.example.khizana.domain.repository.OrderRepository
import com.example.khizana.domain.repository.PriceRuleRepository
import com.example.khizana.domain.repository.ProductRepository
import com.example.khizana.utilis.internet.InternetObserver
import com.example.khizana.utilis.internet.InternetObserverImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

    @Binds
    @Singleton
    abstract fun bindPriceRuleRepository(priceRuleRepositoryImpl: PriceRuleRepositoryImpl): PriceRuleRepository

    @Binds
    @Singleton
    abstract fun bindOrderRepository(orderRepositoryImpl: OrderRepositoryImpl): OrderRepository

    @Binds
    @Singleton
    abstract fun bindInventoryRepository(inventoryRepositoryImpl: InventoryRepositoryImpl): InventoryRepository

    @Binds
    @Singleton
    abstract fun bindDiscountCodeRepository(discountCodeRepositoryImpl: DiscountCodeRepositoryImpl): DiscountCodeRepository
}