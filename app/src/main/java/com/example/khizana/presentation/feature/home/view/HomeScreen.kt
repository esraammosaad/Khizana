package com.example.khizana.presentation.feature.home.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.khizana.R
import com.example.khizana.domain.model.OrdersCountDomain
import com.example.khizana.presentation.feature.home.viewModel.HomeViewModel
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.ui.theme.secondaryColor
import com.example.khizana.utilis.Response

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    LaunchedEffect(Unit) {
        homeViewModel.getOrdersCount()
        homeViewModel.getOrders()
    }
    val ordersCount = homeViewModel.ordersCount.collectAsStateWithLifecycle().value
    val totalOrdersPrice = homeViewModel.totalOrdersPrice.collectAsStateWithLifecycle().value
    val weeklyActivity: MutableList<Int> = mutableListOf()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 0.dp, start = 20.dp, end = 20.dp),
    ) {
        item {
            Text(
                stringResource(R.string.hello_admin),
                style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                stringResource(R.string.welcome_back),
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
                            ordersCount as Response.Success<List<OrdersCountDomain>>
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
                    textOne = "Sales Last Week",
                    textTwo = totalOrdersPrice,
                    icon = R.drawable.discount
                )
                Spacer(modifier = Modifier.width(8.dp))
                CustomBox(
                    modifier = Modifier.weight(1f),
                    color = Color(0xFFece9f2),
                    textOne = "Revenue Last Week",
                    textTwo = totalOrdersPrice,
                    icon = R.drawable.chart
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
//            Row(modifier = Modifier.fillMaxWidth()) {
//                CustomBox(
//                    modifier = Modifier.weight(1f),
//                    color = Color(0xFFf8ede7),
//                    textOne = "Sales Last Week",
//                    textTwo = totalOrdersPrice,
//                    //String.format("%.2f", totalOrdersPrice) + " EGP",
//                    icon = R.drawable.discount
//                )
//                Spacer(modifier = Modifier.width(8.dp))
//                CustomBox(
//                    modifier = Modifier.weight(1f),
//                    color = Color(0xFFece9f2),
//                    textOne = "Revenue Last Week",
//                    textTwo = totalOrdersPrice,
//                    icon = R.drawable.chart
//                )
//            }
        }
    }
}

@Composable
private fun CustomBox(
    modifier: Modifier = Modifier,
    color: Color,
    textOne: String,
    textTwo: Response,
    icon: Int
) {

    Box(
        modifier = modifier
            .background(color, shape = RoundedCornerShape(12))
            .height(120.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,

            ) {
            Image(painter = painterResource(icon), contentDescription = "")
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                textOne,
                style = TextStyle(fontSize = 16.sp, color = Color.Gray)
            )
            Spacer(modifier = Modifier.height(5.dp))
            when (textTwo) {
                is Response.Success<*> -> {
                    textTwo as Response.Success<Double>
                    Text(
                        String.format("%.2f", textTwo.result) + " EGP",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    )
                }

                is Response.Failure -> {
                    Text(textTwo.exception)
                }
                is Response.Loading -> {
                    CircularProgressIndicator(
                        color = primaryColor,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

