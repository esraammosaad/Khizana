package com.example.khizana

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.khizana.presentation.feature.home.view.MainScreen
import com.example.khizana.presentation.feature.landing.OnBoardingScreen
import com.example.khizana.presentation.feature.landing.SplashScreen
import com.example.khizana.presentation.feature.login.view.LoginScreen
import com.example.khizana.presentation.feature.priceRules.view.DiscountCodeScreen
import com.example.khizana.presentation.feature.products.view.ProductDetailsScreen
import com.example.khizana.utilis.NavigationRoutes
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navigationController = rememberNavController()
            val snackBarHostState = remember { SnackbarHostState() }

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
                    )
                }
                composable<NavigationRoutes.MainScreen> {
                    MainScreen(
                        snackBarHostState = snackBarHostState,
                        navigationController = navigationController,
                    )
                }
                composable<NavigationRoutes.ProductDetailsScreen> { backStackEntry ->
                    val data = backStackEntry.toRoute<NavigationRoutes.ProductDetailsScreen>()
                    val id = data.productId
                    ProductDetailsScreen(
                        productId = id,
                        navigationController = navigationController
                    )
                }
                composable<NavigationRoutes.DiscountCodesScreen> { backStackEntry ->
                    val data = backStackEntry.toRoute<NavigationRoutes.DiscountCodesScreen>()
                    val id = data.priceRuleId
                    DiscountCodeScreen(
                        priceRuleId = id,
                        snackBarHostState = snackBarHostState,
                        navigationController = navigationController
                    )
                }
            }
        }
    }
}


