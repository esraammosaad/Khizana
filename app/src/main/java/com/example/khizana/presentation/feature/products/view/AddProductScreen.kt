package com.example.khizana.presentation.feature.products.view

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.khizana.R
import com.example.khizana.domain.model.OptionsItem
import com.example.khizana.domain.model.ProductsItem
import com.example.khizana.domain.model.VariantsItem
import com.example.khizana.presentation.feature.products.view.components.CustomAsyncImage
import com.example.khizana.presentation.feature.products.view.components.CustomDropDownMenu
import com.example.khizana.presentation.feature.products.view.components.CustomImagesRow
import com.example.khizana.presentation.feature.products.view.components.CustomTextArea
import com.example.khizana.presentation.feature.products.view.components.CustomTextField
import com.example.khizana.presentation.feature.products.view.components.CustomVariantItem
import com.example.khizana.presentation.feature.products.view.components.OptionInputDialog
import com.example.khizana.presentation.feature.products.view.components.VariantInputDialog
import com.example.khizana.presentation.feature.products.viewModel.ProductsViewModel
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.ui.theme.secondaryColor

@Composable
fun AddProductScreen(
    productsViewModel: ProductsViewModel,
    showBottomSheet: MutableState<Boolean>,
    product: ProductsItem?
) {

    val productName = remember { mutableStateOf("") }
    val productDescription = remember { mutableStateOf("") }
    val productVendor = remember { mutableStateOf("") }
    val productType = remember { mutableStateOf("") }
    val expanded = remember { mutableStateOf(false) }
    val variantList = remember { mutableStateOf(emptyList<VariantsItem>()) }
    val optionList = remember { mutableStateOf(emptyList<OptionsItem>()) }
    val imageUris = remember {
        mutableStateOf<List<Uri>?>(emptyList())
    }
    val imageUri = remember {
        mutableStateOf(imageUris.value?.firstOrNull())
    }

    val showVariantDialog = remember { mutableStateOf(false) }
    val showOptionDialog = remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        if (product != null) {
            productName.value = product.title ?: ""
            productDescription.value = product.body_html ?: ""
            productVendor.value = product.vendor ?: ""
            productType.value = product.product_type ?: ""
            imageUris.value = product.images?.map { Uri.parse(it?.src) }
            imageUri.value = imageUris.value?.firstOrNull()
        }
    }

    LazyColumn(
        Modifier
            .padding(horizontal = 12.dp)
            .background(Color.White)
    ) {
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    stringResource(R.string.save),
                    style = TextStyle(color = primaryColor, fontSize = 16.sp),
                    modifier = Modifier.clickable {
                        productsViewModel.uploadProduct(
                            imageUris.value,
                            productName.value,
                            productDescription.value,
                            productType.value,
                            productVendor.value,
                            variantList.value,
                            optionList.value,
                            showBottomSheet
                        )
                    })
            }
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = stringResource(R.string.add_new_product_now), style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                CustomAsyncImage(imageUri.value ?: "")
                Spacer(modifier = Modifier.height(16.dp))
                CustomImagesRow(imageUris, imageUri)
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(value = productName, label = stringResource(R.string.product_title))
                CustomTextArea(value = productDescription)
                Column {
                    CustomTextField(
                        value = productVendor,
                        label = stringResource(R.string.product_vendor),
                        trailingIcon = {
                            IconButton(onClick = { expanded.value = true }) {
                                Icon(Icons.Filled.ArrowDropDown, "")
                            }
                        }
                    )
                    CustomDropDownMenu(expanded, productVendor)
                }
                CustomTextField(
                    value = productType,
                    label = stringResource(R.string.product_type),
                )
                CustomOptionsRow(
                    text = stringResource(R.string.variants),
                    onClick = {
                        showVariantDialog.value = true
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomVariantItem(variantList)
                Spacer(modifier = Modifier.height(8.dp))
                CustomOptionsRow(
                    text = stringResource(R.string.options),
                    onClick = {
                        showOptionDialog.value = true
                    }
                )
                optionList.value.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp), horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "${option.name}: ${option.values?.joinToString(", ")}",
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
    VariantInputDialog(showDialog = showVariantDialog) {
        variantList.value = variantList.value.plus(it)
    }
    OptionInputDialog(showDialog = showOptionDialog) {
        optionList.value = optionList.value.plus(it)
    }
}

@Composable
private fun CustomOptionsRow(text: String, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .padding(5.dp)
                .size(25.dp)
                .background(
                    color = secondaryColor,
                    shape = RoundedCornerShape(50)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(R.string.add),
                tint = primaryColor,
                modifier = Modifier
                    .size(18.dp)
                    .clickable {
                        onClick.invoke()

                    }
            )
        }
    }
}















