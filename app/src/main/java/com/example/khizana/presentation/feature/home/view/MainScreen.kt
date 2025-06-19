package com.example.khizana.presentation.feature.home.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Inventory
import androidx.compose.material.icons.outlined.LocalMall
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Redeem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.khizana.R
import com.example.khizana.presentation.feature.authentication.viewModel.AuthViewModel
import com.example.khizana.presentation.feature.inventory.view.InventoryScreen
import com.example.khizana.presentation.feature.priceRules.view.PartialPriceRuleBottomSheet
import com.example.khizana.presentation.feature.priceRules.view.PriceRules
import com.example.khizana.presentation.feature.products.view.PartialBottomSheet
import com.example.khizana.presentation.feature.products.view.ProductsScreen
import com.example.khizana.ui.theme.offWhiteColor
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.utilis.NavigationRoutes
import com.example.khizana.utilis.WarningDialog


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    snackBarHostState: SnackbarHostState,
    navigationController: NavHostController,
    isConnected: MutableState<Boolean>,
    onConfirmation: () -> Unit,
    authViewModel: AuthViewModel,
) {

    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    val showBottomSheet = rememberSaveable { mutableStateOf(false) }
    val showWarningDialog = rememberSaveable { mutableStateOf(false) }
    val showPriceRuleBottomSheet = rememberSaveable { mutableStateOf(false) }
    val colors: NavigationBarItemColors = NavigationBarItemDefaults.colors(
        unselectedIconColor = Color.Gray,
        selectedIconColor = primaryColor,
        indicatorColor = Color.Transparent,
        unselectedTextColor = Color.Gray,
        disabledIconColor = Color.Gray,
        disabledTextColor = Color.Gray,
        selectedTextColor = primaryColor,
    )
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        containerColor = offWhiteColor,
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(
                    containerColor = offWhiteColor,
                    scrolledContainerColor = Color.Black,
                    navigationIconContentColor = Color.Black,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black,
                ),
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(offWhiteColor),
                    ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.bg),
                                    contentDescription = "",
                                    modifier = Modifier.size(40.dp),
                                )
                                Text(
                                    stringResource(R.string.app_name), style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                    ),
                                    modifier = Modifier.padding(start = 8.dp, top = 8.dp)
                                )

                        }
                    }
                },
                navigationIcon = {
                },
                actions = {
                    IconButton(onClick = {
                        if (isConnected.value) {
                            if (selectedIndex == 1) {
                                showBottomSheet.value = true
                            } else if (selectedIndex == 3) {
                                showPriceRuleBottomSheet.value = true
                            }else if (selectedIndex == 0){
                                authViewModel.logout()
                                navigationController.navigate(
                                    NavigationRoutes.LoginScreen,
                                    builder = {
                                        popUpTo(NavigationRoutes.SplashScreen) {
                                            inclusive = true
                                        }
                                    }
                                )

                            }
                        } else {
                            showWarningDialog.value = true
                        }

                    }) {
                        if (selectedIndex != 0 && selectedIndex != 2 && selectedIndex != 4) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = stringResource(R.string.add),
                            )
                        }
                        if(selectedIndex == 0){
                            Icon(
                                imageVector = Icons.Outlined.Logout,
                                contentDescription = stringResource(R.string.logout),
                            )
                        }
                    }
                }
            )
        },
        content = {
            PartialBottomSheet(
                showBottomSheet = showBottomSheet,
                product = null,
                isEditable = false
            )
            WarningDialog(
                onConfirmation = onConfirmation,
                dialogText = stringResource(R.string.there_is_no_internet_connection_you_can_t_add_anything_right_now),
                dialogTitle = stringResource(R.string.warning),
                confirmText = stringResource(R.string.wifi_settings),
                showAlert = showWarningDialog
            )
            PartialPriceRuleBottomSheet(
                showBottomSheet = showPriceRuleBottomSheet,
                priceRule = null,
                isEditable = false
            )
            Column(
                modifier = Modifier
                    .background(offWhiteColor)
                    .fillMaxSize()
                    .padding(it)
            ) {
                when (selectedIndex) {
                    0 -> {
                        return@Column HomeScreen()
                    }

                    1 -> {
                        return@Column ProductsScreen(
                            snackBarHostState = snackBarHostState,
                            navigationController = navigationController
                        )

                    }

                    2 -> {
                        return@Column InventoryScreen(
                            snackBarHostState = snackBarHostState,
                            isConnected = isConnected,
                            onConfirmation = onConfirmation
                        )

                    }

                    3 -> {
                        return@Column PriceRules(
                            snackBarHostState = snackBarHostState,
                            navigationController = navigationController
                        )

                    }
                }

            }
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
                containerColor = Color.White,

                ) {
                NavigationBarItem(
                    colors = colors,
                    label = {
                        Text(stringResource(R.string.overview))
                    },
                    selected = selectedIndex == 0,
                    onClick = {
                        selectedIndex = 0
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.BarChart,
                            contentDescription = stringResource(R.string.barchart_icon)
                        )
                    }
                )
                NavigationBarItem(
                    colors = colors,
                    selected = selectedIndex == 1,
                    label = {
                        Text(stringResource(R.string.products))
                    },
                    onClick = {
                        selectedIndex = 1
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.LocalMall,
                            contentDescription = stringResource(R.string.products_icon)
                        )
                    }
                )
                NavigationBarItem(
                    colors = colors,
                    selected = selectedIndex == 2,
                    onClick = { selectedIndex = 2 },
                    label = {
                        Text(stringResource(R.string.inventory))
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Inventory,
                            contentDescription = stringResource(R.string.inventory_icon)
                        )
                    }
                )
                NavigationBarItem(
                    colors = colors,
                    selected = selectedIndex == 3,
                    onClick = { selectedIndex = 3 },
                    label = {
                        Text(stringResource(R.string.coupons))
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Outlined.Redeem,
                            contentDescription = stringResource(R.string.discount_icon)
                        )
                    }
                )
            }
        },
    )
}