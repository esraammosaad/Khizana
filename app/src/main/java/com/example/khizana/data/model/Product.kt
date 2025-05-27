package com.example.khizana.data.model

data class Product(
    val admin_graphql_api_id: String,
    val body_html: String,
    val created_at: String,
    val handle: String,
    val id: Long,
    val image: ImageXX?,
    val images: List<ImageXX>,
    val options: List<Option>,
    val product_type: String,
    val published_at: String,
    val published_scope: String,
    val status: String,
    val tags: String,
    val template_suffix: Any,
    val title: String,
    val updated_at: String,
    val vendor: String
)