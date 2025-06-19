package com.example.khizana.presentation.feature.priceRules.view

import AddPriceRuleScreen


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.khizana.domain.model.PriceRuleItem
import com.example.khizana.presentation.feature.priceRules.viewModel.PriceRuleViewModel
import androidx.hilt.navigation.compose.hiltViewModel


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartialPriceRuleBottomSheet(
    showBottomSheet: MutableState<Boolean>,
    priceRuleViewModel: PriceRuleViewModel = hiltViewModel(),
    priceRule: PriceRuleItem?,
    isEditable: Boolean
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showBottomSheet.value) {
            ModalBottomSheet(
                containerColor = Color.White,
                dragHandle = null,
                modifier = Modifier.wrapContentSize(),
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet.value = false },
            ) {
                AddPriceRuleScreen(
                    priceRuleViewModel = priceRuleViewModel,
                    priceRule = priceRule,
                    showBottomSheet = showBottomSheet,
                    isEditable = isEditable,
                )
            }

        }
    }
}