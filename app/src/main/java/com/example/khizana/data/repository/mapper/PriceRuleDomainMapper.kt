package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.PrerequisiteToEntitlementQuantityRatio
import com.example.khizana.data.dto.PriceRule
import com.example.khizana.data.dto.Price_rulesItemEntity
import com.example.khizana.domain.model.PrerequisiteToEntitlementQuantityRatioItem
import com.example.khizana.domain.model.PriceRuleDomain
import com.example.khizana.domain.model.PriceRuleItem

fun PriceRule.toDomain(): PriceRuleDomain {
    return PriceRuleDomain(
        price_rules = price_rules.map { it.toDomain() }
    )
}


fun PriceRuleDomain.toDto(): PriceRule {
    return PriceRule(
        price_rules = price_rules.map { it.toDto() }
    )
}

fun PriceRuleItem.toDto(): Price_rulesItemEntity {
    return Price_rulesItemEntity(
        value_type = value_type,
        once_per_customer = once_per_customer,
        starts_at = starts_at,
        ends_at = ends_at,
        created_at = created_at,
        prerequisite_customer_ids = prerequisite_customer_ids,
        title = title,
        entitled_collection_ids = entitled_collection_ids,
        updated_at = updated_at,
        entitled_country_ids = entitled_country_ids,
        entitled_variant_ids = entitled_variant_ids,
        id = id,
        value = value,
        allocation_method = allocation_method,
        allocation_limit = allocation_limit,
        target_type = target_type,
        entitled_product_ids = entitled_product_ids,
        customer_segment_prerequisite_ids = customer_segment_prerequisite_ids,
        customer_selection = customer_selection,
        target_selection = target_selection,
        prerequisite_to_entitlement_quantity_ratio = prerequisite_to_entitlement_quantity_ratio.toDto()
    )
}

fun PrerequisiteToEntitlementQuantityRatioItem.toDto(): PrerequisiteToEntitlementQuantityRatio {
    return PrerequisiteToEntitlementQuantityRatio(
        prerequisite_quantity = prerequisite_quantity,
        entitled_quantity = entitled_quantity
    )
}

    fun PrerequisiteToEntitlementQuantityRatio.toDomain(): PrerequisiteToEntitlementQuantityRatioItem {
        return PrerequisiteToEntitlementQuantityRatioItem(
            prerequisite_quantity = prerequisite_quantity,
            entitled_quantity = entitled_quantity
        )

    }



fun Price_rulesItemEntity.toDomain(): PriceRuleItem {
    return PriceRuleItem(
        value_type = value_type,
        once_per_customer = once_per_customer,
        starts_at = starts_at,
        ends_at = ends_at,
        created_at = created_at,
        prerequisite_customer_ids = prerequisite_customer_ids,
        title = title,
        entitled_collection_ids = entitled_collection_ids,
        updated_at = updated_at,
        entitled_country_ids = entitled_country_ids,
        entitled_variant_ids = entitled_variant_ids,
        id = id,
        value = value,
        allocation_method = allocation_method,
        allocation_limit = allocation_limit,
        target_type = target_type,
        entitled_product_ids = entitled_product_ids,
        customer_segment_prerequisite_ids = customer_segment_prerequisite_ids,
        customer_selection = customer_selection,
        target_selection = target_selection,
        prerequisite_to_entitlement_quantity_ratio = prerequisite_to_entitlement_quantity_ratio.toDomain()
    )
}



