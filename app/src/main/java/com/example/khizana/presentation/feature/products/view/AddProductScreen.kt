package com.example.khizana.presentation.feature.products.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.khizana.R
import com.example.khizana.domain.model.OptionsItem
import com.example.khizana.domain.model.ProductsItem
import com.example.khizana.domain.model.VariantsItem
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
    val options = listOf("ADIDAS", "NIKE", "TIMBERLAND", "TIMBERLAND", "TIMBERLAND")
    val imageUris = remember {
        mutableStateOf<List<Uri>?>(emptyList())
    }
    val imageUri = remember {
        mutableStateOf(imageUris.value?.firstOrNull())
    }
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uri ->
            uri.let {
                imageUris.value = imageUris.value?.plus(it)
                imageUri.value = imageUris.value?.firstOrNull()

            }
        }
    )
    val showVariantDialog = remember { mutableStateOf(false) }

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
                    "Save",
                    style = TextStyle(color = primaryColor, fontSize = 16.sp),
                    modifier = Modifier.clickable {
                        productsViewModel.uploadProduct(
                            imageUris.value,
                            productName.value,
                            productDescription.value,
                            productType.value,
                            productVendor.value,
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
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    items(imageUris.value?.size ?: 0) {

                        Box(
                            contentAlignment = Alignment.TopEnd
                        ) {
                            AsyncImage(
                                model = imageUris.value?.getOrNull(it) ?: "",
                                placeholder = painterResource(R.drawable.photo),
                                error = painterResource(R.drawable.photo),
                                contentDescription = "",
                                modifier = Modifier
                                    .border(
                                        color = primaryColor,
                                        width = 1.dp,
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .size(120.dp)
                                    .padding(8.dp)
                                    .clickable {

                                        imageUri.value = imageUris.value?.get(it)


                                    },
                                contentScale = ContentScale.Fit
                            )

                            Icon(
                                painter = painterResource(R.drawable.baseline_remove_24),
                                contentDescription = "",
                                modifier = Modifier.clickable {
                                    imageUris.value = imageUris.value?.minus(imageUris.value!![it])
                                    imageUri.value = imageUris.value?.firstOrNull()
                                }
                            )

                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    item {
                        Image(
                            painter = painterResource(R.drawable.photo),
                            contentDescription = "",
                            modifier = Modifier
                                .border(
                                    color = primaryColor,
                                    width = 1.dp,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .size(120.dp)
                                .padding(8.dp)
                                .clickable {
                                    galleryLauncher.launch("image/*")
                                }
                        )
                    }
                }
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
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false },
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .background(
                                secondaryColor
                            )
                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    productVendor.value = option
                                    expanded.value = false
                                }
                            )
                        }
                    }
                }
                CustomTextField(
                    value = productType,
                    label = stringResource(R.string.product_type),
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Variants",
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
                            contentDescription = "Add",
                            tint = primaryColor,
                            modifier = Modifier
                                .size(18.dp)
                                .clickable {
                                    showVariantDialog.value = true

                                }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                variantList.value.forEach { variant ->
                    val title = listOfNotNull(
                        variant.option1,
                        variant.option2,
                        variant.option3
                    ).joinToString(" / ")
                    Row {
                        Text(
                            text = "$title → ${variant.price} EGP | Qty: ${variant.inventory_quantity}",
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.width(14.dp))
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Delete",
                            tint = primaryColor,
                            modifier = Modifier.clickable {
                                variantList.value = variantList.value.minus(variant)
                            }
                        )

                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
    InputAlertDialog(showDialog = showVariantDialog) {

        variantList.value = variantList.value.plus(it)

    }
}

@Composable
fun InputAlertDialog(
    showDialog: MutableState<Boolean>,
    onConfirm: (VariantsItem) -> Unit
) {
    val variantTitle = remember { mutableStateOf("") }
    val variantPrice = remember { mutableStateOf("") }
    val variantQuantity = remember { mutableStateOf("") }
    val optionOne = remember { mutableStateOf("") }
    val optionTwo = remember { mutableStateOf("") }
    val optionThree = remember { mutableStateOf("") }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { },
            text = {
                Column {
                    OutlinedTextField(
                        value = variantTitle.value,
                        onValueChange = { variantTitle.value = it },
                        label = { Text("Variant Title") },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = variantPrice.value,
                        onValueChange = { variantPrice.value = it },
                        label = { Text("Price") },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = variantQuantity.value,
                        onValueChange = { variantQuantity.value = it },
                        label = { Text("Quantity") },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = optionOne.value,
                        onValueChange = { optionOne.value = it },
                        label = { Text("Option One") },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = optionTwo.value,
                        onValueChange = { optionTwo.value = it },
                        label = { Text("Option Two") },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = optionThree.value,
                        onValueChange = { optionThree.value = it },
                        label = { Text("Option Three") },
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    val variant = VariantsItem(
                        title = variantTitle.value,
                        price = variantPrice.value,
                        option3 = optionThree.value,
                        option1 = optionOne.value,
                        option2 = optionTwo.value,
                        inventory_quantity = variantQuantity.value.toIntOrNull() ?: 0
                    )
                    onConfirm(variant)
                    showDialog.value = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}


@Composable
private fun CustomAsyncImage(imageUri: Any, size: Dp = 260.dp) {
    AsyncImage(
        model = imageUri,
        placeholder = painterResource(R.drawable.photo),
        error = painterResource(R.drawable.photo),
        contentDescription = "",
        modifier = Modifier
            .border(
                color = primaryColor,
                width = 1.dp,
                shape = RoundedCornerShape(10.dp)
            )
            .size(size)
            .padding(8.dp),
        contentScale = ContentScale.Fit
    )
}

@Composable
private fun CustomTextArea(
    value: MutableState<String>,
) {
    OutlinedTextField(
        label = {
            Text(
                text = stringResource(R.string.product_description),
                color = primaryColor,
            )
        },
        enabled = true,
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(bottom = 12.dp),
        value = value.value,
        singleLine = false,
        maxLines = 10,
        onValueChange = {
            value.value = it
        },
        shape = RoundedCornerShape(15),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = secondaryColor,
            disabledBorderColor = secondaryColor,
            unfocusedBorderColor = secondaryColor,
            errorBorderColor = primaryColor,
            errorTextColor = primaryColor,
            errorCursorColor = primaryColor,
            errorPlaceholderColor = primaryColor,
            cursorColor = primaryColor
        ),
    )
}

@Composable
private fun CustomTextField(
    value: MutableState<String>,
    label: String,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        label = {
            Text(
                text = label,
                color = primaryColor,
            )
        },
        enabled = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        value = value.value,
        onValueChange = {
            value.value = it
        },
        shape = RoundedCornerShape(25),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = secondaryColor,
            disabledBorderColor = secondaryColor,
            unfocusedBorderColor = secondaryColor,
            errorBorderColor = primaryColor,
            errorTextColor = primaryColor,
            errorCursorColor = primaryColor,
            errorPlaceholderColor = primaryColor,
            cursorColor = primaryColor
        ),
        trailingIcon = trailingIcon,
    )
}


