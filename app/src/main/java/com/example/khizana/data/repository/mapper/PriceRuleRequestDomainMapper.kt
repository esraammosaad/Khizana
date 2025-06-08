package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.PriceRuleRequest
import com.example.khizana.data.dto.Price_ruleEntity
import com.example.khizana.data.dto.Prerequisite_to_entitlement_quantity_ratioEntity
import com.example.khizana.domain.model.PriceRuleRequestDomain
import com.example.khizana.domain.model.PriceRule
import com.example.khizana.domain.model.Prerequisite_to_entitlement_quantity_ratio

fun PriceRuleRequest.toDomain(): PriceRuleRequestDomain {
    return PriceRuleRequestDomain(
        price_rule = price_rule.toDomain()
    )
}

fun Price_ruleEntity.toDomain(): PriceRule {
    return PriceRule(
        allocation_method = allocation_method,
        prerequisite_to_entitlement_quantity_ratio = prerequisite_to_entitlement_quantity_ratio.toDomain(),
        value_type = value_type,
        starts_at = starts_at,
        allocation_limit = allocation_limit,
        target_type = target_type,
        entitled_product_ids = entitled_product_ids,
        title = title,
        customer_selection = customer_selection,
        target_selection = target_selection,
        ends_at = ends_at,
        value = value,
        prerequisite_collection_ids = prerequisite_collection_ids
    )
}

fun Prerequisite_to_entitlement_quantity_ratioEntity.toDomain(): Prerequisite_to_entitlement_quantity_ratio {
    return Prerequisite_to_entitlement_quantity_ratio(
        prerequisite_quantity = prerequisite_quantity,
        entitled_quantity = entitled_quantity
    )
}

fun PriceRuleRequestDomain.toDto(): PriceRuleRequest {
    return PriceRuleRequest(
        price_rule = price_rule.toDto()
    )
}

fun PriceRule.toDto(): Price_ruleEntity {
    return Price_ruleEntity(
        allocation_method = allocation_method,
        prerequisite_to_entitlement_quantity_ratio = prerequisite_to_entitlement_quantity_ratio.toDto(),
        value_type = value_type,
        starts_at = starts_at,
        allocation_limit = allocation_limit,
        target_type = target_type,
        entitled_product_ids = entitled_product_ids,
        title = title,
        customer_selection = customer_selection,
        target_selection = target_selection,
        ends_at = ends_at,
        value = value,
        prerequisite_collection_ids = prerequisite_collection_ids
    )
}

fun Prerequisite_to_entitlement_quantity_ratio.toDto(): Prerequisite_to_entitlement_quantity_ratioEntity {
    return Prerequisite_to_entitlement_quantity_ratioEntity(
        prerequisite_quantity = prerequisite_quantity,
        entitled_quantity = entitled_quantity
    )
}

