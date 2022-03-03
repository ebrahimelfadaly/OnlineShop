package com.example.onlineshop.data.remoteDataSource

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object Network {
    private const val BASE_URL ="https://bfe73f4cd7e7f8737d5928b2a439022e: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@jets2022.myshopify.com/admin/api/2022-01/"


    var gson = GsonBuilder()
        .setLenient()
        .create()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(buildAuthClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create()) //important
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val apiService: NetworkService = getRetrofit().create(NetworkService::class.java)

    private fun buildAuthClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("X-Shopify-Access-Token", "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")
                .build()
            chain.proceed(newRequest)
        }

        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        httpClient.writeTimeout(30, TimeUnit.SECONDS)
        return httpClient.build()
    }
}