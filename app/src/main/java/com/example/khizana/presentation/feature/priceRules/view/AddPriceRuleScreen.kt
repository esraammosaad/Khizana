import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.khizana.R
import com.example.khizana.domain.model.Prerequisite_to_entitlement_quantity_ratio
import com.example.khizana.domain.model.PriceRuleRequestDomain
import com.example.khizana.domain.model.PriceRule
import com.example.khizana.domain.model.PriceRuleItem
import com.example.khizana.presentation.feature.priceRules.view.CustomDiscountCard
import com.example.khizana.presentation.feature.priceRules.viewModel.PriceRuleViewModel
import com.example.khizana.presentation.feature.products.view.components.CustomTextField
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.ui.theme.secondaryColor
import java.util.*

@Composable
fun AddPriceRuleScreen(
    modifier: Modifier = Modifier,
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

    val context = LocalContext.current

    val startDate = remember { mutableStateOf("Start Date") }
    val endDate = remember { mutableStateOf("End Date") }

    val calendar = Calendar.getInstance()

    val discountType = remember { mutableStateOf("percentage") }
    val discountOptions = listOf("percentage", "fixed_amount")

    val titleError = remember { mutableStateOf(false) }
    val titleErrorMessage = remember { mutableStateOf("") }
    val discountError = remember { mutableStateOf(false) }
    val discountErrorMessage = remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf("") }
    val error = remember { mutableStateOf(false) }


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

    LazyColumn(modifier = modifier.padding(top = 64.dp)) {
        item {
            Text(
                "Add New Coupon Now!", style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                ),
                modifier = Modifier.padding(12.dp)
            )
            Spacer(modifier = Modifier.height(18.dp))
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
                            val datePicker = DatePickerDialog(
                                context,
                                { _: DatePicker, year: Int, month: Int, day: Int ->
                                    startDate.value = "$year-${month + 1}-$day"
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            )
                            datePicker.datePicker.minDate = System.currentTimeMillis()
                            datePicker.show()
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
                            val datePicker = DatePickerDialog(
                                context,
                                { _: DatePicker, year: Int, month: Int, day: Int ->
                                    endDate.value = "$year-${month + 1}-$day"
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            )
                            datePicker.datePicker.minDate = System.currentTimeMillis()
                            datePicker.show()
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
                            discountError.value = false
                            discountErrorMessage.value = ""
                        }

                        if (startDate.value == "Start Date" || endDate.value == "End Date") {
                            error.value = true
                            errorMessage.value = "Please select a date"
                        } else {
                            error.value = false
                            errorMessage.value = ""
                        }

                        if (!error.value && !titleError.value && !discountError.value) {
                            val rule = PriceRuleRequestDomain(
                                price_rule = PriceRule(
                                    allocation_method = "each",
                                    prerequisite_to_entitlement_quantity_ratio = Prerequisite_to_entitlement_quantity_ratio(
                                        prerequisite_quantity = 2,
                                        entitled_quantity = 1
                                    ),
                                    value_type = discountType.value,
                                    starts_at = startDate.value,
                                    allocation_limit = 3,
                                    target_type = "line_item",
                                    entitled_product_ids = listOf("7379132088433"),
                                    title = titleValue.value,
                                    customer_selection = "all",
                                    target_selection = "entitled",
                                    ends_at = endDate.value,
                                    value = discountValue.value,
                                    prerequisite_collection_ids = listOf("288621756529")
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

