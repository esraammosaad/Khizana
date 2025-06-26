package com.example.khizana.presentation.feature.products.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.khizana.R
import com.example.khizana.domain.model.ProductsItem
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.ui.theme.secondaryColor

@Composable
fun CustomDeleteIcon(
    modifier: Modifier,
    showDialog: MutableState<Boolean>,
    selectedProduct: MutableState<String>,
    it: ProductsItem?,
    isConnected: MutableState<Boolean>,
    showWarningDialog: MutableState<Boolean>
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
                    if (isConnected.value) {
                        showDialog.value = true
                        selectedProduct.value = it?.id ?: ""
                    } else {
                        showWarningDialog.value = true
                    }
                }
        )
    }
}
