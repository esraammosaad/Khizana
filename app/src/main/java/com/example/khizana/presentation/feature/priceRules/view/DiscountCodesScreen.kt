package com.example.khizana.presentation.feature.priceRules.view

import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.khizana.R
import com.example.khizana.domain.model.DiscountCodeDomain
import com.example.khizana.presentation.feature.priceRules.view.components.DiscountCodeInputDialog
import com.example.khizana.presentation.feature.priceRules.viewModel.PriceRuleViewModel
import com.example.khizana.ui.theme.lightGreyColor
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.utilis.CustomLoadingIndicator
import com.example.khizana.utilis.Response

@Composable
fun DiscountCodeScreen(priceRuleViewModel: PriceRuleViewModel, priceRuleId: String) {

    val discountCodes = priceRuleViewModel.discountCodes.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        priceRuleViewModel.getDiscountCodes(priceRuleId)
        priceRuleViewModel.message.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    LazyColumn(
        Modifier
            .systemBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Icon(
                    Icons.Default.KeyboardArrowLeft,
                    contentDescription = stringResource(R.string.back_icon),
                    tint = Color.Black.copy(0.7f),
                    modifier = Modifier
                        .clickable {

                        }
                )
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add",
                    modifier = Modifier.clickable {

                        showDialog.value = true

                    }
                )

            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Discount Codes",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        when (discountCodes) {
            is Response.Success<*> -> {
                discountCodes as Response.Success<DiscountCodeDomain>
                items(discountCodes.result?.discount_codes?.size ?: 0,
                    key = { discountCodes.result?.discount_codes?.get(it)?.id ?: "" }) {
                    DiscountCodeCard(
                        code = discountCodes.result?.discount_codes?.get(it)?.code ?: "",
                        usageCount = discountCodes.result?.discount_codes?.get(it)?.usage_count
                            ?: 0
                    )
                }
            }

            is Response.Failure -> {
                item {
                    Text(text = discountCodes.exception)
                }
            }

            is Response.Loading -> {
                item {
                    CustomLoadingIndicator(modifier = Modifier.fillParentMaxSize())
                }
            }
        }
    }

    DiscountCodeInputDialog(
        showDialog = showDialog,
        onConfirm = {
            priceRuleViewModel.createDiscountCode(priceRuleId, it)
        }
    )
}

@Composable
fun DiscountCodeCard(
    code: String = "SAVE20",
    usageCount: Int = 15
) {
    Column(
        modifier = Modifier
            .padding(bottom = 12.dp)
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
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Usage Count:",
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
    }
}

fun Modifier.dashedBorder(
    color: Color,
    strokeWidth: Dp,
    cornerRadius: Dp,
    dashLength: Float = 10f,
    gapLength: Float = 10f
): Modifier = this.then(
    Modifier.drawBehind {
        val stroke = Stroke(
            width = strokeWidth.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, gapLength), 0f)
        )

        val width = size.width
        val height = size.height
        val radius = cornerRadius.toPx()

        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(0f, 0f, width, height),
                    cornerRadius = CornerRadius(radius, radius)
                )
            )
        }

        drawPath(path = path, color = color, style = stroke)
    }
)

