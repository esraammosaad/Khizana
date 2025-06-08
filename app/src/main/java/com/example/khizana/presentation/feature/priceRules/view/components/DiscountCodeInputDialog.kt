package com.example.khizana.presentation.feature.priceRules.view.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.khizana.R
import com.example.khizana.domain.model.DiscountCodeRequestDomain
import com.example.khizana.domain.model.DiscountCode
import com.example.khizana.presentation.feature.priceRules.view.dashedBorder
import com.example.khizana.ui.theme.lightGreyColor
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.ui.theme.secondaryColor

@Composable
fun DiscountCodeInputDialog(
    showDialog: MutableState<Boolean>,
    onConfirm: (DiscountCodeRequestDomain) -> Unit,
    code: MutableState<String>,
) {
    val discountCode = remember { mutableStateOf("") }
    val error = remember { mutableStateOf(false) }
    val errorText = remember { mutableStateOf("") }

    LaunchedEffect(code.value) {
        if (code.value.isNotEmpty()) {
            discountCode.value = code.value
        }
    }
    if (showDialog.value) {
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = { showDialog.value = false },
            title = {

                Text("Add Discount Code!")
            },
            text = {
                Column {
                    InputDiscountCodeCard(
                        code = discountCode
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
                        if (discountCode.value.isEmpty()) {
                            error.value = true
                            errorText.value = "Please enter a discount code"
                            return@Button
                        }
                        if (discountCode.value.isNotEmpty()) {
                            error.value = false
                            errorText.value = ""
                            onConfirm(
                                DiscountCodeRequestDomain(
                                    DiscountCode(
                                        discountCode.value
                                    )
                                )
                            )
                            showDialog.value = false
                            discountCode.value = ""
                            code.value = ""
                        }
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = secondaryColor,
                        containerColor = primaryColor
                    )
                ) {
                    Text(
                        "Save"
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

@Composable
fun InputDiscountCodeCard(
    code: MutableState<String>,
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(lightGreyColor)
            .padding(12.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .dashedBorder(
                    color = primaryColor,
                    strokeWidth = 1.dp,
                    cornerRadius = 8.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            TextField(
                value = code.value,
                onValueChange = {
                    code.value = it
                },
                textStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    letterSpacing = 2.sp,
                    color = Color.Black,
                ),
                placeholder = { Text("Enter Code", color = Color.Gray) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    errorTextColor = Color.Transparent,
                    errorCursorColor = Color.Transparent,
                    errorPlaceholderColor = Color.Transparent,
                    cursorColor = primaryColor
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}
