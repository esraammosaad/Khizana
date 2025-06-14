package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.Order
import com.example.khizana.data.dto.OrdersItemEntity
import com.example.khizana.data.dto.CurrentTotalDiscountsSetEntity
import com.example.khizana.data.dto.ShopMoneyEntity
import com.example.khizana.data.dto.PresentmentMoneyEntity
import com.example.khizana.data.dto.TotalDiscountsSetEntity
import com.example.khizana.data.dto.CurrentSubtotalPriceSetEntity
import com.example.khizana.data.dto.CurrentTotalPriceSetEntity
import com.example.khizana.data.dto.TotalPriceSetEntity
import com.example.khizana.domain.model.OrderDomain
import com.example.khizana.domain.model.OrdersItem
import com.example.khizana.domain.model.CurrentTotalDiscountsSet
import com.example.khizana.domain.model.ShopMoneySet
import com.example.khizana.domain.model.PresentmentMoneySet
import com.example.khizana.domain.model.TotalDiscountsSet
import com.example.khizana.domain.model.CurrentSubtotalPriceSet
import com.example.khizana.domain.model.CurrentTotalPriceSet
import com.example.khizana.domain.model.TotalPriceSet

fun Order.toDomain(): OrderDomain {
    return OrderDomain(
        orders = orders?.map { it.toDomain() }
    )
}

fun OrdersItemEntity.toDomain(): OrdersItem {
    return OrdersItem(
        total_discounts_set = total_discounts_set?.toDomain(),
        current_subtotal_price_set = current_subtotal_price_set?.toDomain(),
        current_subtotal_price = current_subtotal_price,
        total_discounts = total_discounts,
        currency = currency,
        subtotal_price = subtotal_price,
        total_price = total_price,
        current_total_price_set = current_total_price_set?.toDomain(),
    )
}



fun CurrentTotalDiscountsSetEntity.toDomain(): CurrentTotalDiscountsSet {
    return CurrentTotalDiscountsSet(
        shop_money = shop_money?.toDomain(),
        presentment_money = presentment_money?.toDomain()
    )
}

fun CurrentTotalPriceSetEntity.toDomain(): CurrentTotalPriceSet {
    return CurrentTotalPriceSet(
        shop_money = shop_money?.toDomain(),
        presentment_money = presentment_money?.toDomain()
    )
}

fun ShopMoneyEntity.toDomain(): ShopMoneySet {
    return ShopMoneySet(
        amount = amount,
        currency_code = currency_code
    )
}

fun PresentmentMoneyEntity.toDomain(): PresentmentMoneySet {
    return PresentmentMoneySet(
        amount = amount,
        currency_code = currency_code
    )
}

fun TotalDiscountsSetEntity.toDomain(): TotalDiscountsSet {
    return TotalDiscountsSet(
        shop_money = shop_money?.toDomain(),
        presentment_money = presentment_money?.toDomain()
    )
}


fun CurrentSubtotalPriceSetEntity.toDomain(): CurrentSubtotalPriceSet {
    return CurrentSubtotalPriceSet(
        shop_money = shop_money?.toDomain(),
        presentment_money = presentment_money?.toDomain()
    )
}


fun TotalPriceSetEntity.toDomain(): TotalPriceSet {
    return TotalPriceSet(
        shop_money = shop_money?.toDomain(),
        presentment_money = presentment_money?.toDomain()
    )
}



