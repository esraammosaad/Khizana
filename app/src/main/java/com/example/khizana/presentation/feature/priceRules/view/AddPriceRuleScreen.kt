import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.khizana.R
import com.example.khizana.domain.model.PriceRuleRequestDomain
import com.example.khizana.domain.model.PriceRuleDomainRequest
import com.example.khizana.domain.model.PriceRuleItem
import com.example.khizana.presentation.feature.priceRules.view.components.CustomDiscountCard
import com.example.khizana.presentation.feature.priceRules.viewModel.PriceRuleViewModel
import com.example.khizana.presentation.feature.products.view.components.CustomTextField
import com.example.khizana.ui.theme.offWhiteColor
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.ui.theme.secondaryColor
import com.example.khizana.utilis.ConfirmationDialog
import java.util.*

@Composable
fun AddPriceRuleScreen(
    priceRuleViewModel: PriceRuleViewModel,
    priceRule: PriceRuleItem? = null,
    showBottomSheet: MutableState<Boolean>,
    isEditable: Boolean,
) {
    val discount = remember { mutableStateOf("discount%") }
    val title = remember { mutableStateOf("coupon title") }
    val barcode = remember { mutableStateOf("coupon Serial Number") }

    val titleValue = remember { mutableStateOf("") }
    val discountValue = remember { mutableStateOf("") }

    val startDate = remember { mutableStateOf("Start Date") }
    val endDate = remember { mutableStateOf("End Date") }

    val discountType = remember { mutableStateOf("percentage") }
    val discountOptions = listOf("percentage", "fixed_amount")

    val titleError = remember { mutableStateOf(false) }
    val titleErrorMessage = remember { mutableStateOf("") }
    val discountError = remember { mutableStateOf(false) }
    val discountErrorMessage = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf("") }
    val error = remember { mutableStateOf(false) }
    val showConfirmationDialog = remember { mutableStateOf(false) }
    val openStartDatePicker = remember { mutableStateOf(false) }
    val openEndDatePicker = remember { mutableStateOf(false) }



    LaunchedEffect(Unit) {
        if (priceRule != null) {
            title.value = priceRule.title
            titleValue.value = priceRule.title
            discountValue.value = priceRule.value
            startDate.value = priceRule.starts_at.substring(0, 10)
            endDate.value = priceRule.ends_at.substring(0, 10)
            barcode.value = priceRule.id
            discountType.value = priceRule.value_type
            if (discountType.value == "percentage") {
                discount.value = "${priceRule.value}%"
            } else {
                discount.value = "${priceRule.value}EGP"
            }
        }
    }

    LazyColumn {
        item {
            ConfirmationDialog(
                showDialog = showConfirmationDialog.value,
                text = stringResource(R.string.are_you_sure_you_want_to_save_this_coupon),
                onConfirm = {
                    val rule = PriceRuleRequestDomain(
                        priceRule = PriceRuleDomainRequest(
                            valueType = discountType.value,
                            startsAt = startDate.value,
                            targetType = "line_item",
                            title = titleValue.value,
                            endsAt = endDate.value,
                            value = discountValue.value,
                        ),
                    )
                    if (!isEditable) {
                        priceRuleViewModel.createPriceRule(
                            priceRule = rule
                        )
                    } else {
                        priceRuleViewModel.editPriceRule(
                            priceRule = rule,
                            priceRuleId = barcode.value
                        )
                    }
                    showBottomSheet.value = false

                },
                onDismiss = {
                    showConfirmationDialog.value = false
                }
            )
            ComposeDatePickerExample(
                date = startDate,
                openDialog = openStartDatePicker
            )
            ComposeDatePickerExample(
                date = endDate,
                openDialog = openEndDatePicker
            )
            Spacer(modifier = Modifier.height(42.dp))
            Text(
                "Add New Coupon Now!", style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.padding(12.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomDiscountCard(
                discount = discount.value,
                title = title.value,
                startAt = startDate.value + "         ",
                endAt = endDate.value + "             ",
                barcode = barcode.value,
            )

            Column(modifier = Modifier.padding(16.dp)) {
                CustomTextField(
                    value = discountValue,
                    label = "Discount",
                    onValueChange = {
                        discountValue.value = it
                        discount.value = it
                        if (discountType.value == "percentage") {
                            discount.value = "${it}%"
                        } else {
                            discount.value = "${it}EGP"
                        }
                    },
                    error = discountError.value,
                    errorMessage = discountErrorMessage.value
                )

                CustomTextField(
                    value = titleValue,
                    label = "Title",
                    onValueChange = {
                        titleValue.value = it
                        title.value = it
                    },
                    error = titleError.value,
                    errorMessage = titleErrorMessage.value
                )

                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Button(
                        onClick = {
                            openStartDatePicker.value = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = secondaryColor
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = startDate.value, color = primaryColor)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            openEndDatePicker.value = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = secondaryColor
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = endDate.value, color = primaryColor)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Discount Type",
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = primaryColor,
                        fontWeight = FontWeight.Bold
                    )
                )
                Row {
                    discountOptions.forEach { type ->
                        Row(
                            Modifier
                                .selectable(
                                    selected = (discountType.value == type),
                                    onClick = { discountType.value = type }
                                )
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (discountType.value == type),
                                onClick = {
                                    discountType.value = type
                                    if (discountType.value == "percentage") {
                                        discount.value = discountValue.value + "%"
                                    } else {
                                        discount.value = discountValue.value + "EGP"
                                    }
                                },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = primaryColor,
                                )
                            )
                            Text(
                                text = type,
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(errorMessage.value, color = Color.Red)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (titleValue.value.isEmpty()) {
                            titleError.value = true
                            titleErrorMessage.value = "Please enter a title"
                        } else {
                            titleError.value = false
                            titleErrorMessage.value = ""

                        }

                        if (discountValue.value.isEmpty()) {
                            discountError.value = true
                            discountErrorMessage.value = "Please enter a discount"
                        } else {
                            val discountCheck = discountValue.value.toDoubleOrNull()
                            if (discountCheck == null || discountCheck >= 0.0) {
                                discountError.value = true
                                discountErrorMessage.value = "Discount must be negative number"
                            } else {
                                discountError.value = false
                                discountErrorMessage.value = ""
                            }
                        }

                        if (startDate.value == "Start Date" || endDate.value == "End Date") {
                            error.value = true
                            errorMessage.value = "Please select a date"
                        } else {
                            error.value = false
                            errorMessage.value = ""
                        }

                        if (!error.value && !titleError.value && !discountError.value) {

                            showConfirmationDialog.value = true

                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        stringResource(R.string.save),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeDatePickerExample(
    date: MutableState<String>,
    openDialog: MutableState<Boolean>
) {
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let { millis ->
        val calendar = Calendar.getInstance().apply { timeInMillis = millis }
        "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.DAY_OF_MONTH)}"
    }


    if (openDialog.value) {
        DatePickerDialog(
            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        if (selectedDate != null)
                            date.value = selectedDate
                    }
                ) {
                    Text("OK", color = primaryColor)
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog.value = false }) {
                    Text("Cancel", color = Color.Red)
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = offWhiteColor,
                    titleContentColor = primaryColor,
                    headlineContentColor = primaryColor,
                    weekdayContentColor = primaryColor,
                    subheadContentColor = primaryColor,
                    dayContentColor = primaryColor,
                    selectedDayContainerColor = primaryColor,
                    selectedDayContentColor = Color.White,
                    todayContentColor = primaryColor,
                    todayDateBorderColor = primaryColor,
                    dayInSelectionRangeContentColor = Color.White,
                    dayInSelectionRangeContainerColor = primaryColor,

                    )
            )
        }
    }
}


