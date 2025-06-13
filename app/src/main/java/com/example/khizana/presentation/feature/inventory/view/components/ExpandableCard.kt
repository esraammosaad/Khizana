package com.example.khizana.presentation.feature.inventory.view.components

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.khizana.R
import com.example.khizana.domain.model.ProductsItem
import com.example.khizana.ui.theme.offWhiteColor
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.utilis.CustomDivider

@Composable
fun ExpandableCard(
    product: ProductsItem?,
    showDialog: MutableState<Boolean>,
    inventoryItemId: MutableState<String>,
    productQuantity: MutableState<String>
) {
    val expandedState = rememberSaveable { mutableStateOf(false) }
    val rotationState = animateFloatAsState(
        targetValue = if (expandedState.value) 180f else 0f
    )
    Card(
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.Black,
            disabledContentColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = RoundedCornerShape(25.dp),
        onClick = {
            expandedState.value = !expandedState.value
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            product?.image?.src,
                        ),
                        contentDescription = stringResource(R.string.product_image),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .width(120.dp)
                            .height(100.dp)
                            .padding(vertical = 5.dp)
                    )
                    Text(
                        product?.title ?: "", style = TextStyle(
                            fontSize = 14.sp,
                        ),
                        maxLines = 2,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                }
                IconButton(
                    onClick = {
                        expandedState.value = !expandedState.value
                    },
                    modifier = Modifier
                        .size(50.dp)
                        .rotate(rotationState.value)
                ) {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }

            if (expandedState.value) {
                CustomDivider()
                product?.variants?.forEach { variant ->
                    val title = listOfNotNull(
                        variant?.option1,
                        variant?.option2,
                        variant?.option3
                    ).joinToString(" / ")
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(2.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = offWhiteColor)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = title,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp
                                )
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = "edit icon",
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clickable {
                                            inventoryItemId.value =
                                                variant?.inventory_item_id.toString()
                                            productQuantity.value =
                                                variant?.inventory_quantity.toString()
                                            Log.i(
                                                "TAG",
                                                "ExpandableCard: ${productQuantity.value} ${inventoryItemId.value}"
                                            )
                                            showDialog.value = true
                                        },
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Price: ${variant?.price} EGP",
                                fontSize = 16.sp,
                                color = primaryColor
                            )
                            Text(
                                text = "Quantity: ${variant?.inventory_quantity}",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}