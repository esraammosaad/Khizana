package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.PriceRule
import com.example.khizana.data.dto.Price_rulesItemEntity
import com.example.khizana.data.dto.Prerequisite_to_entitlement_quantity_ratioEntity
import com.example.khizana.data.dto.Prerequisite_to_entitlement_purchaseEntity
import com.example.khizana.domain.model.PriceRuleDomain
import com.example.khizana.domain.model.Price_rulesItem
import com.example.khizana.domain.model.Prerequisite_to_entitlement_quantity_ratio
import com.example.khizana.domain.model.Prerequisite_to_entitlement_purchase

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

fun Price_rulesItem.toDto(): Price_rulesItemEntity {
    return Price_rulesItemEntity(
        value_type = value_type,
        once_per_customer = once_per_customer,
        usage_limit = usage_limit,
        starts_at = starts_at,
        created_at = created_at,
        prerequisite_customer_ids = prerequisite_customer_ids,
        title = title,
        entitled_collection_ids = entitled_collection_ids,
        updated_at = updated_at,
        prerequisite_product_ids = prerequisite_product_ids,
        prerequisite_shipping_price_range = prerequisite_shipping_price_range,
        entitled_country_ids = entitled_country_ids,
        entitled_variant_ids = entitled_variant_ids,
        id = id,
        ends_at = ends_at,
        value = value,
        prerequisite_subtotal_range = prerequisite_subtotal_range,
        allocation_method = allocation_method,
        prerequisite_to_entitlement_quantity_ratio = prerequisite_to_entitlement_quantity_ratio.toDto(),
        prerequisite_quantity_range = prerequisite_quantity_range,
        allocation_limit = allocation_limit,
        target_type = target_type,
        entitled_product_ids = entitled_product_ids,
        customer_segment_prerequisite_ids = customer_segment_prerequisite_ids,
        customer_selection = customer_selection,
        prerequisite_variant_ids = prerequisite_variant_ids,
        target_selection = target_selection,
        prerequisite_to_entitlement_purchase = prerequisite_to_entitlement_purchase.toDto(),
        prerequisite_collection_ids =prerequisite_collection_ids,
    )
}

fun Prerequisite_to_entitlement_quantity_ratio.toDto(): Prerequisite_to_entitlement_quantity_ratioEntity {
    return Prerequisite_to_entitlement_quantity_ratioEntity(
        prerequisite_quantity = prerequisite_quantity,
        entitled_quantity = entitled_quantity
    )
}

fun Prerequisite_to_entitlement_purchase.toDto(): Prerequisite_to_entitlement_purchaseEntity {
    return Prerequisite_to_entitlement_purchaseEntity(
        prerequisite_amount = prerequisite_amount
    )
}

fun Price_rulesItemEntity.toDomain(): Price_rulesItem {
    return Price_rulesItem(
        value_type = value_type,
        once_per_customer = once_per_customer,
        usage_limit = usage_limit,
        starts_at = starts_at,
        created_at = created_at,
        prerequisite_customer_ids = prerequisite_customer_ids,
        title = title,
        entitled_collection_ids = entitled_collection_ids,
        updated_at = updated_at,
        prerequisite_product_ids = prerequisite_product_ids,
        prerequisite_shipping_price_range = prerequisite_shipping_price_range,
        entitled_country_ids = entitled_country_ids,
        entitled_variant_ids = entitled_variant_ids,
        id = id,
        ends_at = ends_at,
        value = value,
        prerequisite_subtotal_range = prerequisite_subtotal_range,
        allocation_method = allocation_method,
        prerequisite_to_entitlement_quantity_ratio = prerequisite_to_entitlement_quantity_ratio.toDomain(),
        prerequisite_quantity_range = prerequisite_quantity_range,
        allocation_limit = allocation_limit,
        target_type = target_type,
        entitled_product_ids = entitled_product_ids,
        customer_segment_prerequisite_ids = customer_segment_prerequisite_ids,
        customer_selection = customer_selection,
        prerequisite_variant_ids = prerequisite_variant_ids,
        target_selection = target_selection,
        prerequisite_to_entitlement_purchase = prerequisite_to_entitlement_purchase.toDomain(),
        prerequisite_collection_ids = prerequisite_collection_ids
    )
}

fun Prerequisite_to_entitlement_quantity_ratioEntity.toDomain(): Prerequisite_to_entitlement_quantity_ratio {
    return Prerequisite_to_entitlement_quantity_ratio(
        prerequisite_quantity = prerequisite_quantity,
        entitled_quantity = entitled_quantity
    )
}

fun Prerequisite_to_entitlement_purchaseEntity.toDomain(): Prerequisite_to_entitlement_purchase {
    return Prerequisite_to_entitlement_purchase(
        prerequisite_amount = prerequisite_amount
    )
}

