package com.example.khizana

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberAsyncImagePainter
import com.example.khizana.data.datasource.remote.RemoteDataSourceImpl
import com.example.khizana.data.datasource.remote.RetrofitFactory
import com.example.khizana.data.repository.OrderRepositoryImpl
import com.example.khizana.data.repository.ProductRepositoryImpl
import com.example.khizana.domain.model.ProductsItem
import com.example.khizana.domain.usecase.GetOrdersUseCase
import com.example.khizana.domain.usecase.GetProductsUseCase
import com.example.khizana.presentation.feature.home.viewModel.HomeViewModelFactory
import com.example.khizana.presentation.feature.home.viewModel.HomeViewModel
import com.example.khizana.ui.theme.KhizanaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val factory =
                HomeViewModelFactory(
                    GetProductsUseCase(ProductRepositoryImpl(RemoteDataSourceImpl(RetrofitFactory.apiService))),
                    GetOrdersUseCase(
                        OrderRepositoryImpl(RemoteDataSourceImpl(RetrofitFactory.apiService))
                    )
                )
            val homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
            homeViewModel.getProducts()
            homeViewModel.getOrders()
            Log.i(
                "TAG", "onCreate: ${
                    homeViewModel.products.observeAsState().value?.products
                        ?: listOf()
                }"
            )
            val products = homeViewModel.products.observeAsState().value?.products
                ?: listOf()

            KhizanaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        products = products ?: listOf(),
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}


@Composable
fun Greeting(products: List<ProductsItem?>?, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(products?.size ?: 0) { index ->
            Image(
                painter = rememberAsyncImagePainter(products?.get(index)?.image?.src ?: ""),
                contentDescription = "",
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun HomeScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 64.dp, horizontal = 24.dp)
    ) {

        item {

            Text("Hello, Admin", style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Welcome Back!", style = TextStyle(fontSize = 18.sp, color = Color.Gray))
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .padding()
                    .background(Color(0xffd3e7e5), shape = RoundedCornerShape(12))
                    .height(230.dp)
                    .width(250.dp)
            ) {

                val weeklyActivity = listOf(40, 60, 80, 60, 40, 20, 20)

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ActivityChart(data = weeklyActivity)
                    Text(
                        "Activity",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    )
                    Text("of Current Week", style = TextStyle(fontSize = 12.sp, color = Color.Gray))

                }


            }

        }


    }
}

@Composable
fun ActivityChart(
    data: List<Int>,
    modifier: Modifier = Modifier
) {
    val maxValue = data.maxOrNull()?.takeIf { it > 0 } ?: 1
    val barWidth = 20.dp
    val spacing = 12.dp
    val chartHeight = 100.dp

    Row(
        modifier = modifier
            .padding(16.dp)
            .height(chartHeight + 30.dp),
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalAlignment = Alignment.Bottom
    ) {
        data.forEach { value ->
            val heightRatio = value.toFloat() / maxValue

            Column(
                modifier = Modifier.height(chartHeight + 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .width(barWidth)
                        .height(chartHeight * heightRatio)
                        .background(Color(0xFF90c4bf), shape = RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = value.toString(),
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    KhizanaTheme {
        HomeScreen()
    }
}