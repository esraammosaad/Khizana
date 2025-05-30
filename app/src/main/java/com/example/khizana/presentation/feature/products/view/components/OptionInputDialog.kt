package com.example.khizana.presentation.feature.products.view.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.khizana.domain.model.OptionsItem
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.ui.theme.secondaryColor

@Composable
fun OptionInputDialog(
    showDialog: MutableState<Boolean>,
    onConfirm: (OptionsItem) -> Unit
) {
    val optionName = remember { mutableStateOf("") }
    val optionValue = remember { mutableStateOf("") }
    val optionValues = remember { mutableStateOf<List<String>>(emptyList()) }
    val error = remember { mutableStateOf(false) }
    val errorText = remember { mutableStateOf("") }

    if (showDialog.value) {
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = { showDialog.value = false },
            title = {

                Text("Add Option to your product")
            },
            text = {
                Column {
                    CustomTextField(
                        value = optionName,
                        label = "Option Name"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CustomTextField(
                        value = optionValue,
                        label = "Option Value",
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    optionValues.value = optionValues.value.plus(optionValue.value)
                                    optionValue.value = ""
                                },
                                content = {
                                    Icon(Icons.Default.Add, contentDescription = "Add")
                                }
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = optionValues.value.joinToString(", "),
                            fontSize = 18.sp
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
                        if (optionName.value.isEmpty()) {
                            error.value = true
                            errorText.value = "Please Fill Option Name Field"
                            return@Button
                        }
                        if (optionValues.value.isEmpty()) {
                            error.value = true
                            errorText.value = "Please Fill Option Value Field"
                            return@Button
                        }
                        val option = OptionsItem(
                            name = optionName.value,
                            values = optionValues.value
                        )
                        onConfirm(option)
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