package com.example.khizana.domain.model

data class ProductDomain(
    val products: List<ProductsItem?>?
)

data class ProductsItem(
    val image: Image?,
    val body_html: String?,
    val images: List<ImagesItem?>?,
    val created_at: String?,
    val variants: List<VariantsItem?>?,
    val title: String?,
    val product_type: String?,
    val updated_at: String?,
    val vendor: String?,
    val options: List<OptionsItem>?,
    val id: String?,
    val published_at: String?,
    val status: String?
)

data class Image(
    val src: String?,
    val alt: String?,
    val variant_ids: List<String?>?,
)

data class ImagesItem(
    val src: String?,
    val alt: String?,
    val variant_ids: List<String?>?,
)

data class VariantsItem(
    val title: String?,
    val price: String?,
    val option3: String?,
    val option1: String?,
    val option2: String?,
    val inventory_quantity: Int?,
)

data class OptionsItem(
    val values: List<String>?,
    val name: String?,
)

