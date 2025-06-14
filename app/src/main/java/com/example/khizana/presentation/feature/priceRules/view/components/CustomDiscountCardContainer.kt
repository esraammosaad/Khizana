package com.example.khizana.presentation.feature.priceRules.view.components

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.khizana.R
import com.example.khizana.ui.theme.lightGreyColor

@Composable
fun DiscountCard(
    title: String,
    discount: String,
    barcode: String,
    startAt: String,
    endAt: String,
    modifier: Modifier,
    onDeleteIconClicked: () -> Unit
) {

    Box(contentAlignment = Alignment.TopStart, modifier = modifier) {
        CustomDiscountCard(discount, title, barcode, startAt, endAt)
        Box(
            modifier = Modifier
                .padding(horizontal = 18.dp, vertical = 8.dp)
                .size(25.dp)
                .background(
                    color = lightGreyColor,
                    shape = RoundedCornerShape(50)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = stringResource(R.string.delete),
                tint = Color.Black,
                modifier = Modifier
                    .size(15.dp)
                    .clickable {
                        onDeleteIconClicked.invoke()
                    }
            )
        }
    }
}