package com.example.khizana.presentation.feature.inventory.view.components

import androidx.compose.foundation.layout.Arrangement
import com.example.khizana.presentation.feature.products.view.components.CustomTextField

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.khizana.R
import com.example.khizana.domain.model.InventoryItem
import com.example.khizana.domain.model.InventoryItemRequestDomain
import com.example.khizana.domain.model.InventoryLevelRequestDomain
import com.example.khizana.presentation.feature.inventory.viewModel.InventoryViewModel
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.ui.theme.secondaryColor
import com.example.khizana.utilis.Response

@Composable
fun InventoryItemInputDialog(
    showDialog: MutableState<Boolean>,
    inventoryItemId: String,
    productQuantity: String,
    inventoryViewModel: InventoryViewModel = hiltViewModel(),
    onConfirm: (InventoryLevelRequestDomain, InventoryItemRequestDomain) -> Unit,

    ) {
    val quantity = remember { mutableStateOf("") }
    val cost = remember { mutableStateOf("") }
    val isTracked = remember { mutableStateOf(false) }
    val error = remember { mutableStateOf(false) }
    val errorText = remember { mutableStateOf("") }
    val errorCost = remember { mutableStateOf(false) }
    val errorCostText = remember { mutableStateOf("") }
    val errorTrack = remember { mutableStateOf(false) }
    val errorTrackText = remember { mutableStateOf("") }
    val inventoryItem = inventoryViewModel.inventoryItem.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    if (inventoryItemId.isNotEmpty()) {
        LaunchedEffect(inventoryItemId) {
            inventoryViewModel.getInventoryItem(inventoryItemId)
            quantity.value = productQuantity
        }
        when (inventoryItem) {
            is Response.Failure -> {
                cost.value = inventoryItem.exception
            }

            Response.Loading -> {
                cost.value = "loading"
            }

            is Response.Success<*> -> {
                inventoryItem as Response.Success<InventoryItemRequestDomain>
                cost.value = inventoryItem.result?.inventoryItem?.cost ?: ""
                isTracked.value = inventoryItem.result?.inventoryItem?.tracked ?: false
            }
        }
    }


    if (showDialog.value) {
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = { showDialog.value = false },
            title = {
                Text(stringResource(R.string.edit_inventory_item))
            },
            text = {
                Column {
                    CustomTextField(
                        value = quantity,
                        label = stringResource(R.string.quantity),
                        error = error.value,
                        errorMessage = errorText.value
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CustomTextField(
                        value = cost,
                        label = stringResource(R.string.cost),
                        error = errorCost.value,
                        errorMessage = errorCostText.value
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            colors = CheckboxDefaults.colors(
                                checkedColor = primaryColor,
                                uncheckedColor = primaryColor
                            ),
                            checked = isTracked.value,
                            onCheckedChange = { isTracked.value = it },
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(stringResource(R.string.tracked))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    if (errorTrack.value)
                        Text(
                            errorTrackText.value,
                            style = TextStyle(
                                color = Color.Red,
                                fontWeight = FontWeight.Bold
                            )
                        )
                }
            },
            confirmButton = {
                Button(
                    onClick = {

                        if (cost.value.isEmpty() || cost.value == "null") {
                            errorCost.value = true
                            errorCostText.value = context.getString(R.string.please_enter_cost)
                            return@Button
                        } else {
                            val newCost = cost.value.toDoubleOrNull()
                            if (newCost == null) {
                                errorCost.value = true
                                errorCostText.value =
                                    context.getString(R.string.please_enter_valid_cost)
                                return@Button
                            } else {
                                errorCost.value = false
                                errorCostText.value = ""
                            }
                        }
                        if (isTracked.value) {
                            errorTrack.value = false
                            errorTrackText.value = ""
                        } else {
                            errorTrack.value = true
                            errorTrackText.value = context.getString(R.string.please_check_tracked)
                            return@Button
                        }
                        if (quantity.value.isEmpty()) {
                            error.value = true
                            errorText.value = context.getString(R.string.please_add_new_quantity)
                            return@Button
                        } else {
                            val newQuantity = quantity.value.toIntOrNull()
                            if (newQuantity == null) {
                                error.value = true
                                errorText.value =
                                    context.getString(R.string.please_enter_valid_quantity)
                                return@Button
                            } else {
                                error.value = false
                                errorText.value = ""
                            }
                        }
                        if (!error.value) {
                            val inventoryLevel = InventoryLevelRequestDomain(
                                available = quantity.value.toInt(),
                                inventory_item_id = inventoryItemId,
                                available_adjustment = 0,
                                location_id = "70409781361"
                            )
                            val inventoryItem = InventoryItemRequestDomain(
                                inventoryItem = InventoryItem(
                                    cost = cost.value,
                                    tracked = isTracked.value
                                )
                            )
                            onConfirm(inventoryLevel, inventoryItem)
                            showDialog.value = false
                        }
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
                    onClick = {
                        showDialog.value = false
                        error.value = false
                        errorText.value = ""
                        errorCost.value = false
                        errorCostText.value = ""
                        errorTrack.value = false
                        errorTrackText.value = ""
                    },
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