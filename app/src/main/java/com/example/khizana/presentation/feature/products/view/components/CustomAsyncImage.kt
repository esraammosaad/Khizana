package com.example.khizana.presentation.feature.products.view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.khizana.R
import com.example.khizana.ui.theme.primaryColor

@Composable
fun CustomAsyncImage(imageUri: Any, size: Dp = 260.dp) {
    AsyncImage(
        model = imageUri,
        placeholder = painterResource(R.drawable.addimage),
        error = painterResource(R.drawable.addimage),
        contentDescription = "",
        modifier = Modifier
            .border(
                color = primaryColor,
                width = 1.dp,
                shape = RoundedCornerShape(10.dp)
            )
            .size(size)
            .padding(8.dp),
        contentScale = ContentScale.Fit
    )
}