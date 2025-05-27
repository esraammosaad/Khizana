package com.example.khizana.data.model

data class ProductX(
    val admin_graphql_api_id: String,
    val body_html: String,
    val created_at: String,
    val handle: String,
    val id: Long,
    val image: ImageXXX,
    val images: List<ImageXXX>,
    val options: List<OptionX>,
    val product_type: String,
    val published_at: String,
    val published_scope: String,
    val status: String,
    val tags: String,
    val template_suffix: Any,
    val title: String,
    val updated_at: String,
    val variants: List<Variant>,
    val vendor: String
)