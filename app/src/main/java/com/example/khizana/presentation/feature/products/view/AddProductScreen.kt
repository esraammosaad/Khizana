package com.example.khizana.presentation.feature.products.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.khizana.presentation.feature.landing.CustomButton
import com.example.khizana.presentation.feature.products.view.components.CustomImagesRow
import com.example.khizana.presentation.feature.products.view.components.CustomOptionsRow
import com.example.khizana.presentation.feature.products.view.components.CustomTextArea
import com.example.khizana.presentation.feature.products.view.components.CustomTextField
import com.example.khizana.presentation.feature.products.view.components.CustomVariantItem
import com.example.khizana.presentation.feature.products.view.components.OptionInputDialog
import com.example.khizana.presentation.feature.products.view.components.VariantInputDialog
import com.example.khizana.presentation.feature.products.viewModel.ProductsViewModel
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.ui.theme.secondaryColor
import com.example.khizana.utilis.ConfirmationDialog

@Composable
fun AddProductScreen(
    productsViewModel: ProductsViewModel,
    showBottomSheet: MutableState<Boolean>,
    product: ProductsItem?,
    isEditable: Boolean,
    productId: String?
) {
    val productName = remember { mutableStateOf("") }
    val productDescription = remember { mutableStateOf("") }
    val productVendor = remember { mutableStateOf("") }
    val productType = remember { mutableStateOf("") }
    val productStatus = remember { mutableStateOf("") }
    val variantList = remember { mutableStateOf(emptyList<VariantsItem>()) }
    val optionList = remember { mutableStateOf(emptyList<OptionsItem>()) }
    val imageUris = remember {
        mutableStateOf<List<Any>?>(emptyList())
    }
    val imageUri = remember {
        mutableStateOf(imageUris.value?.firstOrNull())
    }
    val error = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }
    val showVariantDialog = remember { mutableStateOf(false) }
    val showOptionDialog = remember { mutableStateOf(false) }
    val showConfirmationDialog = remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        if (product != null) {
            productName.value = product.title ?: ""
            productDescription.value = product.body_html ?: ""
            productVendor.value = product.vendor ?: ""
            productType.value = product.product_type ?: ""
            imageUris.value = product.images?.map { it?.src ?: "" }
            imageUri.value = imageUris.value?.firstOrNull()
            variantList.value = (product.variants ?: emptyList()) as List<VariantsItem>
            optionList.value = (product.options ?: emptyList()) as List<OptionsItem>
        }
    }

    LazyColumn(
        Modifier
            .padding(horizontal = 12.dp)
            .background(Color.White)
    ) {
        item {
            ConfirmationDialog(
                showDialog = showConfirmationDialog.value,
                text = stringResource(R.string.are_you_sure_you_want_to_save_this_product),
                onConfirm = {
                    if (!error.value) {
                        productsViewModel.uploadProduct(
                            imageUris.value,
                            productName.value,
                            productDescription.value,
                            productType.value,
                            productVendor.value,
                            variantList.value,
                            optionList.value,
                            showBottomSheet,
                            isEditable,
                            productId
                        )
                    }
                },
                onDismiss = {
                    showConfirmationDialog.value = false
                }
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = if (isEditable) stringResource(R.string.edit_your_product) else stringResource(
                    R.string.add_new_product_now
                ), style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(Modifier.fillMaxSize()) {
                //CustomAsyncImage(imageUri.value ?: "")
                Spacer(modifier = Modifier.height(16.dp))
                CustomImagesRow(imageUris, imageUri)
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "Title",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomTextField(value = productName, label = stringResource(R.string.product_title))
                Text(
                    "Description",
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomTextArea(value = productDescription)
                Spacer(modifier = Modifier.height(8.dp))
                ProductForm(
                    vendors = listOf(
                        "ADIDAS",
                        "NIKE",
                        "FLEX FIT",
                        "HERSCHEL",
                        "VANS",
                        "CONVERSE",
                        "DR MARTENS",
                        "TIMBERLAND"
                    ),
                    productTypes = listOf("ACCESSORIES", "SHOES", "T-SHIRTS"),
                    statuses = listOf("active", "draft", "archived"),
                    productStatus = productStatus,
                    productVendor = productVendor,
                    productType = productType
                )
                Spacer(modifier = Modifier.height(16.dp))
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
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${option.name}: ${option.values?.joinToString(", ")}",
                            fontSize = 18.sp
                        )
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Delete",
                            tint = primaryColor,
                            modifier = Modifier.clickable {
                                optionList.value = optionList.value.minus(option)
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(25.dp))
                if (error.value) {
                    Text(
                        errorMessage.value,
                        color = Color.Red,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                CustomButton(
                    onClick = {
                        when {
                            imageUris.value.isNullOrEmpty() -> {
                                error.value = true
                                errorMessage.value = "Please add at least one image"
                            }

                            productName.value.isEmpty() -> {
                                error.value = true
                                errorMessage.value = "Please fill product name"
                            }

                            productDescription.value.isEmpty() -> {
                                error.value = true
                                errorMessage.value = "Please fill product description"
                            }

                            productVendor.value.isEmpty() -> {
                                error.value = true
                                errorMessage.value = "Please fill product vendor"
                            }

                            productType.value.isEmpty() -> {
                                error.value = true
                                errorMessage.value = "Please fill product type"
                            }

                            variantList.value.isEmpty() -> {
                                error.value = true
                                errorMessage.value = "Please add at least one variant"
                            }

                            optionList.value.isEmpty() -> {
                                error.value = true
                                errorMessage.value = "Please add at least one option"
                            }

                            else -> {
                                error.value = false
                                showConfirmationDialog.value = true
                            }
                        }
                    },
                    text =
                    stringResource(R.string.save),
                )
                Spacer(modifier = Modifier.height(25.dp))
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
fun ProductForm(
    vendors: List<String>,
    productTypes: List<String>,
    statuses: List<String>,
    productStatus: MutableState<String>,
    productVendor: MutableState<String>,
    productType: MutableState<String>
) {
    val expandedVendor = remember { mutableStateOf(false) }

    val expandedType = remember { mutableStateOf(false) }

    val expandedStatus = remember { mutableStateOf(false) }

    Column {
        CustomTextFieldAndMenuColumn(
            text = "Vendor",
            modifier = Modifier.fillMaxWidth(),
            expandedType = expandedVendor,
            productTypes = vendors,
            value = productVendor
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomTextFieldAndMenuColumn(
                modifier = Modifier.Companion.weight(1f),
                text = "Status",
                expandedType = expandedStatus,
                productTypes = statuses,
                value = productStatus
            )
            CustomTextFieldAndMenuColumn(
                modifier = Modifier.Companion.weight(1f),
                text = "Product type",
                expandedType = expandedType,
                productTypes = productTypes,
                value = productType
            )
        }
    }
}

@Composable
private fun CustomTextFieldAndMenuColumn(
    modifier: Modifier,
    text: String,
    expandedType: MutableState<Boolean>,
    productTypes: List<String>,
    value: MutableState<String>
) {
    Column(modifier = modifier) {
        Text(
            text,
            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextFieldWithDropDownMenu(expandedType, productTypes, value)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CustomTextFieldWithDropDownMenu(
    expandedVendor: MutableState<Boolean>,
    vendors: List<String>,
    value: MutableState<String>
) {
    ExposedDropdownMenuBox(
        expanded = expandedVendor.value,
        onExpandedChange = { expandedVendor.value = !expandedVendor.value },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        OutlinedTextField(
            value = value.value,
            onValueChange = {
                value.value = it
            },
            readOnly = true,
            placeholder = { Text("Select vendor") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedVendor.value) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = RoundedCornerShape(25),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = secondaryColor,
                disabledBorderColor = secondaryColor,
                unfocusedBorderColor = secondaryColor,
                errorBorderColor = Color.Red,
                errorTextColor = Color.Red,
                errorCursorColor = Color.Red,
                errorPlaceholderColor = Color.Red,
                cursorColor = primaryColor
            ),
        )
        ExposedDropdownMenu(
            expanded = expandedVendor.value,
            onDismissRequest = { expandedVendor.value = false },
            modifier = Modifier.background(Color.White)
        ) {
            vendors.forEach {
                DropdownMenuItem(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth(),
                    text = { Text(it) },
                    onClick = {
                        value.value = it
                        expandedVendor.value = false
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = Color.Black,
                        leadingIconColor = Color.Black,
                        trailingIconColor = Color.Black,
                        disabledTextColor = Color.Gray,
                        disabledLeadingIconColor = Color.Gray,
                        disabledTrailingIconColor = Color.Gray
                    )
                )
            }
        }
    }
}


















