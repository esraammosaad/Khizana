package com.example.khizana.presentation.feature.home.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.khizana.R
import com.example.khizana.domain.model.CountDomain
import com.example.khizana.presentation.feature.home.view.component.CustomBox
import com.example.khizana.presentation.feature.home.view.component.CustomCountBox
import com.example.khizana.presentation.feature.home.viewModel.HomeViewModel
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.ui.theme.secondaryColor
import com.example.khizana.utilis.Response
import com.google.firebase.auth.FirebaseAuth


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        homeViewModel.getOrdersCount()
        homeViewModel.getTotalOrdersPrice()
        homeViewModel.getTotalRevenue()
        homeViewModel.getProductsCount()
        homeViewModel.getInventoryLocationsCount()
    }
    val ordersCount = homeViewModel.ordersCount.collectAsStateWithLifecycle().value
    val totalOrdersPrice = homeViewModel.totalOrdersPrice.collectAsStateWithLifecycle().value
    val totalRevenue = homeViewModel.revenue.collectAsStateWithLifecycle().value
    val productsCount = homeViewModel.productsCount.collectAsStateWithLifecycle().value
    val inventoryLocationsCount =
        homeViewModel.inventoryLocationsCount.collectAsStateWithLifecycle().value
    val weeklyActivity: MutableList<Int> = mutableListOf()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp, start = 20.dp, end = 20.dp),
    ) {
        item {
            Text(
                stringResource(
                    R.string.hello,
                    FirebaseAuth.getInstance().currentUser?.displayName ?: ""
                ),
                style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                stringResource(R.string.let_s_get_back_to_business),
                style = TextStyle(fontSize = 18.sp, color = Color.Gray)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .background(secondaryColor, shape = RoundedCornerShape(12))
                    .height(260.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (ordersCount) {
                        is Response.Success<*> -> {
                            ordersCount as Response.Success<List<CountDomain>>
                            weeklyActivity.clear()
                            for (i in 0..6) {
                                weeklyActivity.add(ordersCount.result?.get(i)?.count ?: 0)
                            }
                            ActivityChart(data = weeklyActivity)
                        }

                        is Response.Failure -> {
                            Text(ordersCount.exception)
                        }

                        is Response.Loading -> {
                            CircularProgressIndicator(
                                color = primaryColor,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                    Text(
                        stringResource(R.string.activity),
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        stringResource(R.string.of_current_week),
                        style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                CustomBox(
                    modifier = Modifier.weight(1f),
                    color = Color(0xFFf8ede7),
                    textOne = stringResource(R.string.sales_last_week),
                    textTwo = totalOrdersPrice,
                    icon = R.drawable.discount
                )
                Spacer(modifier = Modifier.width(8.dp))
                CustomBox(
                    modifier = Modifier.weight(1f),
                    color = Color(0xFFece9f2),
                    textOne = stringResource(R.string.revenue_last_week),
                    textTwo = totalRevenue,
                    icon = R.drawable.chart
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                CustomCountBox(
                    modifier = Modifier.weight(1f),
                    color = Color(0xFFece9f2),
                    textOne = stringResource(R.string.products_count),
                    textTwo = productsCount,
                    icon = R.drawable.instock,
                    text = stringResource(R.string.product)
                )
                Spacer(modifier = Modifier.width(8.dp))
                CustomCountBox(
                    modifier = Modifier.weight(1f),
                    color = Color(0xFFf8ede7),
                    textOne = stringResource(R.string.inventory_locations_count),
                    textTwo = inventoryLocationsCount,
                    icon = R.drawable.location,
                    text = stringResource(R.string.location)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}





