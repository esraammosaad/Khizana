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

fun ProductsItem.toDto(): ProductsItemEntity {
    return ProductsItemEntity(
        image = image?.toDto(),
        body_html = body_html,
        images = images?.map { it?.toDto() },
        created_at = created_at,
        variants = variants?.map { it?.toDto() },
        title = title,
        product_type = product_type,
        updated_at = updated_at,
        vendor = vendor,
        options = options?.map { it.toDto() },
        id = id,
        published_at = published_at,
        status = status,
        tags = tags
    )
}

fun Image.toDto(): ImageEntity {
    return ImageEntity(
        src = src,
        alt = alt,
        variant_ids = variant_ids,
    )
}

fun ImagesItem.toDto(): ImagesItemEntity {
    return ImagesItemEntity(
        src = src,
        alt = alt,
        variant_ids = variant_ids,
    )
}

fun VariantsItem.toDto(): VariantsItemEntity {
    return VariantsItemEntity(
        title = title,
        price = price,
        option3 = option3,
        option1 = option1,
        option2 = option2,
        inventory_quantity = inventory_quantity,
        inventory_item_id = inventory_item_id
    )
}

fun OptionsItem.toDto(): OptionsItemEntity {
    return OptionsItemEntity(
        values = values,
        name = name,
    )
}

