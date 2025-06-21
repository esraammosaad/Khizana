package com.example.khizana.presentation.feature.home.view.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.utilis.Response

@Composable
fun CustomBox(
    modifier: Modifier = Modifier,
    color: Color,
    textOne: String,
    textTwo: Response,
    icon: Int
) {
    Box(
        modifier = modifier
            .background(color, shape = RoundedCornerShape(12))
            .height(120.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,

            ) {
            Image(painter = painterResource(icon), contentDescription = "")
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                textOne,
                style = TextStyle(fontSize = 16.sp, color = Color.Gray)
            )
            Spacer(modifier = Modifier.height(5.dp))
            when (textTwo) {
                is Response.Success<*> -> {
                    textTwo as Response.Success<Double>
                    Text(
                        String.format("%.2f", textTwo.result) + " EGP",
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    )
                }

                is Response.Failure -> {
                    Text(textTwo.exception)
                }

                is Response.Loading -> {
                    CircularProgressIndicator(
                        color = primaryColor,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}