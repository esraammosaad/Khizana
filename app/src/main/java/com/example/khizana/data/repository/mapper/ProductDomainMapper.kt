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
        body_html = body_html,
        images = images?.map { it?.toDomain() },
        created_at = created_at,
        variants = variants?.map { it?.toDomain() },
        title = title,
        product_type = product_type,
        updated_at = updated_at,
        vendor = vendor,
        options = options?.map { it?.toDomain() },
        id = id,
        published_at = published_at,
        status = status
    )
}

fun ImageEntity.toDomain(): Image {
    return Image(
        src = src,
        alt = alt,
        variant_ids = variant_ids,
    )
}

fun ImagesItemEntity.toDomain(): ImagesItem {
    return ImagesItem(
        src = src,
        alt = alt,
        variant_ids = variant_ids,
    )
}

fun VariantsItemEntity.toDomain(): VariantsItem? {
    return VariantsItem(
        old_inventory_quantity = old_inventory_quantity,
        title = title,
        inventory_item_id = inventory_item_id,
        price = price,
        product_id = product_id,
        option3 = option3,
        option1 = option1,
        id = id,
        option2 = option2,
        sku = sku,
        inventory_quantity = inventory_quantity,
    )
}

fun OptionsItemEntity.toDomain(): OptionsItem? {
    return OptionsItem(
        product_id = product_id,
        values = values,
        name = name,
        id = id,
    )
}

