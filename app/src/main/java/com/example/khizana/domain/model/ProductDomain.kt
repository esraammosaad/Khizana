package com.example.khizana.domain.model



data class ProductDomain(
    val products: List<ProductsItem?>?
)

data class ProductsItem(
    val image: Image?,
    val body_html: String?,
    val images: List<ImagesItem?>?,
    val created_at: String?,
    val handle: String?,
    val variants: List<VariantsItem?>?,
    val title: String?,
    val tags: String?,
    val published_scope: String?,
    val product_type: String?,
    val template_suffix: String?,
    val updated_at: String?,
    val vendor: String?,
    val admin_graphql_api_id: String?,
    val options: List<OptionsItem?>?,
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

data class OptionsItem(
    val product_id: String?,
    val values: List<String?>?,
    val name: String?,
    val id: String?,
    val position: Int?
)

