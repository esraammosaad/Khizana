package com.example.khizana.presentation.feature.products.viewModel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.khizana.domain.model.ImagesItem
import com.example.khizana.domain.model.OptionsItem
import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.model.ProductsItem
import com.example.khizana.domain.model.VariantsItem
import com.example.khizana.domain.usecase.CreateProductUseCase
import com.example.khizana.domain.usecase.DeleteProductUseCase
import com.example.khizana.domain.usecase.EditProductUseCase
import com.example.khizana.domain.usecase.GetProductByIdUseCase
import com.example.khizana.domain.usecase.GetProductsUseCase
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val getProductsUseCase: GetProductsUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val editProductUseCase: EditProductUseCase
) : ViewModel() {

    private var _products: MutableLiveData<ProductDomain> = MutableLiveData()
    val products: LiveData<ProductDomain> = _products

    private var _product: MutableLiveData<ProductRequestDomain> = MutableLiveData()
    val product: LiveData<ProductRequestDomain> = _product


    fun getProducts() {
        viewModelScope.launch {
            val response = getProductsUseCase.getProducts()
            _products.postValue(response)
            Log.i("TAG", "getProducts: $response")

        }
    }

    fun createProduct(productRequestDomain: ProductRequestDomain) {
        viewModelScope.launch {
            val response = createProductUseCase.createProduct(productRequestDomain)
            if (response.product?.id != null) {
                getProducts()
            }
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            deleteProductUseCase.deleteProduct(productId)
            getProducts()
        }
    }

    fun getProductById(productId: String) {
        viewModelScope.launch {
            val response = getProductByIdUseCase.getProductById(productId)
            _product.postValue(response)
            Log.i("TAG", "getProductById: $response")
        }
    }

    fun editProduct(productId: String, product: ProductRequestDomain) {
        viewModelScope.launch {
            editProductUseCase.editProduct(productId, product)
            getProductById(productId)
        }
    }

    fun uploadProduct(
        imageUris: List<Any>?,
        productName: String,
        productDescription: String,
        productType: String,
        productVendor: String,
        variantList: List<VariantsItem>,
        optionList: List<OptionsItem>,
        showBottomSheet: MutableState<Boolean>,
        isEditable: Boolean,
        productId: String? = null
    ) {
        val urlList: MutableList<String> = mutableListOf()
        val uriList: MutableList<String> = mutableListOf()
        val imagesList: MutableList<ImagesItem> = mutableListOf()
        imageUris?.forEach { imageUri ->
            val uploadRequest = when (imageUri) {
                is Uri -> {
                    uriList.add(imageUri.toString())
                    MediaManager.get().upload(imageUri)
                }

                is String -> {
                    urlList.add(imageUri)
                    Log.i("TAG", "onSuccess: $imageUri")
                    null
                }

                else -> {
                    null
                }
            }
            uploadRequest?.callback(object :
                UploadCallback {
                override fun onStart(requestId: String?) {
                }

                override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                }

                override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                    val imageUrl = resultData?.get("url") as String
                    imagesList.add(
                        ImagesItem(
                            src = imageUrl,
                            alt = productName,
                            variant_ids = listOf()
                        )
                    )

                    Log.i("TAG", "onSuccess: ${imageUris.size}")
                    Log.i("TAG", "onSuccess: ${imagesList.size}")
                    Log.i("TAG", "onSuccess: ${urlList.size}")

                    if ((imagesList.size + urlList.size) == imageUris.size) {
                        urlList.forEach { url ->
                            imagesList.add(
                                ImagesItem(
                                    src = url,
                                    alt = productName,
                                    variant_ids = listOf()
                                )
                            )
                        }
                        val productItem = ProductsItem(
                            image = com.example.khizana.domain.model.Image(
                                src = imageUrl,
                                alt = productName,
                                variant_ids = listOf()
                            ),
                            body_html = productDescription,
                            images = imagesList,
                            created_at = "",
                            variants = variantList,
                            title = productName,
                            product_type = productType,
                            updated_at = "",
                            vendor = productVendor,
                            options = optionList,
                            id = "",
                            published_at = "",
                            status = "active"
                        )
                        if (isEditable) {
                            editProduct(
                                productId ?: "",
                                ProductRequestDomain(
                                    product = productItem
                                )
                            )
                        } else {
                            createProduct(
                                productRequestDomain = ProductRequestDomain(
                                    product = productItem
                                )
                            )
                        }
                        showBottomSheet.value = false
                    }
                }

                override fun onError(requestId: String?, error: ErrorInfo?) {
                }

                override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                }
            }
            )?.dispatch()
        }

        if (urlList.isNotEmpty() && uriList.isEmpty() && isEditable) {
            urlList.forEach { url ->
                imagesList.add(
                    ImagesItem(
                        src = url,
                        alt = productName,
                        variant_ids = listOf()
                    )
                )
            }
            val productItem = ProductsItem(
                image = com.example.khizana.domain.model.Image(
                    src = urlList.first(),
                    alt = productName,
                    variant_ids = listOf()
                ),
                body_html = productDescription,
                images = imagesList,
                created_at = "",
                variants = variantList,
                title = productName,
                product_type = productType,
                updated_at = "",
                vendor = productVendor,
                options = optionList,
                id = "",
                published_at = "",
                status = "active"
            )
            editProduct(
                productId ?: "",
                ProductRequestDomain(
                    product = productItem
                )
            )
            showBottomSheet.value = false
        }
    }
}

class ProductsViewModelFactory(
    private val getProductsUseCase: GetProductsUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val editProductUseCase: EditProductUseCase
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductsViewModel(
            getProductsUseCase,
            createProductUseCase,
            deleteProductUseCase,
            getProductByIdUseCase,
            editProductUseCase
        ) as T
    }
}