package com.example.khizana.presentation.feature.home.view

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.khizana.R
import com.example.khizana.presentation.feature.home.viewModel.HomeViewModel
import com.example.khizana.ui.theme.KhizanaTheme

@Composable
fun HomeScreen(modifier: Modifier, homeViewModel: HomeViewModel) {
    val ordersCount = homeViewModel.ordersCount.observeAsState().value
    val weeklyActivity : MutableList<Int> = mutableListOf()
    for (i in 0..6) {
        weeklyActivity.add(ordersCount?.get(i)?.count ?: 0)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 64.dp, horizontal = 24.dp),
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
                    .padding()
                    .background(Color(0xffd3e7e5), shape = RoundedCornerShape(12))
                    .height(260.dp)
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ActivityChart(data = weeklyActivity)
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
                    textTwo = "280.99 EGP",
                    icon = R.drawable.discount
                )
                Spacer(modifier = Modifier.width(8.dp))
                CustomBox(
                    modifier = Modifier.weight(1f),
                    color = Color(0xFFece9f2),
                    textOne = "Revenue Last Week",
                    textTwo = "280.99 EGP",
                    icon = R.drawable.chart
                )
            }


        }


    }
}

@Composable
private fun CustomBox(
    modifier: Modifier = Modifier,
    color: Color,
    textOne: String,
    textTwo: String,
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
            Text(
                textTwo,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    KhizanaTheme {
        //HomeScreen(modifier = Modifier)
    }
}

