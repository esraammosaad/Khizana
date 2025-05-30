package com.example.khizana.presentation.feature.products.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import coil.compose.AsyncImage
import com.example.khizana.R
import com.example.khizana.presentation.feature.products.viewModel.ProductsViewModel
import com.example.khizana.ui.theme.primaryColor
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductDetailsScreen(productId: String, productsViewModel: ProductsViewModel) {

    LaunchedEffect(Unit) {
        productsViewModel.getProductById(productId = productId)
    }
    val product = productsViewModel.product.observeAsState().value
    val pagerState = rememberPagerState(
        pageCount = { product?.product?.images?.size ?: 0 },
        initialPage = 0,
    )


    LaunchedEffect(pagerState.currentPage) {
        while (true) {
            delay(3000)
            val nextPage =
                if (pagerState.currentPage + 1 == pagerState.pageCount) 0 else pagerState.currentPage + 1
            pagerState.scrollToPage(nextPage)
        }
    }
    val showBottomSheet = remember { mutableStateOf(false) }

    LazyColumn(Modifier.systemBarsPadding()) {


        item {
            PartialBottomSheet(
                showBottomSheet = showBottomSheet,
                productsViewModel = productsViewModel,
                onAddClicked = {},
                product = product?.product
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.back_icon),
                    tint = Color.Black.copy(0.7f),
                    modifier = Modifier
                        .size(35.dp)
                        .clickable {

                        }
                )

                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.back_icon),
                    tint = Color.Black.copy(0.7f),
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            showBottomSheet.value = true
                        }
                )
            }
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(
                        RoundedCornerShape(
                            bottomStart = 32.dp,
                            bottomEnd = 32.dp
                        )
                    )
                    .background(color = Color.White),
                contentAlignment = Alignment.TopEnd,
            ) {
                if (product?.product?.images?.isNotEmpty() == true) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.wrapContentSize()
                    ) { index ->
                        CustomProductImage(productImage = product.product.images[index]?.src)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.End) {
                    CustomStatusBox(Modifier, product?.product)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(Modifier.padding(16.dp)) {
                Text(
                    "ID: #${product?.product?.id}", style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Row(Modifier.padding(vertical = 12.dp)) {
                    CustomInfoBox(
                        text =
                        product?.product?.vendor ?: ""
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    CustomInfoBox(
                        text =
                        product?.product?.product_type ?: ""
                    )
                }
                Text(
                    product?.product?.title ?: "", style = TextStyle(
                        fontSize = 18.sp,
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "EGP ${product?.product?.variants?.get(0)?.price}", style = TextStyle(
                        fontSize = 22.sp,
                        color = primaryColor,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Image(
                        painter = painterResource(R.drawable.baseline_access_time_24),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        "${product?.product?.variants?.get(0)?.inventory_quantity} items left",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color(0xffB60000),
                        )
                    )
                }
                HorizontalDivider(
                    color = Color.Gray.copy(alpha = 0.3f),
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                )
                Text(
                    text = product?.product?.body_html ?: "",
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.LightGray
                    ),
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(R.string.product_options),
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
                Spacer(modifier = Modifier.height(5.dp))

                product?.product?.options?.forEach { option ->
                    Text(
                        text = "${option?.name}: ${option?.values?.joinToString(", ")}",
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = stringResource(R.string.variants),
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
                Spacer(modifier = Modifier.height(5.dp))
                product?.product?.variants?.forEach { variant ->
                    val title = listOfNotNull(
                        variant?.option1,
                        variant?.option2,
                        variant?.option3
                    ).joinToString(" / ")
                    Text(
                        text = "$title → ${variant?.price} EGP | Qty: ${variant?.inventory_quantity}",
                        fontSize = 18.sp
                    )
                }
            }
        }
    }

}

@Composable
private fun CustomProductImage(productImage: String?) {
    AsyncImage(
        model = productImage ?: "",
        contentDescription = stringResource(R.string.product_image),
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clip(RoundedCornerShape(16.dp))
    )
}


