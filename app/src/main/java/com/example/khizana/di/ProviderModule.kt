package com.example.khizana.di

import com.example.khizana.data.datasource.remote.ApiService
import com.example.khizana.data.datasource.remote.AuthService
import com.example.khizana.data.datasource.remote.RetrofitFactory
import com.example.khizana.data.repository.AuthRepositoryImpl
import com.example.khizana.data.repository.DiscountCodeRepositoryImpl
import com.example.khizana.data.repository.InventoryRepositoryImpl
import com.example.khizana.data.repository.OrderRepositoryImpl
import com.example.khizana.data.repository.PriceRuleRepositoryImpl
import com.example.khizana.data.repository.ProductRepositoryImpl
import com.example.khizana.domain.usecase.CreateDiscountCodeUseCase
import com.example.khizana.domain.usecase.CreatePriceRuleUseCase
import com.example.khizana.domain.usecase.CreateProductUseCase
import com.example.khizana.domain.usecase.DeleteDiscountCodeUseCase
import com.example.khizana.domain.usecase.DeletePriceRuleUseCase
import com.example.khizana.domain.usecase.DeleteProductUseCase
import com.example.khizana.domain.usecase.EditDiscountCodeUseCase
import com.example.khizana.domain.usecase.EditPriceRuleUseCase
import com.example.khizana.domain.usecase.EditProductUseCase
import com.example.khizana.domain.usecase.GetAllPriceRulesUseCase
import com.example.khizana.domain.usecase.GetDiscountCodeUseCase
import com.example.khizana.domain.usecase.GetInventoryLocationsUseCase
import com.example.khizana.domain.usecase.GetOrdersUseCase
import com.example.khizana.domain.usecase.GetProductByIdUseCase
import com.example.khizana.domain.usecase.GetProductsCountUseCase
import com.example.khizana.domain.usecase.GetProductsUseCase
import com.example.khizana.domain.usecase.LoginUseCase
import com.example.khizana.domain.usecase.SetInventoryItemQuantityUseCase
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
    fun provideAuth(): AuthService = AuthService()

    @Provides
    fun provideLoginUseCase(authRepositoryImpl: AuthRepositoryImpl): LoginUseCase =
        LoginUseCase(authRepositoryImpl)

    @Provides
    fun provideGetProductsUseCase(productRepositoryImpl: ProductRepositoryImpl): GetProductsUseCase =
        GetProductsUseCase(productRepositoryImpl)

    @Provides
    fun provideGetProductsCountUseCase(productRepositoryImpl: ProductRepositoryImpl): GetProductsCountUseCase =
        GetProductsCountUseCase(productRepositoryImpl)

    @Provides
    fun provideGetProductByIdUseCase(productRepositoryImpl: ProductRepositoryImpl): GetProductByIdUseCase =
        GetProductByIdUseCase(productRepositoryImpl)

    @Provides
    fun provideGetOrdersUseCase(orderRepositoryImpl: OrderRepositoryImpl): GetOrdersUseCase =
        GetOrdersUseCase(orderRepositoryImpl)

    @Provides
    fun provideGetDiscountCodeUseCase(discountCodeRepositoryImpl: DiscountCodeRepositoryImpl): GetDiscountCodeUseCase =
        GetDiscountCodeUseCase(discountCodeRepositoryImpl)

    @Provides
    fun provideGetAllPriceRulesUseCase(priceRuleRepositoryImpl: PriceRuleRepositoryImpl): GetAllPriceRulesUseCase =
        GetAllPriceRulesUseCase(priceRuleRepositoryImpl)

    @Provides
    fun provideEditProductUseCase(productRepositoryImpl: ProductRepositoryImpl): EditProductUseCase =
        EditProductUseCase(productRepositoryImpl)

    @Provides
    fun provideEditPriceRuleUseCase(priceRuleRepositoryImpl: PriceRuleRepositoryImpl): EditPriceRuleUseCase =
        EditPriceRuleUseCase(priceRuleRepositoryImpl)

    @Provides
    fun provideEditDiscountCodeUseCase(discountCodeRepositoryImpl: DiscountCodeRepositoryImpl): EditDiscountCodeUseCase =
        EditDiscountCodeUseCase(discountCodeRepositoryImpl)

    @Provides
    fun provideDeleteProductUseCase(productRepositoryImpl: ProductRepositoryImpl): DeleteProductUseCase =
        DeleteProductUseCase(productRepositoryImpl)

    @Provides
    fun provideDeletePriceRuleUseCase(priceRuleRepositoryImpl: PriceRuleRepositoryImpl): DeletePriceRuleUseCase =
        DeletePriceRuleUseCase(priceRuleRepositoryImpl)

    @Provides
    fun provideDeleteDiscountCodeUseCase(discountCodeRepositoryImpl: DiscountCodeRepositoryImpl): DeleteDiscountCodeUseCase =
        DeleteDiscountCodeUseCase(discountCodeRepositoryImpl)

    @Provides
    fun provideCreateProductUseCase(productRepositoryImpl: ProductRepositoryImpl): CreateProductUseCase =
        CreateProductUseCase(productRepositoryImpl)

    @Provides
    fun provideCreatePriceRuleUseCase(priceRuleRepositoryImpl: PriceRuleRepositoryImpl): CreatePriceRuleUseCase =
        CreatePriceRuleUseCase(priceRuleRepositoryImpl)

    @Provides
    fun provideCreateDiscountCodeUseCase(discountCodeRepositoryImpl: DiscountCodeRepositoryImpl): CreateDiscountCodeUseCase =
        CreateDiscountCodeUseCase(discountCodeRepositoryImpl)

    @Provides
    fun provideGtInventoryLocationsUseCase(inventoryRepositoryImpl: InventoryRepositoryImpl): GetInventoryLocationsUseCase =
        GetInventoryLocationsUseCase(inventoryRepositoryImpl)

    @Provides
    fun provideSetInventoryItemQuantityUseCase(inventoryRepositoryImpl: InventoryRepositoryImpl): SetInventoryItemQuantityUseCase =
        SetInventoryItemQuantityUseCase(inventoryRepositoryImpl)

}