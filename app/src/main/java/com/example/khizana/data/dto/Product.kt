package com.example.khizana.data.dto

data class Product(
    val products: List<ProductsItemEntity?>?
)

data class ProductsItemEntity(
    val image: ImageEntity?,
    val body_html: String?,
    val images: List<ImagesItemEntity?>?,
    val created_at: String?,
    val handle: String?,
    val variants: List<VariantsItemEntity?>?,
    val title: String?,
    val tags: String?,
    val published_scope: String?,
    val product_type: String?,
    val template_suffix: String?,
    val updated_at: String?,
    val vendor: String?,
    val admin_graphql_api_id: String?,
    val options: List<OptionsItemEntity>?,
    val id: String?,
    val published_at: String?,
    val status: String?
)

data class ImageEntity(
    val updated_at: String?,
    val src: String?,
    val product_id: String?,
    val admin_graphql_api_id: String?,
    val alt: String?,
    val width: Int?,
    val created_at: String?,
    val variant_ids: List<String?>?,
    val id: String?,
    val position: Int?,
    val height: Int?
)

data class ImagesItemEntity(
    val updated_at: String?,
    val src: String?,
    val product_id: String?,
    val admin_graphql_api_id: String?,
    val alt: String?,
    val width: Int?,
    val created_at: String?,
    val variant_ids: List<String?>?,
    val id: String?,
    val position: Int?,
    val height: Int?
)

data class VariantsItemEntity(
    val inventory_management: String?,
    val requires_shipping: Boolean?,
    val old_inventory_quantity: Int?,
    val created_at: String?,
    val title: String?,
    val updated_at: String?,
    val inventory_item_id: String?,
    val price: String?,
    val product_id: String?,
    val option3: String?,
    val option1: String?,
    val id: String?,
    val option2: String?,
    val grams: Int?,
    val sku: String?,
    val barcode: String?,
    val inventory_quantity: Int?,
    val compare_at_price: String?,
    val taxable: Boolean?,
    val fulfillment_service: String?,
    val weight: String?,
    val inventory_policy: String?,
    val weight_unit: String?,
    val admin_graphql_api_id: String?,
    val position: Int?,
    val image_id: String?
)

data class OptionsItemEntity(
    val product_id: String?,
    val values: List<String?>?,
    val name: String?,
    val id: String?,
    val position: Int?
)

