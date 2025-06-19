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
        orders = orders?.map { it.toDomain() } ?: listOf()
    )
}

fun OrdersItemEntity.toDomain(): OrdersItem {
    return OrdersItem(
        total_discounts_set = totalDiscountsSet?.toDomain(),
        current_subtotal_price_set = currentSubtotalPriceSet?.toDomain(),
        current_subtotal_price = currentSubtotalPrice,
        total_discounts = totalDiscounts,
        currency = currency,
        subtotal_price = subtotalPrice,
        total_price = totalPrice,
        current_total_price_set = currentTotalPriceSet?.toDomain(),
    )
}



fun CurrentTotalDiscountsSetEntity.toDomain(): CurrentTotalDiscountsSet {
    return CurrentTotalDiscountsSet(
        shop_money = shopMoney?.toDomain(),
        presentment_money = presentmentMoney?.toDomain()
    )
}

fun CurrentTotalPriceSetEntity.toDomain(): CurrentTotalPriceSet {
    return CurrentTotalPriceSet(
        shop_money = shopMoney?.toDomain(),
        presentment_money = presentmentMoney?.toDomain()
    )
}

fun ShopMoneyEntity.toDomain(): ShopMoneySet {
    return ShopMoneySet(
        amount = amount,
        currency_code = currencyCode
    )
}

fun PresentmentMoneyEntity.toDomain(): PresentmentMoneySet {
    return PresentmentMoneySet(
        amount = amount,
        currency_code = currencyCode
    )
}

fun TotalDiscountsSetEntity.toDomain(): TotalDiscountsSet {
    return TotalDiscountsSet(
        shop_money = shopMoney?.toDomain(),
        presentment_money = presentmentMoney?.toDomain()
    )
}


fun CurrentSubtotalPriceSetEntity.toDomain(): CurrentSubtotalPriceSet {
    return CurrentSubtotalPriceSet(
        shop_money = shopMoney?.toDomain(),
        presentment_money = presentmentMoney?.toDomain()
    )
}


fun TotalPriceSetEntity.toDomain(): TotalPriceSet {
    return TotalPriceSet(
        shop_money = shopMoney?.toDomain(),
        presentment_money = presentmentMoney?.toDomain()
    )
}



