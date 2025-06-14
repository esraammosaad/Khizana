package com.example.khizana.presentation.feature.products.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.khizana.R
import com.example.khizana.domain.model.VariantsItem
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.ui.theme.secondaryColor

@Composable
fun VariantInputDialog(
    showDialog: MutableState<Boolean>,
    onConfirm: (VariantsItem) -> Unit
) {
    val variantTitle = remember { mutableStateOf("") }
    val variantPrice = remember { mutableStateOf("") }
    val variantQuantity = remember { mutableStateOf("") }
    val optionOne = remember { mutableStateOf("") }
    val optionTwo = remember { mutableStateOf("") }
    val optionThree = remember { mutableStateOf("") }
    val error = remember { mutableStateOf(false) }
    val errorText = remember { mutableStateOf("") }

    if (showDialog.value) {
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = { showDialog.value = false },
            title = {

                Text("Add Variant to your product")
            },
            text = {
                Column {
                    CustomTextField(
                        value = variantTitle,
                        label = stringResource(R.string.variant_title)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CustomTextField(
                        value = variantPrice,
                        label = stringResource(R.string.variant_price)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CustomTextField(
                        value = variantQuantity,
                        label = stringResource(R.string.variant_quantity)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CustomTextField(
                        value = optionOne,
                        label = stringResource(R.string.option_one)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CustomTextField(
                        value = optionTwo,
                        label = stringResource(R.string.option_two),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CustomTextField(
                        value = optionThree,
                        label = stringResource(R.string.option_three),
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (error.value)
                        Text(
                            errorText.value,
                            color = Color.Red,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (variantTitle.value.isEmpty()) {
                            error.value = true
                            errorText.value = "Please Fill Title Field"
                            return@Button
                        }
                        if (variantPrice.value.isEmpty()) {
                            error.value = true
                            errorText.value = "Please Fill Price Field"
                            return@Button
                        }
                        if (variantPrice.value.toDoubleOrNull() == null) {
                            error.value = true
                            errorText.value = "Please Enter Valid Price"
                            return@Button
                        }
                        if (variantQuantity.value.isEmpty()) {
                            error.value = true
                            errorText.value = "Please Fill Quantity Field"
                            return@Button
                        }
                        if (variantQuantity.value.toIntOrNull() == null) {
                            error.value = true
                            errorText.value = "Please Enter Valid Quantity"
                            return@Button
                        }
                        if (optionOne.value.isEmpty()) {
                            error.value = true
                            errorText.value = "Please Add at least one option"
                            return@Button
                        }
                        val variant = VariantsItem(
                            title = variantTitle.value,
                            price = variantPrice.value,
                            option3 = optionThree.value.ifEmpty { null },
                            option1 = optionOne.value,
                            option2 = optionTwo.value.ifEmpty { null },
                            inventory_quantity = variantQuantity.value.toIntOrNull() ?: 0,
                            inventory_item_id = ""
                        )
                        onConfirm(variant)
                        variantTitle.value = ""
                        variantPrice.value = ""
                        variantQuantity.value = ""
                        optionOne.value = ""
                        optionTwo.value = ""
                        optionThree.value = ""
                        showDialog.value = false
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = secondaryColor,
                        containerColor = primaryColor
                    )
                ) {
                    Text(
                        stringResource(R.string.add)
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