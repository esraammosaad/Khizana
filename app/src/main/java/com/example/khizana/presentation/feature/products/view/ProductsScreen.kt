package com.example.khizana.presentation.feature.products.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.khizana.R
import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.domain.model.ProductsItem
import com.example.khizana.presentation.feature.products.viewModel.ProductsViewModel
import com.example.khizana.ui.theme.KhizanaTheme
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.ui.theme.secondaryColor
import com.example.khizana.utilis.ConfirmationDialog
import com.example.khizana.utilis.CustomLoadingIndicator
import com.example.khizana.utilis.NavigationRoutes
import com.example.khizana.utilis.Response
import androidx.hilt.navigation.compose.hiltViewModel



@Composable
fun ProductsScreen(
    modifier: Modifier = Modifier,
    productsViewModel: ProductsViewModel = hiltViewModel(),
    navigationController: NavHostController
) {

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        productsViewModel.getProducts()
        productsViewModel.message.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    val products = productsViewModel.products.collectAsStateWithLifecycle().value

    val showDialog = remember { mutableStateOf(false) }
    val selectedProduct = remember { mutableStateOf("") }

    ConfirmationDialog(
        showDialog = showDialog.value,
        onConfirm = {
            productsViewModel.deleteProduct(productId = selectedProduct.value)
        },
        onDismiss = {
            showDialog.value = false
        }
    )
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        LazyColumn {
            when (products) {
                is Response.Success<*> -> {
                    products as Response.Success<ProductDomain>
                    items(
                        items = products.result?.products.orEmpty(),
                        key = { it?.id ?: "" }) {
                        Box(
                            modifier = Modifier
                                .animateItem()
                                .padding(bottom = 8.dp)
                                .height(250.dp)
                                .clickable {
                                    navigationController.navigate(
                                        NavigationRoutes.ProductDetailsScreen(
                                            it?.id ?: ""
                                        )
                                    )
                                }
                        ) {
                            CustomProductCard(modifier, it)
                            CustomStatusBox(
                                Modifier
                                    .align(alignment = Alignment.TopEnd)
                                    .padding(5.dp), it
                            )
                            CustomDeleteIcon(
                                modifier = Modifier.align(Alignment.TopStart),
                                showDialog,
                                selectedProduct,
                                it
                            )
                        }
                    }

                }
                is Response.Failure -> {
                    item {
                        Text(text = products.exception)
                    }
                }
                is Response.Loading -> {
                    item {
                        CustomLoadingIndicator(modifier = Modifier.fillParentMaxSize())
                    }
                }

            }
        }
    }
}

@Composable
private fun CustomDeleteIcon(
    modifier: Modifier,
    showDialog: MutableState<Boolean>,
    selectedProduct: MutableState<String>,
    it: ProductsItem?
) {
    Box(
        modifier = modifier
            .padding(5.dp)
            .size(30.dp)
            .background(
                color = secondaryColor,
                shape = RoundedCornerShape(50)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Clear,
            contentDescription = stringResource(R.string.delete),
            tint = primaryColor,
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    showDialog.value = true
                    selectedProduct.value = it?.id ?: ""
                }
        )
    }
}

@Composable
fun CustomStatusBox(modifier: Modifier, it: ProductsItem?) {
    Box(
        modifier = modifier
            .height(25.dp)
            .width(75.dp)
            .background(
                Color(if (it?.status.equals("active")) 0xffe1f7e6 else 0xfffff7d9),
                shape = RoundedCornerShape(20)
            )
            .padding(horizontal = 10.dp),

        contentAlignment = Alignment.Center
    ) {
        Text(
            it?.status ?: "",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color(if (it?.status.equals("active")) 0xff68d583 else 0xffffd946)
            )
        )

    }
}

@Composable
private fun CustomProductCard(
    modifier: Modifier,
    it: ProductsItem?
) {
    Card(
        modifier = modifier
            .background(Color.White, shape = RoundedCornerShape(12))
            .height(250.dp)
            .fillMaxWidth(),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.Black,
            disabledContentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = rememberAsyncImagePainter(
                    it?.image?.src ?: ""
                ),
                contentDescription = stringResource(R.string.product_image),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(160.dp)
                    .padding(horizontal = 6.dp)
            )
            Column(
                Modifier
                    .weight(1f)
                    .padding(end = 8.dp, top = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    stringResource(R.string.id, it?.id ?: ""),
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    it?.title ?: "",
                    style = TextStyle(fontSize = 16.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    it?.body_html ?: "",
                    style = TextStyle(color = Color.LightGray),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "${it?.variants?.get(0)?.price} EGP",
                    style = TextStyle()
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomProductInfoRow(it)
            }
        }
    }
}

@Composable
private fun CustomProductInfoRow(
    product: ProductsItem?,
) {
    Row {
        CustomInfoBox(
            text = stringResource(
                R.string.quantity,
                product?.variants?.get(0)?.inventory_quantity ?: ""
            )
        )

        Spacer(modifier = Modifier.width(8.dp))
        CustomInfoBox(text = product?.vendor ?: "")

    }
}

@Composable
fun CustomInfoBox(
    text: String
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(
                secondaryColor,
                shape = RoundedCornerShape(20)
            )
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )
        )

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    KhizanaTheme {
        // ProductsScreen(productsViewModel = productsViewModel)
    }
}