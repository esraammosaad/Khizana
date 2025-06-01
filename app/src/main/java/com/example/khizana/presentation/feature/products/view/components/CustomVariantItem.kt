package com.example.khizana.presentation.feature.products.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.khizana.domain.model.VariantsItem
import com.example.khizana.ui.theme.primaryColor

@Composable
fun CustomVariantItem(variantList: MutableState<List<VariantsItem>>) {
    variantList.value.forEach { variant ->
        val title = listOfNotNull(
            variant.option1,
            variant.option2,
            variant.option3
        ).joinToString(" / ")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$title : ${variant.price} EGP | Qty: ${variant.inventory_quantity}",
                fontSize = 18.sp
            )
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = "Delete",
                tint = primaryColor,
                modifier = Modifier.clickable {
                    variantList.value = variantList.value.minus(variant)
                }
            )

        }
    }
}