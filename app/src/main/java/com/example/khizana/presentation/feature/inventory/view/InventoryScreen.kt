package com.example.khizana.presentation.feature.inventory.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.presentation.feature.inventory.view.components.ExpandableCard
import com.example.khizana.presentation.feature.inventory.view.components.InventoryItemInputDialog
import com.example.khizana.presentation.feature.inventory.viewModel.InventoryViewModel
import com.example.khizana.utilis.CustomLoadingIndicator
import com.example.khizana.utilis.Response


@Composable
fun InventoryScreen(inventoryViewModel: InventoryViewModel = hiltViewModel()) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        inventoryViewModel.getProducts()
        inventoryViewModel.message.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
    val products = inventoryViewModel.products.collectAsStateWithLifecycle().value
    val showDialog = remember { mutableStateOf(false) }
    val inventoryItemId = remember { mutableStateOf("") }
    val productQuantity = remember { mutableStateOf("") }


    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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
                        productQuantity = productQuantity
                    )
                    Spacer(modifier = Modifier.height(16.dp))
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



