package com.example.khizana.presentation.feature.products.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.khizana.R

@Composable
fun CustomProductImage(productImage: String?) {
    AsyncImage(
        model = productImage ?: "",
        contentDescription = stringResource(R.string.product_image),
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    )
}