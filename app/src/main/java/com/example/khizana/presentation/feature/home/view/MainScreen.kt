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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.khizana.R
import com.example.khizana.presentation.feature.inventory.view.InventoryScreen
import com.example.khizana.presentation.feature.priceRules.view.PartialPriceRuleBottomSheet
import com.example.khizana.presentation.feature.priceRules.view.PriceRules
import com.example.khizana.presentation.feature.products.view.PartialBottomSheet
import com.example.khizana.presentation.feature.products.view.ProductsScreen
import com.example.khizana.presentation.feature.authentication.view.ProfileScreen
import com.example.khizana.ui.theme.offWhiteColor
import com.example.khizana.utilis.internet.InternetConnectivityViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    snackBarHostState: SnackbarHostState,
    navigationController: NavHostController,
) {

    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    val showBottomSheet = rememberSaveable { mutableStateOf(false) }
    val showPriceRuleBottomSheet = rememberSaveable { mutableStateOf(false) }
    val internetConnectivityViewModel : InternetConnectivityViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        internetConnectivityViewModel.getInternetConnectivity()
        delay(1000)
        internetConnectivityViewModel.isConnected.collect { isConnected ->
                snackBarHostState.showSnackbar(
                    message = if(isConnected) "Internet Connection Restored" else "Internet Connection Lost",
                    duration = SnackbarDuration.Long,
                    withDismissAction = true,
                )
        }
    }

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
                                "Khizana.", style = TextStyle(
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
                        if (selectedIndex == 1) {
                            showBottomSheet.value = true
                        } else if (selectedIndex == 3) {
                            showPriceRuleBottomSheet.value = true
                        }

                    }) {
                        if (selectedIndex != 0 && selectedIndex != 2 && selectedIndex != 4) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Add",
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
                        )

                    }

                    3 -> {
                        return@Column PriceRules(
                            snackBarHostState = snackBarHostState,
                            navigationController = navigationController
                        )

                    }

                    4 -> {
                        return@Column ProfileScreen(navController = navigationController)

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
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.Gray,
                        selectedIconColor = Color.White,
                        indicatorColor = Color.White,
                    ),

                    label = {
                        if (selectedIndex == 0)
                            Text("Home")
                    },
                    selected = false,
                    onClick = {
                        selectedIndex = 0
                    },
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_show_chart_24),
                            contentDescription = ""
                        )
                    }
                )
                NavigationBarItem(
                    selected = false,
                    label = {
                        if (selectedIndex == 1)
                            Text("Products")
                    },
                    onClick = {
                        selectedIndex = 1
                    },
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_production_quantity_limits_24),
                            contentDescription = ""
                        )
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { selectedIndex = 2 },
                    label = {
                        if (selectedIndex == 2)
                            Text("Inventory")
                    },
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_inventory_24),
                            contentDescription = ""
                        )
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { selectedIndex = 3 },
                    label = {
                        if (selectedIndex == 3)
                            Text("Coupons")
                    },
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_discount_24),
                            contentDescription = ""
                        )
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { selectedIndex = 4 },
                    label = {
                        if (selectedIndex == 4)
                            Text("Profile")
                    },
                    icon = {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_person_24),
                            contentDescription = ""
                        )
                    }
                )
            }
        },
    )
}