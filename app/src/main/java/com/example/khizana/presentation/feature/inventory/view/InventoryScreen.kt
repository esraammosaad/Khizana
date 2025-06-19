package com.example.khizana.presentation.feature.inventory.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.khizana.R
import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.presentation.feature.inventory.view.components.ExpandableCard
import com.example.khizana.presentation.feature.inventory.view.components.InventoryItemInputDialog
import com.example.khizana.presentation.feature.inventory.viewModel.InventoryViewModel
import com.example.khizana.utilis.CustomLoadingIndicator
import com.example.khizana.utilis.Response
import com.example.khizana.utilis.WarningDialog


@Composable
fun InventoryScreen(
    inventoryViewModel: InventoryViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState,
    isConnected: MutableState<Boolean>,
    onConfirmation: () -> Unit
) {
    LaunchedEffect(Unit) {
        inventoryViewModel.getProducts()
        inventoryViewModel.message.collect {
            snackBarHostState.showSnackbar(it)
        }
    }
    val products = inventoryViewModel.products.collectAsStateWithLifecycle().value
    val showDialog = rememberSaveable { mutableStateOf(false) }
    val inventoryItemId = remember { mutableStateOf("") }
    val productQuantity = remember { mutableStateOf("") }
    val showWarningDialog = rememberSaveable { mutableStateOf(false) }



    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp, start = 16.dp, top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (products) {
            is Response.Success<*> -> {
                products as Response.Success<ProductDomain>
                items(
                    items = products.result?.products.orEmpty(),
                    key = { it?.id ?: "" }
                ) {
                    ExpandableCard(
                        product = it,
                        showDialog = showDialog,
                        inventoryItemId = inventoryItemId,
                        productQuantity = productQuantity,
                        isConnected = isConnected,
                        showWarningDialog = showWarningDialog
                    )
                    Spacer(modifier = Modifier.height(8.dp))
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
    WarningDialog(
        onConfirmation = onConfirmation,
        dialogText = stringResource(R.string.there_is_no_internet_connection_you_can_t_edit_anything_right_now),
        dialogTitle = stringResource(R.string.warning),
        confirmText = stringResource(R.string.wifi_settings),
        showAlert = showWarningDialog
    )
    InventoryItemInputDialog(
        showDialog = showDialog,
        inventoryItemId = inventoryItemId.value,
        productQuantity = productQuantity.value,
        onConfirm = { inventoryLevel, inventoryItem ->
            inventoryViewModel.updateInventoryItemWithQuantity(
                inventoryLevelRequestDomain = inventoryLevel,
                inventoryItemRequestDomain = inventoryItem
            )
        }
    )
}



