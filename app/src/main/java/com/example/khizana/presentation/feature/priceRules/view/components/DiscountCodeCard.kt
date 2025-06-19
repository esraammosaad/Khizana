package com.example.khizana.presentation.feature.priceRules.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.khizana.R
import com.example.khizana.ui.theme.lightGreyColor
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.utilis.dashedBorder

@Composable
fun DiscountCodeCard(
    code: String,
    usageCount: Int,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier

    ) {
    Column(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(lightGreyColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .dashedBorder(
                    color = primaryColor,
                    strokeWidth = 1.dp,
                    cornerRadius = 8.dp
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = code,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black,
                letterSpacing = 2.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.usage_count),
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = usageCount.toString(),
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
            }
            Row {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_icon),
                    tint = Color.Gray,
                    modifier = Modifier.clickable {
                        onEditClick.invoke()
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete_icon),
                    tint = Color.Gray,
                    modifier = Modifier.clickable {
                        onDeleteClick.invoke()
                    }
                )

            }
        }
    }
}