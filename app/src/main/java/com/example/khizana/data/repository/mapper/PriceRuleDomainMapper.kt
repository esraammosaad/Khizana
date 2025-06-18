package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.PrerequisiteToEntitlementQuantityRatio
import com.example.khizana.data.dto.PriceRule
import com.example.khizana.data.dto.PriceRulesItemEntity
import com.example.khizana.domain.model.PrerequisiteToEntitlementQuantityRatioItem
import com.example.khizana.domain.model.PriceRuleDomain
import com.example.khizana.domain.model.PriceRuleItem

fun PriceRule.toDomain(): PriceRuleDomain {
    return PriceRuleDomain(
        priceRules = priceRules.map { it.toDomain() }
    )
}


fun PriceRuleDomain.toDto(): PriceRule {
    return PriceRule(
        priceRules = priceRules.map { it.toDto() }
    )
}

fun PriceRuleItem.toDto(): PriceRulesItemEntity {
    return PriceRulesItemEntity(
        valueType = valueType,
        oncePerCustomer = oncePerCustomer,
        startsAt = startsAt,
        endsAt = endsAt,
        createdAt = createdAt,
        prerequisiteCustomerIds = prerequisiteCustomerIds,
        title = title,
        entitledCollectionIds = entitledCollectionIds,
        updatedAt = updatedAt,
        entitledCountryIds = entitledCountryIds,
        entitledVariantIds = entitledVariantIds,
        id = id,
        value = value,
        allocationMethod = allocationMethod,
        allocationLimit = allocationLimit,
        targetType = targetType,
        entitledProductIds = entitledProductIds,
        customerSegmentPrerequisiteIds = customerSegmentPrerequisiteIds,
        customerSelection = customerSelection,
        targetSelection = targetSelection,
        prerequisiteToEntitlementQuantityRatio = prerequisiteToEntitlementQuantityRatio.toDto()
    )
}

fun PrerequisiteToEntitlementQuantityRatioItem.toDto(): PrerequisiteToEntitlementQuantityRatio {
    return PrerequisiteToEntitlementQuantityRatio(
        prerequisiteQuantity = prerequisiteQuantity,
        entitledQuantity = entitledQuantity
    )
}

    fun PrerequisiteToEntitlementQuantityRatio.toDomain(): PrerequisiteToEntitlementQuantityRatioItem {
        return PrerequisiteToEntitlementQuantityRatioItem(
            prerequisiteQuantity = prerequisiteQuantity,
            entitledQuantity = entitledQuantity
        )

    }



fun PriceRulesItemEntity.toDomain(): PriceRuleItem {
    return PriceRuleItem(
        valueType = valueType,
        oncePerCustomer = oncePerCustomer,
        startsAt = startsAt,
        endsAt = endsAt,
        createdAt = createdAt,
        prerequisiteCustomerIds = prerequisiteCustomerIds,
        title = title,
        entitledCollectionIds = entitledCollectionIds,
        updatedAt = updatedAt,
        entitledCountryIds = entitledCountryIds,
        entitledVariantIds = entitledVariantIds,
        id = id,
        value = value,
        allocationMethod = allocationMethod,
        allocationLimit = allocationLimit,
        targetType = targetType,
        entitledProductIds = entitledProductIds,
        customerSegmentPrerequisiteIds = customerSegmentPrerequisiteIds,
        customerSelection = customerSelection,
        targetSelection = targetSelection,
        prerequisiteToEntitlementQuantityRatio = prerequisiteToEntitlementQuantityRatio.toDomain()
    )
}



