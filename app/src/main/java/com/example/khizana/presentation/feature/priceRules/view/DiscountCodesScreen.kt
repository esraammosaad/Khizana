package com.example.khizana.presentation.feature.priceRules.view

import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.khizana.R
import com.example.khizana.domain.model.DiscountCodeDomain
import com.example.khizana.presentation.feature.priceRules.view.components.DiscountCodeInputDialog
import com.example.khizana.presentation.feature.priceRules.viewModel.PriceRuleViewModel
import com.example.khizana.ui.theme.lightGreyColor
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.utilis.CustomLoadingIndicator
import com.example.khizana.utilis.Response
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.khizana.presentation.feature.priceRules.view.components.DiscountCodeCard
import com.example.khizana.utilis.dashedBorder


@Composable
fun DiscountCodeScreen(
    priceRuleViewModel: PriceRuleViewModel = hiltViewModel(),
    priceRuleId: String,
    navigationController: NavHostController
) {

    val discountCodes = priceRuleViewModel.discountCodes.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val code = remember { mutableStateOf("") }
    val codeId = remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        priceRuleViewModel.getDiscountCodes(priceRuleId)
        priceRuleViewModel.message.collect {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    LazyColumn(
        Modifier
            .padding(top = 64.dp)
    ) {
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = {
                    navigationController.popBackStack()
                }) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_icon),
                        tint = Color.Black.copy(0.7f),
                    )
                }
                IconButton(onClick = {
                    showDialog.value = true
                }) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add",
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Discount Codes",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
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
                            ?: 0,
                        onDeleteClick = {
                            priceRuleViewModel.deleteDiscountCode(
                                priceRuleId = priceRuleId,
                                discountCodeId = discountCodes.result?.discount_codes?.get(it)?.id
                                    ?: ""
                            )
                        },
                        onEditClick = {
                            code.value = discountCodes.result?.discount_codes?.get(it)?.code ?: ""
                            codeId.value = discountCodes.result?.discount_codes?.get(it)?.id ?: ""
                            showDialog.value = true
                            Log.i("TAG", "DiscountCodeScreen: ${code.value}")
                        }
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
            if (code.value.isEmpty()) {
                priceRuleViewModel.createDiscountCode(priceRuleId, it)
            } else {
                priceRuleViewModel.editDiscountCode(priceRuleId, codeId.value, it)
            }
        },
        code = code
    )
}




