package com.example.khizana.data.remote

import com.example.khizana.utilis.Strings
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Strings.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient(Strings.SHOPIFY_ACCESS_TOKEN).build())
        .build()
    val apiService : ApiService = retrofit.create(ApiService::class.java)

    private fun okHttpClient(apiKey: String) = OkHttpClient().newBuilder()
        .addInterceptor(
            Interceptor { chain ->
                val request: Request = chain.request()
                    .newBuilder()
                    .header("Content-Type", "application/json")
                    .header("X-Shopify-Access-Token", apiKey)
                    .build()
                chain.proceed(request)
            }
        )
}