package com.example.khizana.presentation.feature.products.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.khizana.R
import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.model.ProductsItem
import com.example.khizana.presentation.feature.products.viewModel.ProductsViewModel
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.ui.theme.secondaryColor

@Composable
fun AddProductScreen(productsViewModel: ProductsViewModel, showBottomSheet: MutableState<Boolean>) {

    val productName = remember { mutableStateOf("") }
    val productDescription = remember { mutableStateOf("") }
    val productVendor = remember { mutableStateOf("") }
    val productType = remember { mutableStateOf("") }
    val expanded = remember { mutableStateOf(false) }
    val options = listOf("ADIDAS", "NIKE", "TIMBERLAND", "TIMBERLAND", "TIMBERLAND")
    val error = remember { mutableStateOf(false) }
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
                imageUris.value = it
                imageUri.value = imageUris.value?.firstOrNull()

            }
        }
    )

    LazyColumn(
        Modifier
            .padding(start = 8.dp, end = 8.dp)
            .background(Color.White)
    ) {
        item {
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = stringResource(R.string.add_new_product_now), style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = imageUri.value ?: "",
                    placeholder = painterResource(R.drawable.photo),
                    error = painterResource(R.drawable.photo),
                    contentDescription = "",
                    modifier = Modifier
                        .size(150.dp)
                        .border(
                            color = primaryColor,
                            width = 1.dp,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    items(imageUris.value?.size ?: 0) {
                        AsyncImage(
                            model = imageUris.value?.getOrNull(it),
                            placeholder = painterResource(R.drawable.photo),
                            error = painterResource(R.drawable.photo),
                            contentDescription = "",
                            modifier = Modifier
                                .size(70.dp)
                                .border(
                                    color = primaryColor,
                                    width = 1.dp,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(8.dp)
                                .clickable {
                                    imageUri.value = imageUris.value?.getOrNull(it)
                                }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    item {
                        Image(
                            painter = painterResource(R.drawable.photo),
                            contentDescription = "",
                            modifier = Modifier
                                .size(70.dp)
                                .border(
                                    color = primaryColor,
                                    width = 1.dp,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(8.dp)
                                .clickable {
                                    galleryLauncher.launch("image/*")
                                }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(value = productName, error = error, label = "Product Title")
                CustomTextArea(value = productDescription, error = error)
                Column {
                    CustomTextField(
                        value = productVendor,
                        error = error,
                        label = "Product Vendor",
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
                    error = error,
                    label = "Product Type",
                )
                Button(
                    modifier = Modifier.fillMaxWidth(), onClick = {
                        val productItem = ProductsItem(
                            image = null,
                            body_html = productDescription.value,
                            images = listOf(),
                            created_at = "",
                            variants = listOf(),
                            title = productName.value,
                            product_type = productType.value,
                            updated_at = "",
                            vendor = productVendor.value,
                            options = listOf(),
                            id = "",
                            published_at = "",
                            status = "active"
                        )
                        productsViewModel.createProduct(productRequestDomain = ProductRequestDomain(
                            product = productItem
                        ))
                        showBottomSheet.value = false
                        productsViewModel.getProducts()
                },
                    colors = ButtonColors(
                        containerColor = primaryColor,
                        contentColor = Color.White,
                        disabledContainerColor = primaryColor,
                        disabledContentColor = primaryColor
                    )
                    ) {

                    Text(text = "Add Product", color = Color.White)
                }
            }
        }
    }
}

@Composable
private fun CustomTextArea(
    value: MutableState<String>,
    error: MutableState<Boolean>
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
        isError = false,
    )
    if (error.value) {
        Text(
            text = "errorMessage",
            color = primaryColor,
            style = TextStyle(fontSize = 12.sp)
        )
    }
}

@Composable
private fun CustomTextField(
    value: MutableState<String>,
    error: MutableState<Boolean>,
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
        isError = false,
    )
    if (error.value) {
        Text(
            text = "errorMessage",
            color = primaryColor,
            style = TextStyle(fontSize = 12.sp)
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddProductScreenPreview() {

   // AddProductScreen(productsViewModel)

}