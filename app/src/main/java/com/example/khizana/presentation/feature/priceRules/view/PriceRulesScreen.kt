package com.example.khizana.presentation.feature.priceRules.view

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.khizana.presentation.feature.priceRules.viewModel.PriceRuleViewModel

@Composable
fun PriceRules(modifier: Modifier = Modifier, priceRuleViewModel: PriceRuleViewModel) {

    LaunchedEffect(Unit) {
        priceRuleViewModel.getAllPriceRules()
    }
    val priceRules = priceRuleViewModel.priceRules.observeAsState().value
    Log.i("TAG", "PriceRules: $priceRules")

}