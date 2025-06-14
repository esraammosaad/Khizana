package com.example.khizana.domain.model

data class OrderDomain(
    val orders: List<OrdersItem?>?
)

data class OrdersItem(
    val total_discounts_set: TotalDiscountsSet?,
    val subtotal_price: String?,
    val current_subtotal_price: String?,
    val current_subtotal_price_set: CurrentSubtotalPriceSet?,
    val currency: String?,
    val total_price: String?,
    val total_discounts: String?,
    val current_total_price_set: CurrentTotalPriceSet?,
)

data class CurrentTotalDiscountsSet(
    val shop_money: ShopMoneySet?,
    val presentment_money: PresentmentMoneySet?
)

data class ShopMoneySet(
    val amount: String?,
    val currency_code: String?
)

data class PresentmentMoneySet(
    val amount: String?,
    val currency_code: String?
)


data class TotalDiscountsSet(
    val shop_money: ShopMoneySet?,
    val presentment_money: PresentmentMoneySet?
)

data class CurrentTotalPriceSet(
    val shop_money: ShopMoneySet?,
    val presentment_money: PresentmentMoneySet?
)


data class CurrentSubtotalPriceSet(
    val shop_money: ShopMoneySet?,
    val presentment_money: PresentmentMoneySet?
)


data class TotalPriceSet(
    val shop_money: ShopMoneySet?,
    val presentment_money: PresentmentMoneySet?
)


