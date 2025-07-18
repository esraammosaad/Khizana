package com.example.khizana.presentation.feature.products.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.ui.theme.secondaryColor
import java.lang.Error

@Composable
fun CustomTextField(
    value: MutableState<String>,
    label: String,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit = {
        value.value = it
    },
    error: Boolean = false,
    errorMessage: String = "",
    isPassword: MutableState<Boolean> = mutableStateOf(false)
) {
    OutlinedTextField(
        label = {
            Text(
                text = label,
                color = Color.Black,
            )
        },
        enabled = true,
        modifier = Modifier
            .fillMaxWidth(),
        value = value.value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(25),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = secondaryColor,
            disabledBorderColor = secondaryColor,
            unfocusedBorderColor = secondaryColor,
            errorBorderColor = Color.Red,
            errorTextColor = Color.Red,
            errorCursorColor = Color.Red,
            errorPlaceholderColor = Color.Red,
            cursorColor = primaryColor
        ),
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        isError = error,
        visualTransformation = if (isPassword.value) PasswordVisualTransformation() else VisualTransformation.None,
        supportingText = {
            if (error) {
                Text(
                    text = errorMessage,
                )
            }
        }
    )
}