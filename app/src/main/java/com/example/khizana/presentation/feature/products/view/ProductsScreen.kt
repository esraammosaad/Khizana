package com.example.khizana.presentation.feature.products.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.presentation.feature.products.viewModel.ProductsViewModel
import com.example.khizana.utilis.ConfirmationDialog
import com.example.khizana.utilis.CustomLoadingIndicator
import com.example.khizana.utilis.NavigationRoutes
import com.example.khizana.utilis.Response
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.khizana.R
import com.example.khizana.domain.model.ProductsItem
import com.example.khizana.presentation.feature.products.view.components.CustomDeleteIcon
import com.example.khizana.presentation.feature.products.view.components.CustomProductCard
import com.example.khizana.presentation.feature.products.view.components.CustomStatusBox
import com.example.khizana.presentation.feature.products.view.components.CustomTextField
import com.example.khizana.utilis.WarningDialog


@Composable
fun ProductsScreen(
    modifier: Modifier = Modifier,
    productsViewModel: ProductsViewModel = hiltViewModel(),
    navigationController: NavHostController,
    snackBarHostState: SnackbarHostState,
    isConnected: MutableState<Boolean>,
    onConfirmation: () -> Unit
) {

    val products = productsViewModel.products.collectAsStateWithLifecycle().value
    val showDialog = remember { mutableStateOf(false) }
    val selectedProduct = remember { mutableStateOf("") }
    val searchText = rememberSaveable { mutableStateOf("") }
    val searchResult = rememberSaveable { mutableStateOf(emptyList<ProductsItem>()) }
    val showWarningDialog = rememberSaveable { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        productsViewModel.getProducts()
        productsViewModel.message.collect {
            snackBarHostState.showSnackbar(it)
        }
    }


    LaunchedEffect(searchText.value) {
        productsViewModel.searchResults.collect {
            searchResult.value = it
        }
    }


    ConfirmationDialog(
        showDialog = showDialog.value,
        onConfirm = {
            productsViewModel.deleteProduct(productId = selectedProduct.value)
        },
        onDismiss = {
            showDialog.value = false
        }
    )
    WarningDialog(
        onConfirmation = onConfirmation,
        dialogText = stringResource(R.string.there_is_no_internet_connection_you_can_t_add_anything_right_now),
        dialogTitle = stringResource(R.string.warning),
        confirmText = stringResource(R.string.wifi_settings),
        showAlert = showWarningDialog
    )
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                CustomTextField(
                    value = searchText,
                    label = "Search",
                    onValueChange = {
                        searchText.value = it
                        productsViewModel.performSearch(it)
                    },
                    errorMessage = "",
                    error = false,
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "")
                    }
                )
            }
            when (products) {
                is Response.Success<*> -> {
                    products as Response.Success<ProductDomain>
                    items(
                        items = if (searchText.value.isNotEmpty()) searchResult.value else products.result?.products.orEmpty(),
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
                                it,
                                isConnected,
                                showWarningDialog
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






