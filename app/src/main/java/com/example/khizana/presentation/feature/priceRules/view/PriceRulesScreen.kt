package com.example.khizana.presentation.feature.priceRules.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.khizana.R
import com.example.khizana.domain.model.PriceRuleDomain
import com.example.khizana.domain.model.PriceRuleItem
import com.example.khizana.presentation.feature.priceRules.view.components.DiscountCard
import com.example.khizana.presentation.feature.priceRules.viewModel.PriceRuleViewModel
import com.example.khizana.utilis.ConfirmationDialog
import com.example.khizana.utilis.CustomLoadingIndicator
import com.example.khizana.utilis.NavigationRoutes
import com.example.khizana.utilis.Response
import com.example.khizana.utilis.WarningDialog


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PriceRules(
    priceRuleViewModel: PriceRuleViewModel = hiltViewModel(),
    navigationController: NavHostController,
    snackBarHostState: SnackbarHostState,
    isConnected: MutableState<Boolean>,
    onConfirmation: () -> Unit
) {
    LaunchedEffect(Unit) {
        priceRuleViewModel.getAllPriceRules()
        priceRuleViewModel.message.collect {
            snackBarHostState.showSnackbar(it)
        }
    }
    val showDialog = remember { mutableStateOf(false) }
    val priceRules = priceRuleViewModel.priceRules.collectAsStateWithLifecycle().value
    val selectedPriceRule = remember { mutableStateOf("") }
    val showBottomSheet = remember { mutableStateOf(false) }
    val priceRule: MutableState<PriceRuleItem?> = remember { mutableStateOf(null) }
    val showWarningDialog = rememberSaveable { mutableStateOf(false) }


    PartialPriceRuleBottomSheet(
        showBottomSheet = showBottomSheet,
        priceRuleViewModel = priceRuleViewModel,
        priceRule = priceRule.value,
        isEditable = true
    )
    WarningDialog(
        onConfirmation = onConfirmation,
        dialogText = stringResource(R.string.there_is_no_internet_connection_you_can_t_add_anything_right_now),
        dialogTitle = stringResource(R.string.warning),
        confirmText = stringResource(R.string.wifi_settings),
        showAlert = showWarningDialog
    )
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        when (priceRules) {
            is Response.Success<*> -> {
                priceRules as Response.Success<PriceRuleDomain>
                items(
                    priceRules.result?.priceRules?.size ?: 0,
                    key = { priceRules.result?.priceRules?.get(it)?.id ?: "" }) {
                    Box(
                        modifier = Modifier
                            .animateItem()
                            .clickable {
                                if (isConnected.value) {
                                    navigationController.navigate(
                                        NavigationRoutes.DiscountCodesScreen(
                                            priceRules.result?.priceRules?.get(it)?.id ?: ""
                                        )
                                    )
                                } else {
                                    showWarningDialog.value = true
                                }
                            }
                    ) {
                        Box(contentAlignment = Alignment.TopEnd) {
                            DiscountCard(
                                title = priceRules.result?.priceRules?.get(it)?.title ?: "",
                                discount = priceRules.result?.priceRules?.get(it)?.value.toString() + if (priceRules.result?.priceRules?.get(
                                        it
                                    )?.valueType == "percentage"
                                ) "%" else "EGP",
                                barcode = priceRules.result?.priceRules?.get(it)?.id ?: "",
                                startAt = priceRules.result?.priceRules?.get(it)?.startsAt ?: "",
                                endAt = priceRules.result?.priceRules?.get(it)?.endsAt ?: "",
                                modifier = Modifier.animateItem(),
                                onDeleteIconClicked = {
                                    if (isConnected.value) {
                                        selectedPriceRule.value =
                                            priceRules.result?.priceRules?.get(it)?.id ?: ""
                                        showDialog.value = true
                                    } else {
                                        showWarningDialog.value = true
                                    }
                                }
                            )
                            Text(
                                "Edit",
                                color = Color.White,
                                modifier = Modifier
                                    .padding(end = 32.dp, top = 16.dp)
                                    .clickable {
                                        if (isConnected.value) {
                                            priceRule.value = priceRules.result?.priceRules?.get(it)
                                            showBottomSheet.value = true
                                        } else {
                                            showWarningDialog.value = true
                                        }
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












