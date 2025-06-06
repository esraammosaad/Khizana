package com.example.khizana.data.dto

data class Order(
    val orders: List<OrdersItemEntity>?
)

data class OrdersItemEntity(
    val cancelled_at: String?,
    val confirmation_number: String?,
    val fulfillment_status: String?,
    val original_total_additional_fees_set: String?,
    val current_total_discounts_set: Current_total_discounts_setEntity?,
    val billing_address: Billing_addressEntity?,
    val line_items: List<Line_itemsItemEntity>?,
    val original_total_duties_set: String?,
    val presentment_currency: String?,
    val total_discounts_set: Total_discounts_setEntity?,
    val location_id: String?,
    val source_url: String?,
    val landing_site: String?,
    val source_identifier: String?,
    val reference: String?,
    val number: Int?,
    val checkout_id: String?,
    val checkout_token: String?,
    val current_total_discounts: String?,
    val merchant_of_record_app_id: String?,
    val customer_locale: String?,
    val current_total_additional_fees_set: String?,
    val id: String?,
    val app_id: Long?,
    val subtotal_price: String?,
    val closed_at: String?,
    val order_status_url: String?,
    val current_total_price_set: Current_total_price_setEntity?,
    val device_id: String?,
    val test: Boolean?,
    val total_shipping_price_set: Total_shipping_price_setEntity?,
    val subtotal_price_set: Subtotal_price_setEntity?,
    val tax_exempt: Boolean?,
    val payment_gateway_names: List<String>?,
    val total_tax: String?,
    val tags: String?,
    val current_subtotal_price_set: Current_subtotal_price_setEntity?,
    val current_total_tax: String?,
    val phone: String?,
    val user_id: String?,
    val note_attributes: List<String>?,
    val name: String?,
    val cart_token: String?,
    val total_tax_set: Total_tax_setEntity?,
    val landing_site_ref: String?,
    val discount_codes: List<String>?,
    val estimated_taxes: Boolean?,
    val note: String?,
    val current_subtotal_price: String?,
    val current_total_tax_set: Current_total_tax_setEntity?,
    val total_outstanding: String?,
    val order_number: Int?,
    val discount_applications: List<String>?,
    val created_at: String?,
    val total_line_items_price_set: Total_line_items_price_setEntity?,
    val taxes_included: Boolean?,
    val buyer_accepts_marketing: Boolean?,
    val confirmed: Boolean?,
    val total_weight: Int?,
    val contact_email: String?,
    val total_discounts: String?,
    val fulfillments: List<String>?,
    val client_details: String?,
    val po_number: String?,
    val referring_site: String?,
    val updated_at: String?,
    val processed_at: String?,
    val company: String?,
    val currency: String?,
    val browser_ip: String?,
    val email: String?,
    val source_name: String?,
    val total_price_set: Total_price_setEntity?,
    val current_total_duties_set: String?,
    val total_price: String?,
    val total_line_items_price: String?,
    val total_tip_received: String?,
    val token: String?,
    val cancel_reason: String?,
    val current_total_price: String?,
    val admin_graphql_api_id: String?,
    val financial_status: String?,
    val customer: CustomerEntity?
)

data class Current_total_discounts_setEntity(
    val shop_money: Shop_moneyEntity?,
    val presentment_money: Presentment_moneyEntity?
)

data class Shop_moneyEntity(
    val amount: String?,
    val currency_code: String?
)

data class Presentment_moneyEntity(
    val amount: String?,
    val currency_code: String?
)

data class Billing_addressEntity(
    val zip: String?,
    val country: String?,
    val city: String?,
    val address2: String?,
    val address1: String?,
    val latitude: String?,
    val last_name: String?,
    val province_code: String?,
    val country_code: String?,
    val province: String?,
    val phone: String?,
    val name: String?,
    val company: String?,
    val first_name: String?,
    val longitude: String?
)

data class Line_itemsItemEntity(
    val fulfillable_quantity: Int?,
    val fulfillment_status: String?,
    val quantity: Int?,
    val total_discount: String?,
    val fulfillment_service: String?,
    val gift_card: Boolean?,
    val taxable: Boolean?,
    val requires_shipping: Boolean?,
    val total_discount_set: Total_discount_setEntity?,
    val title: String?,
    val current_quantity: Int?,
    val attributed_staffs: List<String>?,
    val product_exists: Boolean?,
    val price: String?,
    val admin_graphql_api_id: String?,
    val product_id: String?,
    val name: String?,
    val id: String?,
    val grams: Int?,
    val price_set: Price_setEntity?,
    val sku: String?,
    val properties: List<String>?
)

data class Total_discount_setEntity(
    val shop_money: Shop_moneyEntity?,
    val presentment_money: Presentment_moneyEntity?
)

data class Price_setEntity(
    val shop_money: Shop_moneyEntity?,
    val presentment_money: Presentment_moneyEntity?
)

data class Total_discounts_setEntity(
    val shop_money: Shop_moneyEntity?,
    val presentment_money: Presentment_moneyEntity?
)

data class Current_total_price_setEntity(
    val shop_money: Shop_moneyEntity?,
    val presentment_money: Presentment_moneyEntity?
)

data class Total_shipping_price_setEntity(
    val shop_money: Shop_moneyEntity?,
    val presentment_money: Presentment_moneyEntity?
)

data class Subtotal_price_setEntity(
    val shop_money: Shop_moneyEntity?,
    val presentment_money: Presentment_moneyEntity?
)

data class Current_subtotal_price_setEntity(
    val shop_money: Shop_moneyEntity?,
    val presentment_money: Presentment_moneyEntity?
)

data class Total_tax_setEntity(
    val shop_money: Shop_moneyEntity?,
    val presentment_money: Presentment_moneyEntity?
)

data class Current_total_tax_setEntity(
    val shop_money: Shop_moneyEntity?,
    val presentment_money: Presentment_moneyEntity?
)

data class Total_line_items_price_setEntity(
    val shop_money: Shop_moneyEntity?,
    val presentment_money: Presentment_moneyEntity?
)

data class Total_price_setEntity(
    val shop_money: Shop_moneyEntity?,
    val presentment_money: Presentment_moneyEntity?
)

data class CustomerEntity(
    val note: String?,
    val tax_exempt: Boolean?,
    val email_marketing_consent: Email_marketing_consentEntity?,
    val created_at: String?,
    val last_name: String?,
    val multipass_identifier: String?,
    val verified_email: Boolean?,
    val tags: String?,
    val sms_marketing_consent: Sms_marketing_consentEntity?,
    val default_address: Default_addressEntity?,
    val updated_at: String?,
    val phone: String?,
    val admin_graphql_api_id: String?,
    val tax_exemptions: List<String>?,
    val currency: String?,
    val id: String?,
    val state: String?,
    val first_name: String?,
    val email: String?
)

data class Email_marketing_consentEntity(
    val consent_updated_at: String?,
    val state: String?,
    val opt_in_level: String?
)

data class Sms_marketing_consentEntity(
    val consent_updated_at: String?,
    val consent_collected_from: String?,
    val state: String?,
    val opt_in_level: String?
)

data class Default_addressEntity(
    val zip: String?,
    val country: String?,
    val address2: String?,
    val city: String?,
    val address1: String?,
    val last_name: String?,
    val province_code: String?,
    val country_code: String?,
    val default: Boolean?,
    val province: String?,
    val phone: String?,
    val name: String?,
    val country_name: String?,
    val company: String?,
    val id: String?,
    val customer_id: String?,
    val first_name: String?
)
