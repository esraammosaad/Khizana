package com.example.khizana.utilis

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun getShopifyOrderCountDatesForLastSevenDays(): List<String> {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val dates = mutableListOf<String>()

    for (i in 6 downTo 0) {
        val date = LocalDate.now().minusDays(i.toLong())
        val dateString = date.format(formatter)

        dates.add(dateString)
    }

    return dates
}
