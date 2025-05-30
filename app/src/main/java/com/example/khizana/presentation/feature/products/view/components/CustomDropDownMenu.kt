package com.example.khizana.presentation.feature.products.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import com.example.khizana.ui.theme.secondaryColor

@Composable
fun CustomDropDownMenu(
    expanded: MutableState<Boolean>,
    productVendor: MutableState<String>
) {
    val options = listOf("ADIDAS", "NIKE", "TIMBERLAND")
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .background(
                secondaryColor
            )
    ) {
        options.forEach { option ->
            DropdownMenuItem(
                text = { Text(option) },
                onClick = {
                    productVendor.value = option
                    expanded.value = false
                }
            )
        }
    }
}
