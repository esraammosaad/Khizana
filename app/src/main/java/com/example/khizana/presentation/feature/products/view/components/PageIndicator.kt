package com.example.khizana.presentation.feature.products.view.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.khizana.ui.theme.primaryColor

@Composable
fun <T> PageIndicator(items:List<T>,currentPage:Int){
    Row(Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center) {
        repeat(items.size){
            val width= animateDpAsState(targetValue =if( it==currentPage) 25.dp else 8.dp,
                label = "",
            )

            Box (modifier = Modifier
                .padding(3.dp)
                .height(5.dp)
                .width(width.value)
                .clip(CircleShape)
                .background(
                    if (it == currentPage) primaryColor else Color.Gray
                ))
            {
            }
        }
    }
}