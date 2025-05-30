package com.example.khizana.data.dto

data class Product(
    val products: List<ProductsItemEntity?>?
)

data class ProductsItemEntity(
    val image: ImageEntity?,
    val body_html: String?,
    val images: List<ImagesItemEntity?>?,
    val created_at: String?,
    val variants: List<VariantsItemEntity?>?,
    val title: String?,
    val product_type: String?,
    val updated_at: String?,
    val vendor: String?,
    val options: List<OptionsItemEntity?>?,
    val id: String?,
    val published_at: String?,
    val status: String?
)

data class ImageEntity(
    val src: String?,
    val alt: String?,
    val variant_ids: List<String?>?,
)

data class ImagesItemEntity(
    val src: String?,
    val alt: String?,
    val variant_ids: List<String?>?,
)

data class VariantsItemEntity(
    val old_inventory_quantity: Int?,
    val title: String?,
    val inventory_item_id: String?,
    val price: String?,
    val product_id: String?,
    val option3: String?,
    val option1: String?,
    val id: String?,
    val option2: String?,
    val sku: String?,
    val inventory_quantity: Int?,
)

data class OptionsItemEntity(
    val product_id: String?,
    val values: List<String?>?,
    val name: String?,
    val id: String?,
)

