package com.example.khizana.data.dto

import com.google.gson.annotations.SerializedName

data class DiscountCodeRequest(
    @SerializedName("discount_code")
    val discountCode: DiscountCodeEntity
)

data class DiscountCodeEntity(
    val code: String
)

