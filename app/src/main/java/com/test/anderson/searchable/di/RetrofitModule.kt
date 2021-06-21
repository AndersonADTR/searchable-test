package com.test.anderson.searchable.di

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.test.anderson.searchable.network.Services.Companion.BASE_URL
import com.test.anderson.searchable.base.CustomInterceptor
import com.test.anderson.searchable.base.ResponseHandler
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {

    fun provideCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    fun provideHeadersInterceptor() = CustomInterceptor()

    fun provideResponseHandler() = ResponseHandler()

    fun provideHttpClient(cache: Cache, customInterceptor: CustomInterceptor): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(customInterceptor)
            .addInterceptor(logging)
            .cache(cache)

        return okHttpClientBuilder.build()
    }

    fun provideGson(): Gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(client)
            .build()

    single { provideCache(androidApplication()) }
    single { provideHeadersInterceptor() }
    single { provideHttpClient(get(), get()) }
    single { provideGson() }
    single { provideResponseHandler() }
    single { provideRetrofit(get(), get()) }
}