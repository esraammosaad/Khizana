package com.example.khizana.presentation.feature.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.khizana.R

@Composable
fun HomeScreen(modifier: Modifier) {
    val weeklyActivity = listOf(40, 60, 80, 60, 40, 20, 20)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 64.dp, horizontal = 24.dp)
    ) {

        item {

            Text(stringResource(R.string.hello_admin), style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(8.dp))
            Text(stringResource(R.string.welcome_back), style = TextStyle(fontSize = 18.sp, color = Color.Gray))
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .padding()
                    .background(Color(0xffd3e7e5), shape = RoundedCornerShape(12))
                    .height(230.dp)
                    .width(250.dp)
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ActivityChart(data = weeklyActivity)
                    Text(
                        stringResource(R.string.activity),
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    )
                    Text(stringResource(R.string.of_current_week), style = TextStyle(fontSize = 12.sp, color = Color.Gray))

                }


            }

        }


    }
}

