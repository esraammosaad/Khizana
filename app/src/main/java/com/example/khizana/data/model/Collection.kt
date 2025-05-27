package com.example.khizana.data.model

data class Collection(
    val admin_graphql_api_id: String,
    val body_html: String,
    val collection_type: String,
    val handle: String,
    val id: Long,
    val image: Image,
    val products_count: Int,
    val published_at: String,
    val published_scope: String,
    val sort_order: String,
    val template_suffix: Any,
    val title: String,
    val updated_at: String
)