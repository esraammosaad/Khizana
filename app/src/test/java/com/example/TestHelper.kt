package com.example

import com.example.khizana.data.dto.CurrentSubtotalPriceSetEntity
import com.example.khizana.data.dto.CurrentTotalPriceSetEntity
import com.example.khizana.data.dto.DiscountCodesItemEntity
import com.example.khizana.data.dto.ImageEntity
import com.example.khizana.data.dto.ImagesItemEntity
import com.example.khizana.data.dto.InventoryItemEntity
import com.example.khizana.data.dto.InventoryItemRequest
import com.example.khizana.data.dto.InventoryLevelRequest
import com.example.khizana.data.dto.Location
import com.example.khizana.data.dto.LocationsItemEntity
import com.example.khizana.data.dto.OptionsItemEntity
import com.example.khizana.data.dto.OrdersItemEntity
import com.example.khizana.data.dto.PrerequisiteToEntitlementQuantityRatio
import com.example.khizana.data.dto.PresentmentMoneyEntity
import com.example.khizana.data.dto.PriceRule
import com.example.khizana.data.dto.PriceRuleEntity
import com.example.khizana.data.dto.PriceRuleRequest
import com.example.khizana.data.dto.PriceRulesItemEntity
import com.example.khizana.data.dto.Product
import com.example.khizana.data.dto.ProductRequest
import com.example.khizana.data.dto.ProductsItemEntity
import com.example.khizana.data.dto.ShopMoneyEntity
import com.example.khizana.data.dto.TotalDiscountsSetEntity
import com.example.khizana.data.dto.TotalPriceSetEntity
import com.example.khizana.data.dto.VariantsItemEntity
import com.example.khizana.domain.model.Image
import com.example.khizana.domain.model.ImagesItem
import com.example.khizana.domain.model.OptionsItem
import com.example.khizana.domain.model.PriceRuleDomainRequest
import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.model.ProductsItem
import com.example.khizana.domain.model.VariantsItem

object ProductTestFactory {

    private fun createImage(
        src: String = "https://example.com/image1.jpg",
        alt: String = "Product image",
        variantIds: List<String> = emptyList()
    ): Image = Image(
        src = src,
        alt = alt,
        variant_ids = variantIds
    )

    private fun createImagesItem(
        src: String = "https://example.com/image1.jpg",
        alt: String = "Product image",
        variantIds: List<String> = emptyList()
    ): ImagesItem = ImagesItem(
        src = src,
        alt = alt,
        variant_ids = variantIds
    )

    private fun createVariantItem(
        title: String = "Default Variant",
        price: String = "19.99",
        option1: String = "Size",
        option2: String = "Color",
        option3: String = "Material",
        inventoryQuantity: Int = 100,
        inventoryItemId: String = "inv_123"
    ): VariantsItem = VariantsItem(
        title = title,
        price = price,
        option1 = option1,
        option2 = option2,
        option3 = option3,
        inventory_quantity = inventoryQuantity,
        inventory_item_id = inventoryItemId
    )

    private fun createOptionItem(
        name: String = "Size",
        values: List<String> = listOf("Small", "Medium", "Large")
    ): OptionsItem = OptionsItem(
        name = name,
        values = values
    )

    fun createProductItem(
        id: String = "sample prod id",
        title: String = "Sample Product",
        productType: String = "Clothing",
        vendor: String = "Sample Vendor",
        status: String = "active",
        tags: String = "new,featured",
        images: List<ImagesItem> = listOf(createImagesItem()),
        variants: List<VariantsItem> = listOf(createVariantItem()),
        options: List<OptionsItem> = listOf(createOptionItem())
    ): ProductsItem = ProductsItem(
        id = id,
        title = title,
        product_type = productType,
        vendor = vendor,
        status = status,
        tags = tags,
        image = createImage(),
        body_html = "<p>Product description</p>",
        images = images,
        variants = variants,
        options = options,
        created_at = "2023-01-01T00:00:00Z",
        updated_at = "2023-01-01T00:00:00Z",
        published_at = "2023-01-01T00:00:00Z"
    )

    fun createProductDomain(
        products: List<ProductsItem> = listOf(createProductItem())
    ): ProductDomain = ProductDomain(
        products = products
    )
}

object ProductDtoTestFactory {

    private fun createImageEntity(
        src: String = "https://example.com/image1.jpg",
        alt: String = "Product image",
        variantIds: List<String> = emptyList()
    ): ImageEntity = ImageEntity(
        src = src,
        alt = alt,
        variantIds = variantIds
    )

    private fun createImagesItemEntity(
        src: String = "https://example.com/image1.jpg",
        alt: String = "Product image",
        variantIds: List<String> = emptyList()
    ): ImagesItemEntity = ImagesItemEntity(
        src = src,
        alt = alt,
        variantIds = variantIds
    )

    private fun createVariantItemEntity(
        title: String = "Default Variant",
        price: String = "19.99",
        option1: String = "Size",
        option2: String = "Color",
        option3: String = "Material",
        inventoryQuantity: Int = 100,
        inventoryItemId: String = "inv_123"
    ): VariantsItemEntity = VariantsItemEntity(
        title = title,
        price = price,
        option1 = option1,
        option2 = option2,
        option3 = option3,
        inventoryQuantity = inventoryQuantity,
        inventoryItemId = inventoryItemId
    )

    private fun createOptionItemEntity(
        name: String = "Size",
        values: List<String> = listOf("Small", "Medium", "Large")
    ): OptionsItemEntity = OptionsItemEntity(
        name = name,
        values = values
    )

    fun createProductItemEntity(
        id: String = "prod_123",
        title: String = "Sample Product",
        productType: String = "Clothing",
        vendor: String = "Sample Vendor",
        status: String = "active",
        tags: String = "new,featured",
        images: List<ImagesItemEntity> = listOf(createImagesItemEntity()),
        variants: List<VariantsItemEntity> = listOf(createVariantItemEntity()),
        options: List<OptionsItemEntity> = listOf(createOptionItemEntity())
    ): ProductsItemEntity = ProductsItemEntity(
        id = id,
        title = title,
        productType = productType,
        vendor = vendor,
        status = status,
        tags = tags,
        image = createImageEntity(),
        bodyHtml = "<p>Product description</p>",
        images = images,
        variants = variants,
        options = options,
        createdAt = "2023-01-01T00:00:00Z",
        updatedAt = "2023-01-01T00:00:00Z",
        publishedAt = "2023-01-01T00:00:00Z"
    )

    fun createProductDto(
        products: List<ProductsItemEntity> = listOf(createProductItemEntity())
    ): Product = Product(
        products = products
    )

}

object ProductRequestDtoTestFactory {
    fun createProductRequest(
        product: ProductsItemEntity = ProductDtoTestFactory.createProductItemEntity(
            id = "prod_123",
            title = "Sample Product",
            productType = "Clothing",
            vendor = "Sample Vendor",
            status = "active",
            tags = "new,featured",
        )
    ): ProductRequest = ProductRequest(
        product = product
    )
}

object ProductRequestTestFactory {
    fun createProductRequest(
        product: ProductsItem = ProductTestFactory.createProductItem(
            id = "prod_123",
            title = "Sample Product",
            productType = "Clothing",
            vendor = "Sample Vendor",
            status = "active",
            tags = "new,featured",
        )
    ): ProductRequestDomain = ProductRequestDomain(
        product = product
    )
}

object OrderDtoTestFactory {
    fun createOrderItem(): OrdersItemEntity {
        val defaultMoney = ShopMoneyEntity(
            amount = "0.00",
            currencyCode = "USD"
        )

        val defaultPresentmentMoney = PresentmentMoneyEntity(
            amount = "0.00",
            currencyCode = "USD"
        )

        return OrdersItemEntity(
            totalDiscountsSet = TotalDiscountsSetEntity(
                shopMoney = defaultMoney,
                presentmentMoney = defaultPresentmentMoney
            ),
            subtotalPrice = "100.00",
            currentSubtotalPriceSet = CurrentSubtotalPriceSetEntity(
                shopMoney = defaultMoney.copy(amount = "100.00"),
                presentmentMoney = defaultPresentmentMoney.copy(amount = "100.00")
            ),
            currentSubtotalPrice = "100.00",
            currency = "USD",
            totalPriceSet = TotalPriceSetEntity(
                shopMoney = defaultMoney.copy(amount = "90.00"),
                presentmentMoney = defaultPresentmentMoney.copy(amount = "90.00")
            ),
            totalPrice = "90.00",
            totalDiscounts = "10.00",
            currentTotalPriceSet = CurrentTotalPriceSetEntity(
                shopMoney = defaultMoney.copy(amount = "90.00"),
                presentmentMoney = defaultPresentmentMoney.copy(amount = "90.00")
            )
        )
    }

}

object PriceRuleDtoTestFactory {
    fun createPriceRule(
        priceRules: List<PriceRulesItemEntity> = listOf(createPriceRulesItemEntity())
    ): PriceRule {
        return PriceRule(
            priceRules = priceRules
        )
    }

    fun createPriceRulesItemEntity(
        id: String = "default_id_123",
        value: String = "10.0",
        title: String = "Default Discount"
    ): PriceRulesItemEntity {
        return PriceRulesItemEntity(
            valueType = "percentage",
            oncePerCustomer = false,
            startsAt = "2023-01-01T00:00:00Z",
            endsAt = "2023-12-31T23:59:59Z",
            createdAt = "2023-01-01T00:00:00Z",
            prerequisiteCustomerIds = emptyList(),
            title = title,
            entitledCollectionIds = emptyList(),
            updatedAt = "2023-01-01T00:00:00Z",
            entitledCountryIds = emptyList(),
            entitledVariantIds = emptyList(),
            id = id,
            value = value,
            allocationMethod = "across",
            allocationLimit = 1,
            targetType = "line_item",
            entitledProductIds = emptyList(),
            customerSegmentPrerequisiteIds = emptyList(),
            customerSelection = "all",
            targetSelection = "all",
            prerequisiteToEntitlementQuantityRatio = createPrerequisiteRatio()
        )
    }

    private fun createPrerequisiteRatio(): PrerequisiteToEntitlementQuantityRatio {
        return PrerequisiteToEntitlementQuantityRatio(
            prerequisiteQuantity = 1,
            entitledQuantity = 1
        )
    }
}

object PriceRuleRequestDtoTestFactory {

    fun createTestPriceRuleRequest(createPriceRulesItemEntity: PriceRuleEntity = createTestPriceRuleEntity()): PriceRuleRequest {
        return PriceRuleRequest(
            priceRule = createPriceRulesItemEntity
        )
    }

    fun createTestPriceRuleEntity(
        value: String = "10.0",
        title: String = "Default Discount"
    ): PriceRuleEntity {
        return PriceRuleEntity(
            allocationMethod = "across",
            valueType = "percentage",
            startsAt = "2023-01-01T00:00:00Z",
            targetType = "line_item",
            title = title,
            customerSelection = "all",
            targetSelection = "all",
            endsAt = "2023-12-31T23:59:59Z",
            value = value,
        )
    }
}

object PriceRuleTestFactory {

    fun createPriceRule(
        valueType: String = "percentage",
        startsAt: String = "2023-01-01T00:00:00Z",
        targetType: String = "line_item",
        title: String = "Test Price Rule",
        endsAt: String = "2023-12-31T23:59:59Z",
        value: String = "-10.0",
    ): PriceRuleDomainRequest {
        return PriceRuleDomainRequest(
            valueType = valueType,
            startsAt = startsAt,
            targetType = targetType,
            title = title,
            endsAt = endsAt,
            value = value,
        )
    }

}

object LocationTestFactory {
    private fun createLocationItem(
        country: String = "US",
        active: Boolean = true,
        countryCode: String = "US",
        name: String = "Main Warehouse",
        countryName: String = "United States",
        id: String = "loc_123"
    ): LocationsItemEntity = LocationsItemEntity(
        country = country,
        active = active,
        countryCode = countryCode,
        name = name,
        countryName = countryName,
        id = id
    )

    fun createLocation(
        locations: List<LocationsItemEntity> = listOf(createLocationItem())
    ): Location = Location(locations)
}

object DiscountCodeDtoTestFactory {
    fun createDiscountCodeItemEntity(
        usageCount: Int = 0,
        code: String = "DISCOUNT20",
        updatedAt: String = "2023-01-01T00:00:00Z",
        priceRuleId: String = "price_rule_123",
        createdAt: String = "2023-01-01T00:00:00Z",
        id: String = "discount_123"
    ): DiscountCodesItemEntity = DiscountCodesItemEntity(
        usageCount = usageCount,
        code = code,
        updatedAt = updatedAt,
        priceRuleId = priceRuleId,
        createdAt = createdAt,
        id = id
    )
}

object InventoryItemDtoTestFactory {
    private fun createInventoryItemEntity(
        cost: String = "10.0",
        tracked: Boolean = true,
    ): InventoryItemEntity = InventoryItemEntity(
        cost = cost,
        tracked = tracked,
    )

    fun createInventoryItemRequest(
        inventoryItem: InventoryItemEntity = createInventoryItemEntity()
    ): InventoryItemRequest = InventoryItemRequest(
        inventoryItem = inventoryItem
    )
}

object InventoryLevelDtoTestFactory {
    fun createInventoryLevelRequest(
        inventoryItemId: String = "inventory_item_123",
        availableAdjustment: Int = 0,
        available: Int = 10,
        locationId: String = "location_456"
    ): InventoryLevelRequest = InventoryLevelRequest(
        inventoryItemId = inventoryItemId,
        availableAdjustment = availableAdjustment,
        available = available,
        locationId = locationId
    )


}