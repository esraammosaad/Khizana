package com.example.khizana.presentation.feature.home.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.khizana.R
import com.example.khizana.presentation.feature.home.viewModel.HomeViewModel
import com.example.khizana.presentation.feature.products.view.ProductsScreen
import com.example.khizana.presentation.feature.products.viewModel.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    productsViewModel : ProductsViewModel
) {

    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(R.string.app_name))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                when (selectedIndex) {
                    0 -> {
                        return@Column HomeScreen(homeViewModel = homeViewModel)
                    }

                    1 -> {
                        return@Column ProductsScreen(productsViewModel = productsViewModel)

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
                        if (selectedIndex == 0) HorizontalDivider(
                            color = Color.Black,
                            thickness = 3.dp,
                            modifier = Modifier.width(30.dp)
                        )
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
                        if (selectedIndex == 1) HorizontalDivider(
                            color = Color.Black,
                            thickness = 3.dp,
                            modifier = Modifier.width(30.dp)
                        )
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
                        if (selectedIndex == 2) HorizontalDivider(
                            color = Color.Black,
                            thickness = 3.dp,
                            modifier = Modifier.width(30.dp)
                        )
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
                        if (selectedIndex == 3) HorizontalDivider(
                            color = Color.Black,
                            thickness = 3.dp,
                            modifier = Modifier.width(30.dp)
                        )
                    },
                    icon = {
//                        Image(
//                            painter = painterResource(id = R.drawable.user),
//                            contentDescription = ""
//                        )
                    }
                )
            }
        }
    )


}