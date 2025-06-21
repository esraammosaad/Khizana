package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.Product
import com.example.khizana.data.dto.ProductsItemEntity
import com.example.khizana.data.dto.ImageEntity
import com.example.khizana.data.dto.ImagesItemEntity
import com.example.khizana.data.dto.VariantsItemEntity
import com.example.khizana.data.dto.OptionsItemEntity
import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.domain.model.ProductsItem
import com.example.khizana.domain.model.Image
import com.example.khizana.domain.model.ImagesItem
import com.example.khizana.domain.model.VariantsItem
import com.example.khizana.domain.model.OptionsItem

fun Product.toDomain(): ProductDomain {
    return ProductDomain(
        products = products?.map { it?.toDomain() }
    )
}

fun ProductsItemEntity.toDomain(): ProductsItem {
    return ProductsItem(
        image = image?.toDomain(),
        body_html = bodyHtml,
        images = images?.map { it?.toDomain() },
        created_at = createdAt,
        variants = variants?.map { it?.toDomain() },
        title = title,
        product_type = productType,
        updated_at = updatedAt,
        vendor = vendor,
        options = options?.map { it.toDomain() },
        id = id,
        published_at = publishedAt,
        status = status,
        tags = tags
    )
}

fun ImageEntity.toDomain(): Image {
    return Image(
        src = src,
        alt = alt,
        variant_ids = variantIds,
    )
}

fun ImagesItemEntity.toDomain(): ImagesItem {
    return ImagesItem(
        src = src,
        alt = alt,
        variant_ids = variantIds,
    )
}

fun VariantsItemEntity.toDomain(): VariantsItem {
    return VariantsItem(
        title = title,
        price = price,
        option3 = option3,
        option1 = option1,
        option2 = option2,
        inventory_quantity = inventoryQuantity,
        inventory_item_id = inventoryItemId
    )
}

fun OptionsItemEntity.toDomain(): OptionsItem {
    return OptionsItem(
        values = values,
        name = name,
    )
}

