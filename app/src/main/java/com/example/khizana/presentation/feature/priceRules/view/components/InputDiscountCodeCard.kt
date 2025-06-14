package com.example.khizana.presentation.feature.priceRules.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.khizana.ui.theme.lightGreyColor
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.utilis.dashedBorder

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
