package com.example.khizana.presentation.feature.products.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.khizana.R
import com.example.khizana.domain.model.ImagesItem
import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.presentation.feature.products.view.components.CustomInfoBox
import com.example.khizana.presentation.feature.products.view.components.CustomProductImage
import com.example.khizana.presentation.feature.products.view.components.CustomStatusBox
import com.example.khizana.presentation.feature.products.view.components.PageIndicator
import com.example.khizana.presentation.feature.products.viewModel.ProductsViewModel
import com.example.khizana.ui.theme.offWhiteColor
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.ui.theme.secondaryColor
import com.example.khizana.utilis.CustomDivider
import com.example.khizana.utilis.CustomLoadingIndicator
import com.example.khizana.utilis.Response
import com.example.khizana.utilis.WarningDialog
import kotlinx.coroutines.delay


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductDetailsScreen(
    productId: String,
    productsViewModel: ProductsViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState,
    navigationController: NavController,
    isConnected: Boolean,
    onConfirmation: () -> Unit,

    ) {
    LaunchedEffect(Unit) {
        productsViewModel.getProductById(productId = productId)
        productsViewModel.message.collect {
            snackBarHostState.showSnackbar(it)
        }
    }
    val product = productsViewModel.product.collectAsStateWithLifecycle().value
    var productImagesList: MutableList<ImagesItem?> = mutableListOf()
    val size = remember { mutableIntStateOf(productImagesList.size) }
    val pagerState = rememberPagerState(
        pageCount = { size.intValue },
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
    val showWarningDialog = rememberSaveable { mutableStateOf(false) }
    WarningDialog(
        onConfirmation = onConfirmation,
        dialogText = stringResource(R.string.there_is_no_internet_connection_you_can_t_add_anything_right_now),
        dialogTitle = stringResource(R.string.warning),
        confirmText = stringResource(R.string.wifi_settings),
        showAlert = showWarningDialog
    )
    Box {
        LazyColumn(
            Modifier
                .systemBarsPadding()
                .background(offWhiteColor)
        ) {
            when (product) {
                is Response.Success<*> -> {
                    product as Response.Success<ProductRequestDomain>
                    productImagesList =
                        product.result?.product?.images?.toMutableList() ?: mutableListOf()
                    size.intValue = productImagesList.size
                    item {
                        PartialBottomSheet(
                            showBottomSheet = showBottomSheet,
                            productsViewModel = productsViewModel,
                            product = product.result?.product,
                            isEditable = true,
                            productId = productId
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            IconButton(onClick = {
                                navigationController.popBackStack()
                            }) {
                                Icon(
                                    Icons.Filled.ArrowBack,
                                    contentDescription = stringResource(R.string.back_icon),
                                    tint = Color.Black.copy(0.7f),
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .wrapContentSize()
                                .background(color = Color.White),
                            contentAlignment = Alignment.TopEnd,
                        ) {
                            if (product.result?.product?.images?.isNotEmpty() == true) {
                                HorizontalPager(
                                    state = pagerState,
                                    modifier = Modifier
                                        .wrapContentSize()
                                        .padding(top = 5.dp)
                                ) { index ->
                                    CustomProductImage(
                                        productImage = productImagesList[index]?.src ?: ""
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                            Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.End) {
                                CustomStatusBox(Modifier, product.result?.product)
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Column(Modifier.padding(16.dp)) {
                            PageIndicator(
                                items = product.result?.product?.images ?: listOf(),
                                currentPage = pagerState.currentPage
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "ID: #${product.result?.product?.id}", style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Row(Modifier.padding(vertical = 12.dp)) {
                                CustomInfoBox(
                                    text =
                                    product.result?.product?.vendor ?: ""
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                CustomInfoBox(
                                    text =
                                    product.result?.product?.product_type ?: ""
                                )
                            }
                            Text(
                                ("#Tags: " + product.result?.product?.tags), style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color.Gray,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                product.result?.product?.title ?: "", style = TextStyle(
                                    fontSize = 18.sp,
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "EGP ${product.result?.product?.variants?.get(0)?.price}",
                                style = TextStyle(
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
                                    "${product.result?.product?.variants?.get(0)?.inventory_quantity} items left",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        color = Color(0xffB60000),
                                    )
                                )
                            }
                            CustomDivider()
                            Text(
                                text = product.result?.product?.body_html ?: "",
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    color = Color.Gray
                                ),
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Column {
                                Text(
                                    text = stringResource(R.string.options),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                product.result?.product?.options?.forEach { option ->
                                    if (!option.values.isNullOrEmpty()) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .horizontalScroll(rememberScrollState())
                                                .padding(bottom = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "${option.name}:",
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier.padding(bottom = 4.dp)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            option.values.forEach { value ->
                                                Box(
                                                    modifier = Modifier
                                                        .padding(end = 6.dp)
                                                        .background(
                                                            color = secondaryColor,
                                                            shape = RoundedCornerShape(12.dp),
                                                        )
                                                ) {
                                                    Text(
                                                        text = value,
                                                        modifier = Modifier.padding(
                                                            horizontal = 12.dp,
                                                            vertical = 6.dp
                                                        ),
                                                        fontSize = 14.sp,
                                                        color = primaryColor
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(14.dp))
                                Text(
                                    text = stringResource(R.string.variants),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                product.result?.product?.variants?.forEach { variant ->
                                    val title = listOfNotNull(
                                        variant?.option1,
                                        variant?.option2,
                                        variant?.option3
                                    ).joinToString(" / ")
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        elevation = CardDefaults.cardElevation(2.dp),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color.White)
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp)) {
                                            Text(
                                                text = title,
                                                fontWeight = FontWeight.SemiBold,
                                                fontSize = 18.sp
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = "Price: ${variant?.price} EGP",
                                                fontSize = 16.sp,
                                                color = primaryColor
                                            )
                                            Text(
                                                text = "Quantity: ${variant?.inventory_quantity}",
                                                fontSize = 16.sp,
                                                color = Color.Gray
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(60.dp))
                    }
                }

                is Response.Failure -> item {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(product.exception)
                    }
                }

                Response.Loading -> item {
                    CustomLoadingIndicator(modifier = Modifier.fillParentMaxSize())
                }
            }
        }
        FloatingActionButton(
            onClick = {
                if (isConnected) {
                    showBottomSheet.value = true
                } else {
                    showWarningDialog.value = true
                }
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(horizontal = 20.dp, vertical = 42.dp),
            containerColor = Color(0xFF295BBE),
            contentColor = Color.White,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(4.dp)
        ) {
            Icon(Icons.Default.Edit, contentDescription = "Edit Product")
        }
    }
}




