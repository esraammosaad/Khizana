package com.example.khizana.presentation.feature.products.viewModel

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
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
import com.example.khizana.utilis.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val createProductUseCase: CreateProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val editProductUseCase: EditProductUseCase
) : ViewModel() {

    private var _products = MutableStateFlow<Response>(Response.Loading)
    val products = _products.asStateFlow()

    private var _product = MutableStateFlow<Response>(Response.Loading)
    val product = _product.asStateFlow()

    private var _message = MutableSharedFlow<String>()
    val message = _message.asSharedFlow()

    private val _searchResults = MutableSharedFlow<List<ProductsItem>>(replay = 1)
    val searchResults = _searchResults.asSharedFlow()

    fun getProducts() {
        viewModelScope.launch {
            try {
                val response = getProductsUseCase.getProducts()
                response.catch {
                    _products.emit(Response.Failure(it.message.toString()))
                    _message.emit(it.message.toString())
                }
                    .collect {
                        _products.emit(Response.Success(it))
                    }
            } catch (e: Exception) {
                _products.emit(Response.Failure(e.message.toString()))
                _message.emit(e.message.toString())
            }
        }
    }

    fun createProduct(productRequestDomain: ProductRequestDomain) {
        viewModelScope.launch {
            try {
                val response = createProductUseCase.createProduct(productRequestDomain)
                response.catch {
                    _message.emit(it.message.toString())
                }.collect {
                    if (it.product?.id != null) {
                        getProducts()
                    }
                    _message.emit("Product created successfully")
                }
            } catch (e: Exception) {
                _message.emit(e.message.toString())
            }
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            try {
                deleteProductUseCase.deleteProduct(productId)
                getProducts()
                _message.emit("Product deleted successfully")
            } catch (e: Exception) {
                _message.emit(e.message.toString())
            }
        }
    }

    fun getProductById(productId: String) {
        viewModelScope.launch {
            try {
                val response = getProductByIdUseCase.getProductById(productId)
                response.catch {
                    _product.emit(Response.Failure(it.message.toString()))
                    _message.emit(it.message.toString())
                }.collect {
                    _product.emit(Response.Success(it))
                }
            } catch (e: Exception) {
                _product.emit(Response.Failure(e.message.toString()))
                _message.emit(e.message.toString())
            }
        }
    }

    fun editProduct(productId: String, product: ProductRequestDomain) {
        viewModelScope.launch {
            try {
                editProductUseCase.editProduct(productId, product)
                getProductById(productId)
                _message.emit("Product edited successfully")
            } catch (e: Exception) {
                _message.emit(e.message.toString())
            }
        }
    }

    fun performSearch(query: String) {
        viewModelScope.launch {
            val productResponse = _products.value
            if (productResponse !is Response.Success<*>) {
                _searchResults.emit(emptyList())
                return@launch
            }
            val items = (productResponse.result as? ProductDomain)?.products ?: run {
                _searchResults.emit(emptyList())
                return@launch
            }
            if (query.isBlank()) {
                _searchResults.emit(emptyList())
                return@launch
            }
            val normalizedQuery = query.lowercase().trim()
            val results = items
                .asSequence()
                .filterNotNull()
                .map { product ->
                    val title = product.title?.lowercase()?.trim() ?: ""
                    val distance = if (title.isNotEmpty()) {
                        val levenshteinScore = levenshteinDistance(title, normalizedQuery)
                        val containsScore = if (title.contains(normalizedQuery)) 0 else 100
                        val startsWithScore = if (title.startsWith(normalizedQuery)) 0 else 10
                        (levenshteinScore * 0.6 + containsScore * 0.3 + startsWithScore * 0.1).toInt()
                    } else {
                        Int.MAX_VALUE
                    }
                    product to distance
                }
                .sortedBy { (_, distance) -> distance }
                .take(5)
                .map { (product, _) -> product }
                .toList()
            _searchResults.emit(results)
        }
    }

    private fun levenshteinDistance(a: String, b: String): Int {
        if (a.isEmpty()) return b.length
        if (b.isEmpty()) return a.length
        val costs = IntArray(b.length + 1) { it }
        for (i in 1..a.length) {
            var previousValue = i - 1
            costs[0] = i
            for (j in 1..b.length) {
                val currentCost = when {
                    a[i - 1] == b[j - 1] -> previousValue
                    else -> 1 + minOf(costs[j - 1], costs[j], previousValue)
                }
                previousValue = costs[j]
                costs[j] = currentCost
            }
        }
        return costs[b.length]
    }


    fun uploadProduct(
        imageUris: List<Any>?,
        productName: String,
        productDescription: String,
        productType: String,
        productVendor: String,
        productStatus: String,
        tag: String,
        variantList: List<VariantsItem>,
        optionList: List<OptionsItem>,
        showBottomSheet: MutableState<Boolean>,
        isEditable: Boolean,
        productId: String? = null,
        isUploading: MutableState<Boolean>
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
                            status = productStatus,
                            tags = tag
                        )
                        if (isEditable) {
                            editProduct(
                                productId ?: "",
                                ProductRequestDomain(
                                    product = productItem
                                )
                            )
                            viewModelScope.launch {
                                delay(1500)
                                showBottomSheet.value = false
                                isUploading.value = false
                            }
                        } else {
                            createProduct(
                                productRequestDomain = ProductRequestDomain(
                                    product = productItem
                                )
                            )
                            showBottomSheet.value = false
                            isUploading.value = false
                        }
                    }
                }
                override fun onError(requestId: String?, error: ErrorInfo?) {
                    viewModelScope.launch {
                        _message.emit(error?.description.toString())
                    }
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
                status = productStatus,
                tags = tag
            )
            editProduct(
                productId ?: "",
                ProductRequestDomain(
                    product = productItem
                )
            )
            viewModelScope.launch {
                delay(1500)
                showBottomSheet.value = false
                isUploading.value = false
            }
        }
    }
}
