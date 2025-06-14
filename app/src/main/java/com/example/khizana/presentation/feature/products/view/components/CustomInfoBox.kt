package com.example.khizana.presentation.feature.products.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.ui.theme.secondaryColor

@Composable
fun CustomInfoBox(
    text: String
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(
                secondaryColor,
                shape = RoundedCornerShape(20)
            )
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )
        )

    }
}
