package com.example.khizana.data.dto

import com.google.gson.annotations.SerializedName


data class Order(
    val orders: List<OrdersItemEntity>?
)

data class OrdersItemEntity(
    @SerializedName("total_discounts_set")
    val totalDiscountsSet: TotalDiscountsSetEntity?,
    @SerializedName("subtotal_price")
    val subtotalPrice: String?,
    @SerializedName("current_subtotal_price_set")
    val currentSubtotalPriceSet: CurrentSubtotalPriceSetEntity?,
    @SerializedName("current_subtotal_price")
    val currentSubtotalPrice: String?,
    val currency: String?,
    @SerializedName("total_price_set")
    val totalPriceSet: TotalPriceSetEntity?,
    @SerializedName("total_price")
    val totalPrice: String?,
    @SerializedName("total_discounts")
    val totalDiscounts: String?,
    @SerializedName("current_total_price_set")
    val currentTotalPriceSet: CurrentTotalPriceSetEntity?,
)

data class CurrentTotalDiscountsSetEntity(
    @SerializedName("shop_money")
    val shopMoney: ShopMoneyEntity?,
    @SerializedName("presentment_money")
    val presentmentMoney: PresentmentMoneyEntity?
)

data class ShopMoneyEntity(
    val amount: String?,
    @SerializedName("currency_code")
    val currencyCode: String?
)

data class PresentmentMoneyEntity(
    val amount: String?,
    @SerializedName("currency_code")
    val currencyCode: String?
)


data class CurrentSubtotalPriceSetEntity(
    @SerializedName("shop_money")
    val shopMoney: ShopMoneyEntity?,
    @SerializedName("presentment_money")
    val presentmentMoney: PresentmentMoneyEntity?
)

data class TotalPriceSetEntity(
    @SerializedName("shop_money")
    val shopMoney: ShopMoneyEntity?,
    @SerializedName("presentment_money")
    val presentmentMoney: PresentmentMoneyEntity?
)

data class TotalDiscountsSetEntity(
    @SerializedName("shop_money")
    val shopMoney: ShopMoneyEntity?,
    @SerializedName("presentment_money")
    val presentmentMoney: PresentmentMoneyEntity?
)

data class CurrentTotalPriceSetEntity(
    @SerializedName("shop_money")
    val shopMoney: ShopMoneyEntity?,
    @SerializedName("presentment_money")
    val presentmentMoney: PresentmentMoneyEntity?
)







