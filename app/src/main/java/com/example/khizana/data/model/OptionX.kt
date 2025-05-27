package com.example.khizana.data.model

data class OptionX(
    val id: Long,
    val name: String,
    val position: Int,
    val product_id: Long,
    val values: List<String>
)