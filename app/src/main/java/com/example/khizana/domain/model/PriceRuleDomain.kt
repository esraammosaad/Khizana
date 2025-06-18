package com.example.khizana.domain.model


data class PriceRuleDomain(
    var priceRules: List<PriceRuleItem>
)

data class PriceRuleItem(
    val valueType: String,
    val oncePerCustomer: Boolean,
    val startsAt: String,
    val endsAt: String,
    val createdAt: String,
    val prerequisiteCustomerIds: List<String>,
    var title: String,
    val entitledCollectionIds: List<String>,
    val updatedAt: String,
    val entitledCountryIds: List<String>,
    val entitledVariantIds: List<String>,
    val id: String,
    var value: String,
    val allocationMethod: String,
    val allocationLimit: Int,
    val targetType: String,
    val entitledProductIds: List<String>,
    val customerSegmentPrerequisiteIds: List<String>,
    val customerSelection: String,
    val targetSelection: String,
    val prerequisiteToEntitlementQuantityRatio: PrerequisiteToEntitlementQuantityRatioItem

)

data class PrerequisiteToEntitlementQuantityRatioItem(
    val prerequisiteQuantity: Int,
    val entitledQuantity: Int
)



