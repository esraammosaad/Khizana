package com.example.khizana.presentation.feature.priceRules.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.khizana.R
import com.example.khizana.presentation.feature.priceRules.viewModel.PriceRuleViewModel
import com.example.khizana.ui.theme.lightGreyColor
import com.example.khizana.utilis.ConfirmationDialog

@Composable
fun PriceRules(priceRuleViewModel: PriceRuleViewModel) {

    LaunchedEffect(Unit) {
        priceRuleViewModel.getAllPriceRules()
    }
    val showDialog = remember { mutableStateOf(false) }
    val priceRules = priceRuleViewModel.priceRules.observeAsState().value
    val selectedPriceRule = remember { mutableStateOf("") }

    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        item {
            ConfirmationDialog(
                showDialog = showDialog.value,
                onConfirm = {
                    priceRuleViewModel.deletePriceRule(selectedPriceRule.value)
                },
                onDismiss = {
                    showDialog.value = false
                }
            )
        }
        items(
            priceRules?.price_rules?.size ?: 0,
            key = { priceRules?.price_rules?.get(it)?.id ?: "" }) {
            DiscountCard(
                title = priceRules?.price_rules?.get(it)?.title ?: "",
                discount = priceRules?.price_rules?.get(it)?.value.toString() + "%",
                barcode = priceRules?.price_rules?.get(it)?.id ?: "",
                startAt = priceRules?.price_rules?.get(it)?.starts_at ?: "",
                endAt = priceRules?.price_rules?.get(it)?.ends_at ?: "",
                showDialog,
                selectedPriceRule,
                modifier = Modifier.animateItem()
            )
        }
    }

}


@Composable
fun DiscountCard(
    title: String,
    discount: String,
    barcode: String,
    startAt: String,
    endAt: String,
    showDialog: MutableState<Boolean>,
    selectedPriceRule: MutableState<String>,
    modifier: Modifier
) {

    Box(contentAlignment = Alignment.TopStart, modifier = modifier) {
        CustomDiscountCard(discount, title, barcode, startAt, endAt)
        Box(
            modifier = Modifier
                .padding(horizontal = 18.dp, vertical = 8.dp)
                .size(25.dp)
                .background(
                    color = lightGreyColor,
                    shape = RoundedCornerShape(50)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = stringResource(R.string.delete),
                tint = Color.Black,
                modifier = Modifier
                    .size(15.dp)
                    .clickable {
                        showDialog.value = true
                        selectedPriceRule.value = barcode
                    }
            )
        }

    }
}

@Composable
fun CustomDiscountCard(
    discount: String,
    title: String,
    barcode: String,
    startAt: String,
    endAt: String
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
                .height(230.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.horizontalGradient(listOf(Color(0xFFFFD1DC), Color(0xFFFFC1CC)))
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .width(80.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "DISCOUNT",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        modifier = Modifier.rotate(-90f)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = discount,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = title,
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Column {
                        Image(
                            painter = painterResource(R.drawable.numbers),
                            contentDescription = "",
                            modifier = Modifier.size(55.dp)
                        )
                        Text(
                            text = barcode,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "start at: ${startAt.substring(0, 10)}",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                        Text(
                            text = "end at: ${endAt.substring(0, 10)}",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .background(Color.White, shape = CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .background(Color.White, shape = CircleShape)
            )
        }


    }
}







