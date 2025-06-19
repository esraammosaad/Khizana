package com.example.khizana.data.dto

import com.google.gson.annotations.SerializedName


data class PriceRule(
    @SerializedName("price_rules")
    val priceRules: List<PriceRulesItemEntity>
)

data class PriceRulesItemEntity(
    @SerializedName("value_type")
    val valueType: String,
    @SerializedName("once_per_customer")
    val oncePerCustomer: Boolean,
    @SerializedName("starts_at")
    val startsAt: String,
    @SerializedName("ends_at")
    val endsAt: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("prerequisite_customer_ids")
    val prerequisiteCustomerIds: List<String>,
    var title: String,
    @SerializedName("entitled_collection_ids")
    val entitledCollectionIds: List<String>,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("entitled_country_ids")
    val entitledCountryIds: List<String>,
    @SerializedName("entitled_variant_ids")
    val entitledVariantIds: List<String>,
    val id: String,
    var value: String,
    @SerializedName("allocation_method")
    val allocationMethod: String,
    @SerializedName("allocation_limit")
    val allocationLimit: Int,
    @SerializedName("target_type")
    val targetType: String,
    @SerializedName("entitled_product_ids")
    val entitledProductIds: List<String>,
    @SerializedName("customer_segment_prerequisite_ids")
    val customerSegmentPrerequisiteIds: List<String>,
    @SerializedName("customer_selection")
    val customerSelection: String,
    @SerializedName("target_selection")
    val targetSelection: String,
    @SerializedName("prerequisite_to_entitlement_quantity_ratio")
    val prerequisiteToEntitlementQuantityRatio: PrerequisiteToEntitlementQuantityRatio
)


data class PrerequisiteToEntitlementQuantityRatio(
    @SerializedName("prerequisite_quantity")
    val prerequisiteQuantity: Int,
    @SerializedName("entitled_quantity")
    val entitledQuantity: Int
)



