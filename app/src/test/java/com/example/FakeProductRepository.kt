package com.example

import com.example.khizana.domain.model.CountDomain
import com.example.khizana.domain.model.Image
import com.example.khizana.domain.model.ImagesItem
import com.example.khizana.domain.model.OptionsItem
import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.model.ProductsItem
import com.example.khizana.domain.model.VariantsItem
import com.example.khizana.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeProductRepository(private val productsList: MutableList<ProductsItem>) :
    ProductRepository {
    override suspend fun getProducts(): Flow<ProductDomain> {
        return flowOf(ProductTestFactory.createProductDomain(productsList))
    }

    override suspend fun createProduct(product: ProductRequestDomain): Flow<ProductRequestDomain> {
        productsList.add(ProductTestFactory.createProductItem())
        return flowOf(product)
    }

    override suspend fun deleteProduct(productId: String) {
        productsList.removeIf { it.id == productId }
    }

    override suspend fun getProductById(productId: String): Flow<ProductRequestDomain> {
        return flowOf(ProductRequestDomain(product = productsList.find { it.id == productId }))
    }

    override suspend fun editProduct(productId: String, product: ProductRequestDomain) {
        val index = productsList.indexOfFirst { it.id == productId }
        if (index != -1) {
            productsList[index] = product.product!!
        }
    }

    override suspend fun getProductsCount(): Flow<CountDomain> {
        return flowOf(CountDomain(count = productsList.size))
    }
}

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
        title: String = "Sample Product",
        productType: String = "Clothing",
        vendor: String = "Sample Vendor",
        status: String = "active",
        tags: String = "new,featured",
        images: List<ImagesItem> = listOf(createImagesItem()),
        variants: List<VariantsItem> = listOf(createVariantItem()),
        options: List<OptionsItem> = listOf(createOptionItem())
    ): ProductsItem = ProductsItem(
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
        id = "prod_123",
        published_at = "2023-01-01T00:00:00Z"
    )

    fun createProductDomain(
        products: List<ProductsItem> = listOf(createProductItem())
    ): ProductDomain = ProductDomain(
        products = products
    )

    fun createProductWithMultipleVariants(): ProductsItem {
        val variants = listOf(
            createVariantItem(title = "Small", price = "19.99"),
            createVariantItem(title = "Medium", price = "24.99"),
            createVariantItem(title = "Large", price = "29.99")
        )
        return createProductItem(
            title = "T-Shirt",
            variants = variants,
            options = listOf(
                createOptionItem(name = "Size", values = listOf("S", "M", "L"))
            )
        )
    }

    fun createProductWithMultipleImages(): ProductsItem {
        val images = listOf(
            createImagesItem(src = "https://example.com/image1.jpg"),
            createImagesItem(src = "https://example.com/image2.jpg"),
            createImagesItem(src = "https://example.com/image3.jpg")
        )
        return createProductItem(
            title = "Photo Album",
            images = images
        )
    }

    fun createOutOfStockProduct(): ProductsItem {
        return createProductItem(
            title = "Sold Out Item",
            variants = listOf(
                createVariantItem(inventoryQuantity = 0)
            )
        )
    }

    fun createEmptyProductDomain(): ProductDomain = ProductDomain(products = emptyList())

    fun createNullProductDomain(): ProductDomain = ProductDomain(products = null)
}