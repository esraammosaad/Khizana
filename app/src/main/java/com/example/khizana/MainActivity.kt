package com.example.khizana

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.khizana.data.datasource.remote.AuthService
import com.example.khizana.data.datasource.remote.RemoteDataSourceImpl
import com.example.khizana.data.datasource.remote.RetrofitFactory
import com.example.khizana.data.repository.AuthRepositoryImpl
import com.example.khizana.data.repository.DiscountCodeRepositoryImpl
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
import com.example.khizana.domain.usecase.GetOrdersUseCase
import com.example.khizana.domain.usecase.GetProductByIdUseCase
import com.example.khizana.domain.usecase.GetProductsUseCase
import com.example.khizana.domain.usecase.LoginUseCase
import com.example.khizana.presentation.feature.home.view.MainScreen
import com.example.khizana.presentation.feature.home.viewModel.HomeViewModelFactory
import com.example.khizana.presentation.feature.home.viewModel.HomeViewModel
import com.example.khizana.presentation.feature.landing.OnBoardingScreen
import com.example.khizana.presentation.feature.landing.SplashScreen
import com.example.khizana.presentation.feature.login.view.LoginScreen
import com.example.khizana.presentation.feature.login.viewModel.LoginViewModel
import com.example.khizana.presentation.feature.login.viewModel.LoginViewModelFactory
import com.example.khizana.presentation.feature.priceRules.view.DiscountCodeScreen
import com.example.khizana.presentation.feature.priceRules.viewModel.PriceRuleViewModel
import com.example.khizana.presentation.feature.priceRules.viewModel.PriceRuleViewModelFactory
import com.example.khizana.presentation.feature.products.view.ProductDetailsScreen
import com.example.khizana.presentation.feature.products.viewModel.ProductsViewModel
import com.example.khizana.presentation.feature.products.viewModel.ProductsViewModelFactory
import com.example.khizana.utilis.NavigationRoutes
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navigationController = rememberNavController()
            val homeFactory =
                HomeViewModelFactory(
                    GetProductsUseCase(ProductRepositoryImpl(RemoteDataSourceImpl(RetrofitFactory.apiService))),
                    GetOrdersUseCase(
                        OrderRepositoryImpl(RemoteDataSourceImpl(RetrofitFactory.apiService))
                    )
                )
            val homeViewModel = ViewModelProvider(this, homeFactory)[HomeViewModel::class.java]
            val productsFactory = ProductsViewModelFactory(
                GetProductsUseCase(
                    ProductRepositoryImpl(RemoteDataSourceImpl(RetrofitFactory.apiService))
                ),
                CreateProductUseCase(
                    ProductRepositoryImpl(RemoteDataSourceImpl(RetrofitFactory.apiService))
                ),
                DeleteProductUseCase(
                    ProductRepositoryImpl(RemoteDataSourceImpl(RetrofitFactory.apiService))
                ),
                GetProductByIdUseCase(
                    ProductRepositoryImpl(RemoteDataSourceImpl(RetrofitFactory.apiService))
                ),
                EditProductUseCase(
                    ProductRepositoryImpl(RemoteDataSourceImpl(RetrofitFactory.apiService))
                )
            )
            val productsViewModel =
                ViewModelProvider(this, productsFactory)[ProductsViewModel::class.java]

            val priceRuleFactory = PriceRuleViewModelFactory(
                GetAllPriceRulesUseCase(
                    PriceRuleRepositoryImpl(RemoteDataSourceImpl(RetrofitFactory.apiService))
                ),
                CreatePriceRuleUseCase(PriceRuleRepositoryImpl(RemoteDataSourceImpl(RetrofitFactory.apiService))),
                EditPriceRuleUseCase(PriceRuleRepositoryImpl(RemoteDataSourceImpl(RetrofitFactory.apiService))),
                DeletePriceRuleUseCase(PriceRuleRepositoryImpl(RemoteDataSourceImpl(RetrofitFactory.apiService))),
                GetDiscountCodeUseCase(
                    DiscountCodeRepositoryImpl(
                        RemoteDataSourceImpl(
                            RetrofitFactory.apiService
                        )
                    )
                ),
                CreateDiscountCodeUseCase(
                    DiscountCodeRepositoryImpl(
                        RemoteDataSourceImpl(
                            RetrofitFactory.apiService
                        )
                    )
                ),
                DeleteDiscountCodeUseCase(
                    DiscountCodeRepositoryImpl(
                        RemoteDataSourceImpl(
                            RetrofitFactory.apiService
                        )
                    )
                ),
                EditDiscountCodeUseCase(
                    DiscountCodeRepositoryImpl(
                        RemoteDataSourceImpl(
                            RetrofitFactory.apiService
                        )
                    )
                )
            )
            val priceRuleViewModel =
                ViewModelProvider(this, priceRuleFactory)[PriceRuleViewModel::class.java]
            val loginFactory = LoginViewModelFactory(
                LoginUseCase(
                    AuthRepositoryImpl(
                        RemoteDataSourceImpl(RetrofitFactory.apiService, AuthService())
                    )
                )
            )
            val loginViewModel = ViewModelProvider(this, loginFactory)[LoginViewModel::class.java]
            NavHost(
                navController = navigationController,
                startDestination = NavigationRoutes.SplashScreen
            ) {
                composable<NavigationRoutes.SplashScreen> {
                    SplashScreen {
                        if (FirebaseAuth.getInstance().currentUser != null)
                            navigationController.navigate(
                                NavigationRoutes.MainScreen,
                                builder = {
                                    popUpTo(NavigationRoutes.SplashScreen) {
                                        inclusive = true
                                    }
                                }
                            )
                        else
                            navigationController.navigate(NavigationRoutes.OnBoardingScreen,
                                builder = {
                                    popUpTo(NavigationRoutes.SplashScreen) {
                                        inclusive = true
                                    }
                                }
                            )
                    }
                }
                composable<NavigationRoutes.OnBoardingScreen> {
                    OnBoardingScreen(navController = navigationController)
                }
                composable<NavigationRoutes.LoginScreen> {
                    LoginScreen(
                        navController = navigationController,
                        loginViewModel = loginViewModel
                    )
                }
                composable<NavigationRoutes.MainScreen> {
                    MainScreen(
                        homeViewModel = homeViewModel,
                        productsViewModel = productsViewModel,
                        priceRuleViewModel = priceRuleViewModel,
                        navigationController = navigationController,
                    )
                }
                composable<NavigationRoutes.ProductDetailsScreen> { backStackEntry ->
                    val data = backStackEntry.toRoute<NavigationRoutes.ProductDetailsScreen>()
                    val id = data.productId
                    ProductDetailsScreen(
                        productId = id,
                        productsViewModel = productsViewModel,
                        navigationController = navigationController
                    )
                }
                composable<NavigationRoutes.DiscountCodesScreen> { backStackEntry ->
                    val data = backStackEntry.toRoute<NavigationRoutes.DiscountCodesScreen>()
                    val id = data.priceRuleId
                    DiscountCodeScreen(
                        priceRuleViewModel = priceRuleViewModel,
                        priceRuleId = id,
                        navigationController = navigationController
                    )
                }
            }
        }
    }
}


