package com.example.khizana.presentation.feature.products.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.khizana.domain.model.ProductsItem
import com.example.khizana.presentation.feature.products.viewModel.ProductsViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartialBottomSheet(
    showBottomSheet: MutableState<Boolean>,
    productsViewModel: ProductsViewModel,
    onAddClicked: () -> Unit,
    product: ProductsItem?,
    isEditable: Boolean,
    productId: String? = null
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showBottomSheet.value) {
            ModalBottomSheet(
                containerColor = Color.White,
                dragHandle = null,
                modifier = Modifier.wrapContentSize(),
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet.value = false },
            ) {
               AddProductScreen(productsViewModel = productsViewModel, showBottomSheet = showBottomSheet, product = product, isEditable = isEditable, productId = productId)
            }

        }
    }
}