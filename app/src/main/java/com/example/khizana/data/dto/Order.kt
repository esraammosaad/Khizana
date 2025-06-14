package com.example.khizana.data.dto


data class Order(
    val orders: List<OrdersItemEntity>?
)

data class OrdersItemEntity(
    val total_discounts_set: TotalDiscountsSetEntity?,
    val subtotal_price: String?,
    val current_subtotal_price_set: CurrentSubtotalPriceSetEntity?,
    val current_subtotal_price: String?,
    val currency: String?,
    val total_price_set: TotalPriceSetEntity?,
    val total_price: String?,
    val total_discounts: String?,
    val current_total_price_set: CurrentTotalPriceSetEntity?,
)

data class CurrentTotalDiscountsSetEntity(
    val shop_money: ShopMoneyEntity?,
    val presentment_money: PresentmentMoneyEntity?
)

data class ShopMoneyEntity(
    val amount: String?,
    val currency_code: String?
)

data class PresentmentMoneyEntity(
    val amount: String?,
    val currency_code: String?
)


data class CurrentSubtotalPriceSetEntity(
    val shop_money: ShopMoneyEntity?,
    val presentment_money: PresentmentMoneyEntity?
)

data class TotalPriceSetEntity(
    val shop_money: ShopMoneyEntity?,
    val presentment_money: PresentmentMoneyEntity?
)

data class TotalDiscountsSetEntity(
    val shop_money: ShopMoneyEntity?,
    val presentment_money: PresentmentMoneyEntity?
)

data class CurrentTotalPriceSetEntity(
    val shop_money: ShopMoneyEntity?,
    val presentment_money: PresentmentMoneyEntity?
)







