package com.example.khizana

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberAsyncImagePainter
import com.example.khizana.data.model.Product
import com.example.khizana.data.remote.RemoteDataSourceImpl
import com.example.khizana.data.remote.RetrofitFactory
import com.example.khizana.data.repo.RepositoryImpl
import com.example.khizana.presentation.viewModel.ProductsViewModelFactory
import com.example.khizana.presentation.viewModel.ProductsViewModel
import com.example.khizana.ui.theme.KhizanaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val factory =
                ProductsViewModelFactory(RepositoryImpl(RemoteDataSourceImpl(RetrofitFactory.apiService)))
            val productsViewModel = ViewModelProvider(this, factory)[ProductsViewModel::class.java]
            productsViewModel.getCollects()
            productsViewModel.getCollectById(
                productsViewModel.collects.observeAsState().value?.collects?.get(1)?.collection_id
                    ?: 0
            )
            productsViewModel.getProducts()
            Log.i(
                "TAG", "onCreate: ${
                    productsViewModel.products.observeAsState().value?.products
                        ?: listOf()
                }"
            )
            val products = productsViewModel.products.observeAsState().value?.products
                ?: listOf()

            KhizanaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        products = products,
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
fun Greeting(products: List<Product>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(products.size) { index ->
            Image(
                painter = rememberAsyncImagePainter(products[index].image?.src ?: ""),
                contentDescription = "",
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}





@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    KhizanaTheme {
        Greeting(listOf())
    }
}