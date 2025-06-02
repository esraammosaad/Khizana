package com.example.khizana.domain.model

data class PriceRuleDomain(
    val price_rules: List<Price_rulesItem>
)

data class Price_rulesItem(
    val value_type: String,
    val once_per_customer: Boolean,
    val starts_at: String,
    val created_at: String,
    val prerequisite_customer_ids: List<String>,
    val title: String,
    val entitled_collection_ids: List<String>,
    val updated_at: String,
    val entitled_country_ids: List<String>,
    val entitled_variant_ids: List<String>,
    val id: String,
    val value: String,
    val allocation_method: String,
    val allocation_limit: Int,
    val target_type: String,
    val entitled_product_ids: List<String>,
    val customer_segment_prerequisite_ids: List<String>,
    val customer_selection: String,
    val target_selection: String,
    val prerequisite_to_entitlement_quantity_ratio: PrerequisiteToEntitlementQuantityRatioItem

)

data class PrerequisiteToEntitlementQuantityRatioItem(
    val prerequisite_quantity: Int,
    val entitled_quantity: Int
)



