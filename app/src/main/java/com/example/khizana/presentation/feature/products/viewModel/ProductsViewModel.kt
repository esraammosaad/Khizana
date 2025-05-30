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
import com.cloudinary.android.UploadRequest
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.cloudinary.android.payload.Payload
import com.example.khizana.domain.model.ImagesItem
import com.example.khizana.domain.model.ProductDomain
import com.example.khizana.domain.model.ProductRequestDomain
import com.example.khizana.domain.model.ProductsItem
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
            getProducts()
        }
    }

    fun uploadProduct(
        imageUris: List<Uri>?,
        productName: String,
        productDescription: String,
        productType: String,
        productVendor: String,
        showBottomSheet: MutableState<Boolean>
    ) {
        val imagesList: MutableList<ImagesItem> = mutableListOf()
        imageUris?.forEach { imageUri ->
            uploadImageToCloudinary(imageUri)?.callback(object :
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
                    if (imagesList.size == imageUris.size) {
                        val productItem = ProductsItem(
                            image = com.example.khizana.domain.model.Image(
                                src = imageUrl,
                                alt = productName,
                                variant_ids = listOf()
                            ),
                            body_html = productDescription,
                            images = imagesList,
                            created_at = "",
                            variants = listOf(),
                            title = productName,
                            product_type = productType,
                            updated_at = "",
                            vendor = productVendor,
                            options = listOf(),
                            id = "",
                            published_at = "",
                            status = "active"
                        )
                        createProduct(
                            productRequestDomain = ProductRequestDomain(
                                product = productItem
                            )
                        )
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
    }

    private fun uploadImageToCloudinary(imageUri: Uri): UploadRequest<out Payload<*>>? {
        return MediaManager.get().upload(imageUri)
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