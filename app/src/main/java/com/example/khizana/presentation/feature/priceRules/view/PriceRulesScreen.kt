package com.example.khizana.presentation.feature.priceRules.view

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.khizana.domain.model.PriceRuleDomain
import com.example.khizana.domain.model.PriceRuleItem
import com.example.khizana.presentation.feature.priceRules.viewModel.PriceRuleViewModel
import com.example.khizana.utilis.ConfirmationDialog
import com.example.khizana.utilis.CustomLoadingIndicator
import com.example.khizana.utilis.NavigationRoutes
import com.example.khizana.utilis.Response
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.khizana.presentation.feature.priceRules.view.components.DiscountCard


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
            .fillMaxSize().padding(top = 8.dp)
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












