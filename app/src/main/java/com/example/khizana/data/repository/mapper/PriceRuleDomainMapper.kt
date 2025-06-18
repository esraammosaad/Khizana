package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.PrerequisiteToEntitlementQuantityRatio
import com.example.khizana.data.dto.PriceRule
import com.example.khizana.data.dto.PriceRulesItemEntity
import com.example.khizana.domain.model.PrerequisiteToEntitlementQuantityRatioItem
import com.example.khizana.domain.model.PriceRuleDomain
import com.example.khizana.domain.model.PriceRuleItem

fun PriceRule.toDomain(): PriceRuleDomain {
    return PriceRuleDomain(
        price_rules = priceRules.map { it.toDomain() }
    )
}


fun PriceRuleDomain.toDto(): PriceRule {
    return PriceRule(
        priceRules = price_rules.map { it.toDto() }
    )
}

fun PriceRuleItem.toDto(): PriceRulesItemEntity {
    return PriceRulesItemEntity(
        valueType = value_type,
        oncePerCustomer = once_per_customer,
        startsAt = starts_at,
        endsAt = ends_at,
        createdAt = created_at,
        prerequisiteCustomerIds = prerequisite_customer_ids,
        title = title,
        entitledCollectionIds = entitled_collection_ids,
        updatedAt = updated_at,
        entitledCountryIds = entitled_country_ids,
        entitledVariantIds = entitled_variant_ids,
        id = id,
        value = value,
        allocationMethod = allocation_method,
        allocationLimit = allocation_limit,
        targetType = target_type,
        entitledProductIds = entitled_product_ids,
        customerSegmentPrerequisiteIds = customer_segment_prerequisite_ids,
        customerSelection = customer_selection,
        targetSelection = target_selection,
        prerequisiteToEntitlementQuantityRatio = prerequisite_to_entitlement_quantity_ratio.toDto()
    )
}

fun PrerequisiteToEntitlementQuantityRatioItem.toDto(): PrerequisiteToEntitlementQuantityRatio {
    return PrerequisiteToEntitlementQuantityRatio(
        prerequisiteQuantity = prerequisite_quantity,
        entitledQuantity = entitled_quantity
    )
}

    fun PrerequisiteToEntitlementQuantityRatio.toDomain(): PrerequisiteToEntitlementQuantityRatioItem {
        return PrerequisiteToEntitlementQuantityRatioItem(
            prerequisite_quantity = prerequisiteQuantity,
            entitled_quantity = entitledQuantity
        )

    }



fun PriceRulesItemEntity.toDomain(): PriceRuleItem {
    return PriceRuleItem(
        value_type = valueType,
        once_per_customer = oncePerCustomer,
        starts_at = startsAt,
        ends_at = endsAt,
        created_at = createdAt,
        prerequisite_customer_ids = prerequisiteCustomerIds,
        title = title,
        entitled_collection_ids = entitledCollectionIds,
        updated_at = updatedAt,
        entitled_country_ids = entitledCountryIds,
        entitled_variant_ids = entitledVariantIds,
        id = id,
        value = value,
        allocation_method = allocationMethod,
        allocation_limit = allocationLimit,
        target_type = targetType,
        entitled_product_ids = entitledProductIds,
        customer_segment_prerequisite_ids = customerSegmentPrerequisiteIds,
        customer_selection = customerSelection,
        target_selection = targetSelection,
        prerequisite_to_entitlement_quantity_ratio = prerequisiteToEntitlementQuantityRatio.toDomain()
    )
}



