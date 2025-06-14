package com.example.khizana.presentation.feature.products.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.khizana.domain.model.ProductsItem

@Composable
fun CustomStatusBox(modifier: Modifier, it: ProductsItem?) {
    Box(
        modifier = modifier
            .height(25.dp)
            .width(75.dp)
            .background(
                Color(if (it?.status.equals("active")) 0xffe1f7e6 else if (it?.status.equals("draft")) 0xfffff7d9 else 0xffFDDCDC),
                shape = RoundedCornerShape(20)
            )
            .padding(horizontal = 10.dp),

        contentAlignment = Alignment.Center
    ) {
        Text(
            it?.status ?: "",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color(
                    if (it?.status.equals("active")) 0xff68d583 else if (it?.status.equals(
                            "draft"
                        )
                    ) 0xffffd946 else 0xffB52A2A
                )
            )
        )

    }
}