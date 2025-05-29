package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.ImageEntity
import com.example.khizana.data.dto.ImagesItemEntity
import com.example.khizana.data.dto.OptionsItemEntity
import com.example.khizana.data.dto.ProductRequest
import com.example.khizana.data.dto.ProductsItemEntity
import com.example.khizana.data.dto.VariantsItemEntity
import com.example.khizana.domain.model.Image
import com.example.khizana.domain.model.ImagesItem
import com.example.khizana.domain.model.OptionsItem
import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.model.ProductsItem
import com.example.khizana.domain.model.VariantsItem

fun ProductRequest.toDomain(): ProductRequestDomain {
    return ProductRequestDomain(
        product = product?.toDomain()
    )
}

fun ProductRequestDomain.toDto(): ProductRequest {

    return ProductRequest(
        product = product?.toDto()
    )
}

fun ProductsItem.toDto(): ProductsItemEntity? {
    return ProductsItemEntity(
        image = image?.toDto(),
        body_html = body_html,
        images = images?.map { it?.toDto() },
        created_at = created_at,
        handle = handle,
        variants = variants?.map { it?.toDto() },
        title = title,
        tags = tags,
        published_scope = published_scope,
        product_type = product_type,
        template_suffix = template_suffix,
        updated_at = updated_at,
        vendor = vendor,
        admin_graphql_api_id = admin_graphql_api_id,
        options = options?.map { it?.toDto() },
        id = id,
        published_at = published_at,
        status = status
    ) ?: null
}

fun Image.toDto(): ImageEntity? {
    return ImageEntity(
        src = src,
        alt = alt,
        variant_ids = variant_ids,
    ) ?: null
}

fun ImagesItem.toDto(): ImagesItemEntity? {
    return ImagesItemEntity(
        src = src,
        alt = alt,
        variant_ids = variant_ids,
    ) ?: null
}

fun VariantsItem.toDto(): VariantsItemEntity? {
    return VariantsItemEntity(
        inventory_management = inventory_management,
        requires_shipping = requires_shipping,
        old_inventory_quantity = old_inventory_quantity,
        created_at = created_at,
        title = title,
        updated_at = updated_at,
        inventory_item_id = inventory_item_id,
        price = price,
        product_id = product_id,
        option3 = option3,
        option1 = option1,
        id = id,
        option2 = option2,
        grams = grams,
        sku = sku,
        barcode = barcode,
        inventory_quantity = inventory_quantity,
        compare_at_price = compare_at_price,
        taxable = taxable,
        fulfillment_service = fulfillment_service,
        weight = weight,
        inventory_policy = inventory_policy,
        weight_unit = weight_unit,
        admin_graphql_api_id = admin_graphql_api_id,
        position = position,
        image_id = image_id
    ) ?: null
}

fun OptionsItem.toDto(): OptionsItemEntity? {
    return OptionsItemEntity(
        product_id = product_id,
        values = values,
        name = name,
        id = id,
        position = position
    ) ?: null
}

