package com.example.khizana.data.dto

import com.google.gson.annotations.SerializedName

data class Product(
    val products: List<ProductsItemEntity?>?
)

data class ProductsItemEntity(
    val image: ImageEntity?,
    @SerializedName("body_html")
    val bodyHtml: String?,
    val images: List<ImagesItemEntity?>?,
    val tags: String,
    @SerializedName("created_at")
    val createdAt: String?,
    val variants: List<VariantsItemEntity?>?,
    val title: String?,
    @SerializedName("product_type")
    val productType: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    val vendor: String?,
    val options: List<OptionsItemEntity>?,
    val id: String?,
    @SerializedName("published_at")
    val publishedAt: String?,
    val status: String?
)

data class ImageEntity(
    val src: String?,
    val alt: String?,
    @SerializedName("variant_ids")
    val variantIds: List<String?>?,
)

data class ImagesItemEntity(
    val src: String?,
    val alt: String?,
    @SerializedName("variant_ids")
    val variantIds: List<String?>?,
)

data class VariantsItemEntity(
    val title: String?,
    val price: String?,
    val option3: String?,
    val option1: String?,
    val option2: String?,
    @SerializedName("inventory_quantity")
    val inventoryQuantity: Int?,
    @SerializedName("inventory_item_id")
    val inventoryItemId: String?
)

data class OptionsItemEntity(
    val values: List<String>?,
    val name: String?,
)

