package com.example.khizana.data.datasource.remote

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitFactory @Inject constructor(
    private val context: Context
) {
    private val cache by lazy {
        Cache(File(context.cacheDir, "http_cache"), Strings.CACHE_SIZE)
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Strings.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient(Strings.SHOPIFY_ACCESS_TOKEN))
            .build()
    }

    val apiService: ApiService by lazy { retrofit.create(ApiService::class.java) }

    private fun okHttpClient(apiKey: String): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(createHeaderInterceptor(apiKey))
            .addInterceptor(createOfflineCacheInterceptor())
            .addNetworkInterceptor(createCacheInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private fun createHeaderInterceptor(apiKey: String): Interceptor {
        return Interceptor { chain ->
            val request: Request = chain.request()
                .newBuilder()
                .header("Content-Type", "application/json")
                .header("X-Shopify-Access-Token", apiKey)
                .build()
            chain.proceed(request)
        }
    }

    private fun createCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            val response = chain.proceed(chain.request())
            response.newBuilder()
                .header("Cache-Control", "public, max-age=${Strings.MAX_AGE}")
                .build()
        }
    }

    private fun createOfflineCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            if (!NetworkUtil.isNetworkAvailable(context)) {
                request = request.newBuilder()
                    .header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=${Strings.MAX_STALE}"
                    )
                    .build()
            }
            chain.proceed(request)
        }
    }
}

object NetworkUtil {
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}

object Strings {
    const val CACHE_SIZE = 10 * 1024 * 1024L
    const val MAX_AGE = 60
    const val MAX_STALE = 60 * 60 * 24 * 7
    const val BASE_URL = "https://mad45-sv-and4.myshopify.com/admin/api/2025-04/"
    const val SHOPIFY_ACCESS_TOKEN = "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"
}