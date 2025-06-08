package com.example.khizana.domain.model

data class PriceRuleRequestDomain(
    val price_rule: PriceRule
)

data class PriceRule(
    val allocation_method: String,
    val prerequisite_to_entitlement_quantity_ratio: Prerequisite_to_entitlement_quantity_ratio,
    val value_type: String,
    val starts_at: String,
    val allocation_limit: Int,
    val target_type: String,
    val entitled_product_ids: List<String>,
    val title: String,
    val customer_selection: String,
    val target_selection: String,
    val ends_at: String,
    val value: String,
    val prerequisite_collection_ids: List<String>
)

data class Prerequisite_to_entitlement_quantity_ratio(
    val prerequisite_quantity: Int,
    val entitled_quantity: Int
)

