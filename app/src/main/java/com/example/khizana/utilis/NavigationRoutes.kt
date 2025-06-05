package com.example.khizana.utilis

import com.example.khizana.domain.model.PriceRuleItem
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationRoutes {

    @Serializable
    data object SplashScreen : NavigationRoutes()

    @Serializable
    data object OnBoardingScreen : NavigationRoutes()

    @Serializable
    data object LoginScreen : NavigationRoutes()

    @Serializable
    data object MainScreen : NavigationRoutes()

    @Serializable
    data class ProductDetailsScreen(val productId : String) : NavigationRoutes()

    @Serializable
    data object AddProductScreen : NavigationRoutes()

    @Serializable
    data object AddPriceRuleScreen : NavigationRoutes()

}