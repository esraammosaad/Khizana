package com.example.khizana.presentation.feature.products.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.khizana.R
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.ui.theme.secondaryColor

@Composable
fun CustomTextArea(
    value: MutableState<String>,
) {
    OutlinedTextField(
        label = {
            Text(
                text = stringResource(R.string.product_description),
                color = Color.Black,
            )
        },
        enabled = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(bottom = 12.dp),
        value = value.value,
        singleLine = false,
        maxLines = 10,
        onValueChange = {
            value.value = it
        },
        shape = RoundedCornerShape(15),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = secondaryColor,
            disabledBorderColor = secondaryColor,
            unfocusedBorderColor = secondaryColor,
            errorBorderColor = primaryColor,
            errorTextColor = primaryColor,
            errorCursorColor = primaryColor,
            errorPlaceholderColor = primaryColor,
            cursorColor = primaryColor
        ),
    )
}