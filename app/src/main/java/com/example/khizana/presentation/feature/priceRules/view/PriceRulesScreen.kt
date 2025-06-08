package com.example.khizana.presentation.feature.priceRules.view

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.khizana.R
import com.example.khizana.domain.model.PriceRuleDomain
import com.example.khizana.domain.model.PriceRuleItem
import com.example.khizana.presentation.feature.priceRules.viewModel.PriceRuleViewModel
import com.example.khizana.ui.theme.lightGreyColor
import com.example.khizana.ui.theme.lightPrimaryColor
import com.example.khizana.ui.theme.lighterPrimaryColor
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.utilis.ConfirmationDialog
import com.example.khizana.utilis.CustomLoadingIndicator
import com.example.khizana.utilis.NavigationRoutes
import com.example.khizana.utilis.Response
import androidx.hilt.navigation.compose.hiltViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PriceRules(priceRuleViewModel: PriceRuleViewModel = hiltViewModel(), navigationController: NavHostController) {

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        priceRuleViewModel.getAllPriceRules()
        priceRuleViewModel.message.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    val showDialog = remember { mutableStateOf(false) }
    val priceRules = priceRuleViewModel.priceRules.collectAsStateWithLifecycle().value
    val selectedPriceRule = remember { mutableStateOf("") }
    val showBottomSheet = remember { mutableStateOf(false) }
    val priceRule: MutableState<PriceRuleItem?> = remember { mutableStateOf(null) }

    PartialPriceRuleBottomSheet(
        showBottomSheet = showBottomSheet,
        priceRuleViewModel = priceRuleViewModel,
        priceRule = priceRule.value,
        isEditable = true
    )
    LazyColumn(
        Modifier
            .fillMaxSize()
    ) {
        when (priceRules) {
            is Response.Success<*> -> {
                priceRules as Response.Success<PriceRuleDomain>
                items(
                    priceRules.result?.price_rules?.size ?: 0,
                    key = { priceRules.result?.price_rules?.get(it)?.id ?: "" }) {
                    Box(
                        modifier = Modifier.clickable {
                            navigationController.navigate(
                                NavigationRoutes.DiscountCodesScreen(
                                    priceRules.result?.price_rules?.get(it)?.id ?: ""
                                )
                            )
                        }
                    ) {
                        Box(contentAlignment = Alignment.TopEnd) {
                            DiscountCard(
                                title = priceRules.result?.price_rules?.get(it)?.title ?: "",
                                discount = priceRules.result?.price_rules?.get(it)?.value.toString() + if (priceRules.result?.price_rules?.get(
                                        it
                                    )?.value_type == "percentage"
                                ) "%" else "EGP",
                                barcode = priceRules.result?.price_rules?.get(it)?.id ?: "",
                                startAt = priceRules.result?.price_rules?.get(it)?.starts_at ?: "",
                                endAt = priceRules.result?.price_rules?.get(it)?.ends_at ?: "",
                                modifier = Modifier.animateItem(),
                                onDeleteIconClicked = {
                                    selectedPriceRule.value =
                                        priceRules.result?.price_rules?.get(it)?.id ?: ""
                                    showDialog.value = true
                                }
                            )
                            Text(
                                "Edit",
                                color = Color.White,
                                modifier = Modifier
                                    .padding(end = 32.dp, top = 16.dp)
                                    .clickable {
                                        priceRule.value = priceRules.result?.price_rules?.get(it)
                                        showBottomSheet.value = true
                                    },
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            is Response.Failure -> {
                item {
                    Box(
                        modifier = Modifier.fillParentMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = priceRules.exception)
                    }
                }
            }

            Response.Loading -> {
                item {
                    CustomLoadingIndicator(modifier = Modifier.fillParentMaxSize())
                }
            }
        }
    }
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


@Composable
fun DiscountCard(
    title: String,
    discount: String,
    barcode: String,
    startAt: String,
    endAt: String,
    modifier: Modifier,
    onDeleteIconClicked: () -> Unit
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
                        onDeleteIconClicked.invoke()
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
                    Brush.horizontalGradient(
                        listOf(
                            primaryColor,
                            lightPrimaryColor,
                            lighterPrimaryColor
                        )
                    )
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







