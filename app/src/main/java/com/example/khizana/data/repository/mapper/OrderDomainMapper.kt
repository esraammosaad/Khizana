package com.example.khizana.data.repository.mapper

import com.example.khizana.data.dto.Order
import com.example.khizana.data.dto.OrdersItemEntity
import com.example.khizana.data.dto.Current_total_discounts_setEntity
import com.example.khizana.data.dto.Shop_moneyEntity
import com.example.khizana.data.dto.Presentment_moneyEntity
import com.example.khizana.data.dto.Billing_addressEntity
import com.example.khizana.data.dto.Line_itemsItemEntity
import com.example.khizana.data.dto.Total_discount_setEntity
import com.example.khizana.data.dto.Price_setEntity
import com.example.khizana.data.dto.Total_discounts_setEntity
import com.example.khizana.data.dto.Current_total_price_setEntity
import com.example.khizana.data.dto.Total_shipping_price_setEntity
import com.example.khizana.data.dto.Subtotal_price_setEntity
import com.example.khizana.data.dto.Current_subtotal_price_setEntity
import com.example.khizana.data.dto.Total_tax_setEntity
import com.example.khizana.data.dto.Current_total_tax_setEntity
import com.example.khizana.data.dto.Total_line_items_price_setEntity
import com.example.khizana.data.dto.Total_price_setEntity
import com.example.khizana.data.dto.CustomerEntity
import com.example.khizana.data.dto.Email_marketing_consentEntity
import com.example.khizana.data.dto.Sms_marketing_consentEntity
import com.example.khizana.data.dto.Default_addressEntity
import com.example.khizana.domain.model.OrderDomain
import com.example.khizana.domain.model.OrdersItem
import com.example.khizana.domain.model.Current_total_discounts_set
import com.example.khizana.domain.model.Shop_money
import com.example.khizana.domain.model.Presentment_money
import com.example.khizana.domain.model.Billing_address
import com.example.khizana.domain.model.Line_itemsItem
import com.example.khizana.domain.model.Total_discount_set
import com.example.khizana.domain.model.Price_set
import com.example.khizana.domain.model.Total_discounts_set
import com.example.khizana.domain.model.Current_total_price_set
import com.example.khizana.domain.model.Total_shipping_price_set
import com.example.khizana.domain.model.Subtotal_price_set
import com.example.khizana.domain.model.Current_subtotal_price_set
import com.example.khizana.domain.model.Total_tax_set
import com.example.khizana.domain.model.Current_total_tax_set
import com.example.khizana.domain.model.Total_line_items_price_set
import com.example.khizana.domain.model.Total_price_set
import com.example.khizana.domain.model.Customer
import com.example.khizana.domain.model.Email_marketing_consent
import com.example.khizana.domain.model.Sms_marketing_consent
import com.example.khizana.domain.model.Default_address

fun Order.toDomain(): OrderDomain {
    return OrderDomain(
        orders = orders?.map { it.toDomain() }
    )
}

fun OrdersItemEntity.toDomain(): OrdersItem {
    return OrdersItem(
        cancelled_at = cancelled_at,
        confirmation_number = confirmation_number,
        fulfillment_status = fulfillment_status,
        original_total_additional_fees_set = original_total_additional_fees_set,
        current_total_discounts_set = current_total_discounts_set?.toDomain(),
        billing_address = billing_address?.toDomain(),
        line_items = line_items?.map { it.toDomain() },
        original_total_duties_set = original_total_duties_set,
        presentment_currency = presentment_currency,
        total_discounts_set = total_discounts_set?.toDomain(),
        location_id = location_id,
        source_url = source_url,
        landing_site = landing_site,
        source_identifier = source_identifier,
        reference = reference,
        number = number,
        checkout_id = checkout_id,
        checkout_token = checkout_token,
        tax_lines = tax_lines,
        current_total_discounts = current_total_discounts,
        merchant_of_record_app_id = merchant_of_record_app_id,
        customer_locale = customer_locale,
        current_total_additional_fees_set = current_total_additional_fees_set,
        id = id,
        app_id = app_id,
        subtotal_price = subtotal_price,
        closed_at = closed_at,
        order_status_url = order_status_url,
        current_total_price_set = current_total_price_set?.toDomain(),
        device_id = device_id,
        test = test,
        total_shipping_price_set = total_shipping_price_set?.toDomain(),
        subtotal_price_set = subtotal_price_set?.toDomain(),
        tax_exempt = tax_exempt,
        payment_gateway_names = payment_gateway_names,
        total_tax = total_tax,
        tags = tags,
        current_subtotal_price_set = current_subtotal_price_set?.toDomain(),
        current_total_tax = current_total_tax,
        phone = phone,
        user_id = user_id,
        note_attributes = note_attributes,
        name = name,
        cart_token = cart_token,
        total_tax_set = total_tax_set?.toDomain(),
        landing_site_ref = landing_site_ref,
        discount_codes = discount_codes,
        estimated_taxes = estimated_taxes,
        note = note,
        current_subtotal_price = current_subtotal_price,
        current_total_tax_set = current_total_tax_set?.toDomain(),
        total_outstanding = total_outstanding,
        order_number = order_number,
        discount_applications = discount_applications,
        created_at = created_at,
        total_line_items_price_set = total_line_items_price_set?.toDomain(),
        taxes_included = taxes_included,
        buyer_accepts_marketing = buyer_accepts_marketing,
        confirmed = confirmed,
        total_weight = total_weight,
        contact_email = contact_email,
        total_discounts = total_discounts,
        fulfillments = fulfillments,
        client_details = client_details,
        po_number = po_number,
        referring_site = referring_site,
        updated_at = updated_at,
        processed_at = processed_at,
        company = company,
        currency = currency,
        browser_ip = browser_ip,
        email = email,
        source_name = source_name,
        total_price_set = total_price_set?.toDomain(),
        current_total_duties_set = current_total_duties_set,
        total_price = total_price,
        total_line_items_price = total_line_items_price,
        total_tip_received = total_tip_received,
        token = token,
        cancel_reason = cancel_reason,
        current_total_price = current_total_price,
        admin_graphql_api_id = admin_graphql_api_id,
        financial_status = financial_status,
        customer = customer?.toDomain()
    )
}

fun Current_total_discounts_setEntity.toDomain(): Current_total_discounts_set {
    return Current_total_discounts_set(
        shop_money = shop_money?.toDomain(),
        presentment_money = presentment_money?.toDomain()
    )
}

fun Shop_moneyEntity.toDomain(): Shop_money {
    return Shop_money(
        amount = amount,
        currency_code = currency_code
    )
}

fun Presentment_moneyEntity.toDomain(): Presentment_money {
    return Presentment_money(
        amount = amount,
        currency_code = currency_code
    )
}

fun Billing_addressEntity.toDomain(): Billing_address {
    return Billing_address(
        zip = zip,
        country = country,
        city = city,
        address2 = address2,
        address1 = address1,
        latitude = latitude,
        last_name = last_name,
        province_code = province_code,
        country_code = country_code,
        province = province,
        phone = phone,
        name = name,
        company = company,
        first_name = first_name,
        longitude = longitude
    )
}

fun Line_itemsItemEntity.toDomain(): Line_itemsItem {
    return Line_itemsItem(
        fulfillable_quantity = fulfillable_quantity,
        fulfillment_status = fulfillment_status,
        quantity = quantity,
        total_discount = total_discount,
        fulfillment_service = fulfillment_service,
        gift_card = gift_card,
        taxable = taxable,
        requires_shipping = requires_shipping,
        total_discount_set = total_discount_set?.toDomain(),
        title = title,
        current_quantity = current_quantity,
        attributed_staffs = attributed_staffs,
        product_exists = product_exists,
        price = price,
        admin_graphql_api_id = admin_graphql_api_id,
        product_id = product_id,
        name = name,
        id = id,
        grams = grams,
        price_set = price_set?.toDomain(),
        sku = sku,
        properties = properties
    )
}

fun Total_discount_setEntity.toDomain(): Total_discount_set {
    return Total_discount_set(
        shop_money = shop_money?.toDomain(),
        presentment_money = presentment_money?.toDomain()
    )
}

fun Price_setEntity.toDomain(): Price_set {
    return Price_set(
        shop_money = shop_money?.toDomain(),
        presentment_money = presentment_money?.toDomain()
    )
}


fun Total_discounts_setEntity.toDomain(): Total_discounts_set {
    return Total_discounts_set(
        shop_money = shop_money?.toDomain(),
        presentment_money = presentment_money?.toDomain()
    )
}

fun Current_total_price_setEntity.toDomain(): Current_total_price_set {
    return Current_total_price_set(
        shop_money = shop_money?.toDomain(),
        presentment_money = presentment_money?.toDomain()
    )
}

fun Total_shipping_price_setEntity.toDomain(): Total_shipping_price_set {
    return Total_shipping_price_set(
        shop_money = shop_money?.toDomain(),
        presentment_money = presentment_money?.toDomain()
    )
}


fun Subtotal_price_setEntity.toDomain(): Subtotal_price_set {
    return Subtotal_price_set(
        shop_money = shop_money?.toDomain(),
        presentment_money = presentment_money?.toDomain()
    )
}


fun Current_subtotal_price_setEntity.toDomain(): Current_subtotal_price_set {
    return Current_subtotal_price_set(
        shop_money = shop_money?.toDomain(),
        presentment_money = presentment_money?.toDomain()
    )
}

fun Total_tax_setEntity.toDomain(): Total_tax_set {
    return Total_tax_set(
        shop_money = shop_money?.toDomain(),
        presentment_money = presentment_money?.toDomain()
    )
}

fun Current_total_tax_setEntity.toDomain(): Current_total_tax_set {
    return Current_total_tax_set(
        shop_money = shop_money?.toDomain(),
        presentment_money = presentment_money?.toDomain()
    )
}

fun Total_line_items_price_setEntity.toDomain(): Total_line_items_price_set {
    return Total_line_items_price_set(
        shop_money = shop_money?.toDomain(),
        presentment_money = presentment_money?.toDomain()
    )
}


fun Total_price_setEntity.toDomain(): Total_price_set {
    return Total_price_set(
        shop_money = shop_money?.toDomain(),
        presentment_money = presentment_money?.toDomain()
    )
}


fun CustomerEntity.toDomain(): Customer {
    return Customer(
        note = note,
        tax_exempt = tax_exempt,
        email_marketing_consent = email_marketing_consent?.toDomain(),
        created_at = created_at,
        last_name = last_name,
        multipass_identifier = multipass_identifier,
        verified_email = verified_email,
        tags = tags,
        sms_marketing_consent = sms_marketing_consent?.toDomain(),
        default_address = default_address?.toDomain(),
        updated_at = updated_at,
        phone = phone,
        admin_graphql_api_id = admin_graphql_api_id,
        tax_exemptions = tax_exemptions,
        currency = currency,
        id = id,
        state = state,
        first_name = first_name,
        email = email
    )
}

fun Email_marketing_consentEntity.toDomain(): Email_marketing_consent {
    return Email_marketing_consent(
        consent_updated_at = consent_updated_at,
        state = state,
        opt_in_level = opt_in_level
    )
}

fun Sms_marketing_consentEntity.toDomain(): Sms_marketing_consent {
    return Sms_marketing_consent(
        consent_updated_at = consent_updated_at,
        consent_collected_from = consent_collected_from,
        state = state,
        opt_in_level = opt_in_level
    )
}

fun Default_addressEntity.toDomain(): Default_address {
    return Default_address(
        zip = zip,
        country = country,
        address2 = address2,
        city = city,
        address1 = address1,
        last_name = last_name,
        province_code = province_code,
        country_code = country_code,
        default = default,
        province = province,
        phone = phone,
        name = name,
        country_name = country_name,
        company = company,
        id = id,
        customer_id = customer_id,
        first_name = first_name
    )
}

