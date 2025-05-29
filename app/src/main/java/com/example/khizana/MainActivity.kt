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
import com.example.khizana.data.datasource.remote.RemoteDataSourceImpl
import com.example.khizana.data.datasource.remote.RetrofitFactory
import com.example.khizana.data.repository.OrderRepositoryImpl
import com.example.khizana.data.repository.ProductRepositoryImpl
import com.example.khizana.domain.usecase.CreateProductUseCase
import com.example.khizana.domain.usecase.DeleteProductUseCase
import com.example.khizana.domain.usecase.EditProductUseCase
import com.example.khizana.domain.usecase.GetOrdersUseCase
import com.example.khizana.domain.usecase.GetProductByIdUseCase
import com.example.khizana.domain.usecase.GetProductsUseCase
import com.example.khizana.presentation.feature.home.view.MainScreen
import com.example.khizana.presentation.feature.home.viewModel.HomeViewModelFactory
import com.example.khizana.presentation.feature.home.viewModel.HomeViewModel
import com.example.khizana.presentation.feature.products.view.ProductDetailsScreen
import com.example.khizana.presentation.feature.products.viewModel.ProductsViewModel
import com.example.khizana.presentation.feature.products.viewModel.ProductsViewModelFactory
import com.example.khizana.utilis.NavigationRoutes

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

            NavHost(
                navController = navigationController,
                startDestination = NavigationRoutes.MainScreen
            ) {

                composable<NavigationRoutes.MainScreen> {

                    MainScreen(
                        homeViewModel = homeViewModel,
                        productsViewModel = productsViewModel,
                        navigationController = navigationController
                    )

                }

                composable<NavigationRoutes.ProductDetailsScreen> { backStackEntry ->
                    val data = backStackEntry.toRoute<NavigationRoutes.ProductDetailsScreen>()
                    val id = data.productId
                    ProductDetailsScreen(productId = id)
                }
            }

        }
    }
}


