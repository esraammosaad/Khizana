package com.example

import com.example.khizana.data.dto.Count
import com.example.khizana.data.dto.DiscountCode
import com.example.khizana.data.dto.DiscountCodeRequest
import com.example.khizana.data.dto.ImageEntity
import com.example.khizana.data.dto.ImagesItemEntity
import com.example.khizana.data.dto.InventoryItemRequest
import com.example.khizana.data.dto.InventoryLevel
import com.example.khizana.data.dto.InventoryLevelRequest
import com.example.khizana.data.dto.Location
import com.example.khizana.data.dto.OptionsItemEntity
import com.example.khizana.data.dto.Order
import com.example.khizana.data.dto.PriceRule
import com.example.khizana.data.dto.PriceRuleRequest
import com.example.khizana.data.dto.Product
import com.example.khizana.data.dto.ProductRequest
import com.example.khizana.data.dto.ProductsItemEntity
import com.example.khizana.data.dto.VariantsItemEntity
import com.example.khizana.data.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRemoteDataSource(private val productsList: MutableList<ProductsItemEntity>) : RemoteDataSource {
    override suspend fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {

    }

    override suspend fun logout() {

    }

    override suspend fun getProducts(): Flow<Product> {
        return flowOf(ProductDtoTestFactory.createProductDto(productsList))
    }

    override suspend fun createProduct(product: ProductRequest): Flow<ProductRequest> {
        productsList.add(ProductDtoTestFactory.createProductItemEntity())
        return flowOf(product)
    }

    override suspend fun getOrders(minDate: String, maxDate: String): Flow<Order> {
        TODO("Not yet implemented")
    }

    override suspend fun getOrdersCount(minDate: String, maxDate: String): Flow<Count> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProduct(productId: String) {
        productsList.removeIf { it.id == productId }
    }

    override suspend fun getProductsCount(): Flow<Count> {
        return flowOf(Count(count = productsList.size))
    }

    override suspend fun getProductById(productId: String): Flow<ProductRequest> {
        return flowOf(ProductRequest(product = productsList.find { it.id == productId }))
    }

    override suspend fun editProduct(productId: String, product: ProductRequest) {
        val index = productsList.indexOfFirst { it.id == productId }
        if (index != -1) {
            productsList[index] = product.product!!
        }
    }

    override suspend fun getAllInventoryLocations(): Flow<Location> {
        TODO("Not yet implemented")
    }

    override suspend fun getInventoryLevels(locationId: String): Flow<InventoryLevel> {
        TODO("Not yet implemented")
    }

    override suspend fun adjustInventoryItemQuantity(inventoryLevelRequest: InventoryLevelRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun setInventoryItemQuantity(inventoryLevelRequest: InventoryLevelRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun updateInventoryItem(
        inventoryItemRequest: InventoryItemRequest,
        inventoryItemId: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getInventoryItem(inventoryItemId: String): Flow<InventoryItemRequest> {
        TODO("Not yet implemented")
    }

    override suspend fun getPriceRules(): Flow<PriceRule> {
        TODO("Not yet implemented")
    }

    override suspend fun createPriceRules(priceRuleRequest: PriceRuleRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun updatePriceRules(priceRuleId: String, priceRuleRequest: PriceRuleRequest) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePriceRules(priceRuleId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getDiscountCodes(priceRuleId: String): Flow<DiscountCode> {
        TODO("Not yet implemented")
    }

    override suspend fun createDiscountCodes(
        priceRuleId: String,
        discountCodeRequest: DiscountCodeRequest
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteDiscountCodes(priceRuleId: String, discountCodeId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateDiscountCodes(
        priceRuleId: String,
        discountCodeId: String,
        discountCodeRequest: DiscountCodeRequest
    ) {
        TODO("Not yet implemented")
    }
}

object ProductDtoTestFactory {

    private fun createImageEntity(
        src: String = "https://example.com/image1.jpg",
        alt: String = "Product image",
        variantIds: List<String> = emptyList()
    ): ImageEntity = ImageEntity(
        src = src,
        alt = alt,
        variant_ids = variantIds
    )

    private fun createImagesItemEntity(
        src: String = "https://example.com/image1.jpg",
        alt: String = "Product image",
        variantIds: List<String> = emptyList()
    ): ImagesItemEntity = ImagesItemEntity(
        src = src,
        alt = alt,
        variant_ids = variantIds
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
        inventory_quantity = inventoryQuantity,
        inventory_item_id = inventoryItemId
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
        product_type = productType,
        vendor = vendor,
        status = status,
        tags = tags,
        image = createImageEntity(),
        body_html = "<p>Product description</p>",
        images = images,
        variants = variants,
        options = options,
        created_at = "2023-01-01T00:00:00Z",
        updated_at = "2023-01-01T00:00:00Z",
        published_at = "2023-01-01T00:00:00Z"
    )

    fun createProductDto(
        products: List<ProductsItemEntity> = listOf(createProductItemEntity())
    ): Product = Product(
        products = products
    )

    fun createProductWithMultipleVariants(): ProductsItemEntity {
        val variants = listOf(
            createVariantItemEntity(title = "Small", price = "19.99"),
            createVariantItemEntity(title = "Medium", price = "24.99"),
            createVariantItemEntity(title = "Large", price = "29.99")
        )
        return createProductItemEntity(
            title = "T-Shirt",
            variants = variants,
            options = listOf(
                createOptionItemEntity(name = "Size", values = listOf("S", "M", "L"))
            )
        )
    }

    fun createProductWithMultipleImages(): ProductsItemEntity {
        val images = listOf(
            createImagesItemEntity(src = "https://example.com/image1.jpg"),
            createImagesItemEntity(src = "https://example.com/image2.jpg"),
            createImagesItemEntity(src = "https://example.com/image3.jpg")
        )
        return createProductItemEntity(
            title = "Photo Album",
            images = images
        )
    }

    fun createOutOfStockProduct(): ProductsItemEntity {
        return createProductItemEntity(
            title = "Sold Out Item",
            variants = listOf(
                createVariantItemEntity(inventoryQuantity = 0)
            )
        )
    }

    fun createProductWithNoVariants(): ProductsItemEntity {
        return createProductItemEntity(
            title = "Simple Product",
            variants = emptyList()
        )
    }

    fun createEmptyProductDto(): Product = Product(products = emptyList())

    fun createNullProductDto(): Product = Product(products = null)
}

object ProductRequestDtoTestFactory{
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
