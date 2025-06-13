package com.example.khizana.presentation.feature.inventory.view.components

import com.example.khizana.presentation.feature.products.view.components.CustomTextField

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.khizana.R
import com.example.khizana.domain.model.InventoryLevelRequestDomain
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.ui.theme.secondaryColor

@Composable
fun InventoryItemInputDialog(
    showDialog: MutableState<Boolean>,
    inventoryItemId: String,
    productQuantity: String,
    onConfirm: (InventoryLevelRequestDomain) -> Unit,

    ) {
    val quantity = remember { mutableStateOf("") }
    val error = remember { mutableStateOf(false) }
    val errorText = remember { mutableStateOf("") }

    LaunchedEffect(inventoryItemId) {
        quantity.value = productQuantity
    }


    if (showDialog.value) {
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = { showDialog.value = false },
            title = {

                Text("Edit Inventory Item")
            },
            text = {
                Column {
                    CustomTextField(
                        value = quantity,
                        label = "Quantity",
                        error = error.value,
                        errorMessage = errorText.value
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (quantity.value.isEmpty()) {
                            error.value = true
                            errorText.value = "Please Add New Quantity"
                            return@Button
                        }
                        val inventoryLevel = InventoryLevelRequestDomain(
                            available = quantity.value.toInt(),
                            inventory_item_id = inventoryItemId,
                            available_adjustment = 0,
                            location_id = "70409781361"
                        )
                        onConfirm(inventoryLevel)
                        showDialog.value = false
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = secondaryColor,
                        containerColor = primaryColor
                    )
                ) {
                    Text(
                        stringResource(R.string.save)
                    )
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showDialog.value = false },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = primaryColor,
                        containerColor = secondaryColor
                    )
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}