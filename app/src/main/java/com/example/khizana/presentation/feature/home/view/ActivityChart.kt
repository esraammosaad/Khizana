package com.example.khizana.presentation.feature.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.khizana.ui.theme.lighterPrimaryColor

@Composable
fun ActivityChart(
    data: List<Int>,
    modifier: Modifier = Modifier
) {
    val maxValue = data.maxOrNull()?.takeIf { it > 0 } ?: 1
    val chartHeight = 105.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(chartHeight + 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        data.forEach { value ->
            val heightRatio = value.toFloat() / maxValue
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(if (value == 0) 8.dp else chartHeight * heightRatio)
                        .background(lighterPrimaryColor, shape = RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = value.toString(),
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                )
            }
        }
    }
}