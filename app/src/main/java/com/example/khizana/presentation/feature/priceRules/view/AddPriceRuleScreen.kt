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
import com.example.khizana.domain.model.PriceRuleDomain
import com.example.khizana.presentation.feature.priceRules.view.CustomDiscountCard
import com.example.khizana.presentation.feature.priceRules.viewModel.PriceRuleViewModel
import com.example.khizana.presentation.feature.products.view.components.CustomTextField
import com.example.khizana.ui.theme.primaryColor
import com.example.khizana.ui.theme.secondaryColor
import java.util.*

@Composable
fun AddPriceRuleScreen(modifier: Modifier = Modifier, priceRuleViewModel: PriceRuleViewModel) {
    val discount = remember { mutableStateOf("discount%") }
    val title = remember { mutableStateOf("coupon title") }
    val barcode = remember { mutableStateOf("coupon Serial Number") }

    val titleValue = remember { mutableStateOf("") }
    val discountValue = remember { mutableStateOf("") }

    val context = LocalContext.current

    val startDate = remember { mutableStateOf("Start Date") }
    val endDate = remember { mutableStateOf("End Date") }

    val calendar = Calendar.getInstance()

    val discountType = remember { mutableStateOf("Percentage") }
    val discountOptions = listOf("Percentage", "Fixed Price")

    LazyColumn(modifier = modifier.padding(top = 64.dp)) {
        item {
            CustomDiscountCard(
                discount = discount.value,
                title = title.value,
                startAt = startDate.value + "               ",
                endAt = endDate.value + "                 ",
                barcode = barcode.value,
            )

            Column(modifier = Modifier.padding(16.dp)) {
                CustomTextField(
                    value = discountValue,
                    label = "Discount",
                    onValueChange = {
                        discountValue.value = it
                        discount.value = it
                        if (discountType.value == "Percentage") {
                            discount.value = "$it%"
                        } else {
                            discount.value = it + "EGP"
                        }
                    }
                )

                CustomTextField(
                    value = titleValue,
                    label = "Title",
                    onValueChange = {
                        titleValue.value = it
                        title.value = it
                    }
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
                                    if (discountType.value == "Percentage") {
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
                Button(
                    onClick = {

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

