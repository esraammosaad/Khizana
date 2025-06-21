package com.example.khizana

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.khizana.presentation.feature.authentication.view.LoginScreen
import com.example.khizana.presentation.feature.authentication.viewModel.AuthViewModel
import com.example.khizana.presentation.feature.home.view.MainScreen
import com.example.khizana.presentation.feature.landing.OnBoardingScreen
import com.example.khizana.presentation.feature.landing.SplashScreen
import com.example.khizana.presentation.feature.priceRules.view.DiscountCodeScreen
import com.example.khizana.presentation.feature.products.view.ProductDetailsScreen
import com.example.khizana.utilis.NavigationRoutes
import com.example.khizana.utilis.WarningDialog
import com.example.khizana.utilis.internet.InternetConnectivityViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val isConnected = mutableStateOf(false)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel: AuthViewModel by viewModels()
        val internetConnectivityViewModel: InternetConnectivityViewModel by viewModels()
        internetConnectivityViewModel.getInternetConnectivity()
        enableEdgeToEdge()
        setContent {
            val navigationController = rememberNavController()
            val snackBarHostState = remember { SnackbarHostState() }
            isConnected.value =
                internetConnectivityViewModel.isConnected.collectAsStateWithLifecycle().value
            val openAlertDialog = remember { mutableStateOf(false) }
            val dialogTitle = remember { mutableStateOf("") }
            val dialogText = remember { mutableStateOf("") }
            val confirmText = remember { mutableStateOf("Confirm") }
            val onConfirmation = remember { mutableStateOf({
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
            }) }
            fun decideStartDestination(): NavigationRoutes {
                return when {
                    FirebaseAuth.getInstance().currentUser != null -> NavigationRoutes.MainScreen
                    authViewModel.getStartedState() -> NavigationRoutes.LoginScreen
                    else -> NavigationRoutes.OnBoardingScreen
                }
            }
            checkInternet(
                openAlertDialog = openAlertDialog,
                dialogTitle = dialogTitle,
                dialogText = dialogText,
                confirmText = confirmText,
                onConfirmation = onConfirmation,
                showRadioButton = isConnected
            )

            NavHost(
                navController = navigationController,
                startDestination = NavigationRoutes.SplashScreen
            ) {
                composable<NavigationRoutes.SplashScreen> {
                    SplashScreen {
                        navigationController.navigate(
                            decideStartDestination(),
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
                        isConnected = isConnected,
                        onConfirmation = onConfirmation.value,
                        authViewModel = authViewModel
                    )
                }
                composable<NavigationRoutes.ProductDetailsScreen> { backStackEntry ->
                    val data = backStackEntry.toRoute<NavigationRoutes.ProductDetailsScreen>()
                    val id = data.productId
                    ProductDetailsScreen(
                        productId = id,
                        snackBarHostState = snackBarHostState,
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

            WarningDialog(
                onConfirmation = onConfirmation.value,
                dialogText = dialogText.value,
                dialogTitle = dialogTitle.value,
                confirmText = confirmText.value,
                showAlert = openAlertDialog
            )
        }
    }

    private fun checkInternet(
        openAlertDialog: MutableState<Boolean>,
        dialogTitle: MutableState<String>,
        dialogText: MutableState<String>,
        confirmText: MutableState<String>,
        onConfirmation: MutableState<() -> Unit>,
        showRadioButton: MutableState<Boolean>
    ) {
        if (!isConnected.value) {
            openAlertDialog.value = true
            dialogTitle.value = "Warning"
            dialogText.value =
                "No Internet Connection Check Your Network Settings"
            showRadioButton.value = false
            confirmText.value = "WIFI Settings"
            onConfirmation.value = onConfirmation.value

        } else if (isConnected.value) {
            openAlertDialog.value = false
        }
    }
}


