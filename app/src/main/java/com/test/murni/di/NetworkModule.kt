package com.test.murni.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.test.murni.core.App
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideHttpClient() }
    single {
        val baseUrl = "https://api.spaceflightnewsapi.net"
        provideRetrofit(get(), baseUrl)
    }
}

const val connectTimeout: Long = 30
const val readTimeout: Long = 30

fun provideHttpClient(): OkHttpClient {
    /*Chucker for show network logging*/
    val context: Context? = App.getContext()
    val chuckedCollector: ChuckerCollector?
    var chuckedInterceptor: ChuckerInterceptor? = null
    if (context != null) {
        chuckedCollector = ChuckerCollector(context = context, showNotification = true)
        chuckedInterceptor = ChuckerInterceptor.Builder(context)
            .collector(chuckedCollector)
            .build()
    }

    val okHttpClientBuilder = OkHttpClient.Builder()
        .connectTimeout(connectTimeout, TimeUnit.SECONDS)
        .readTimeout(readTimeout, TimeUnit.SECONDS)
    val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    okHttpClientBuilder.addNetworkInterceptor(httpLoggingInterceptor)
//    if (chuckedInterceptor != null) {
//        okHttpClientBuilder.addInterceptor(chuckedInterceptor)
//    }
//    okHttpClientBuilder.addInterceptor(NetworkInterceptor())
//    okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
    okHttpClientBuilder.build()
    return okHttpClientBuilder.build()
}

fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}