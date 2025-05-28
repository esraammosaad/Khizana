package com.example.khizana

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.khizana.data.datasource.remote.RemoteDataSourceImpl
import com.example.khizana.data.datasource.remote.RetrofitFactory
import com.example.khizana.data.repository.OrderRepositoryImpl
import com.example.khizana.data.repository.ProductRepositoryImpl
import com.example.khizana.domain.usecase.GetOrdersUseCase
import com.example.khizana.domain.usecase.GetProductsUseCase
import com.example.khizana.presentation.feature.home.view.MainScreen
import com.example.khizana.presentation.feature.home.viewModel.HomeViewModelFactory
import com.example.khizana.presentation.feature.home.viewModel.HomeViewModel
import com.example.khizana.presentation.feature.products.viewModel.ProductsViewModel
import com.example.khizana.presentation.feature.products.viewModel.ProductsViewModelFactory
import com.example.khizana.ui.theme.KhizanaTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val homeFactory =
                HomeViewModelFactory(
                    GetProductsUseCase(ProductRepositoryImpl(RemoteDataSourceImpl(RetrofitFactory.apiService))),
                    GetOrdersUseCase(
                        OrderRepositoryImpl(RemoteDataSourceImpl(RetrofitFactory.apiService))
                    )
                )
            val homeViewModel = ViewModelProvider(this, homeFactory)[HomeViewModel::class.java]

            val productsFactory = ProductsViewModelFactory(GetProductsUseCase(ProductRepositoryImpl(RemoteDataSourceImpl(RetrofitFactory.apiService))))
            val productsViewModel = ViewModelProvider(this, productsFactory)[ProductsViewModel::class.java]


            KhizanaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        homeViewModel = homeViewModel,
                        productsViewModel = productsViewModel
                    )
                }
            }
        }
    }
}


