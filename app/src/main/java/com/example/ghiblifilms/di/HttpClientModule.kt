package com.example.ghiblifilms.di

import com.example.ghiblifilms.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val BASE_URL: String ="https://ghibliapi.dev"
private const val YOUTUBE_URL: String = "https://youtube.googleapis.com/youtube/v3/"
private const val TIMEOUT_CONNECT_MS: Long = 8000
private const val TIMEOUT_READ_MS: Long = 8000

@OptIn(ExperimentalSerializationApi::class)
val HttpClientModule = module() {
    single {
        val client = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_CONNECT_MS, TimeUnit.MILLISECONDS)
            .readTimeout(TIMEOUT_READ_MS, TimeUnit.MILLISECONDS)

        if (BuildConfig.DEBUG) {
            // OKHHTP LOGGER
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(interceptor)
        }

        client.build()
    }

    fun provideKotlinSerialization(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        return json.asConverterFactory(contentType)
    }

    single(named("youtube")) {
        val converterFactory = provideKotlinSerialization()

        Retrofit.Builder()
            .client(get())
            .baseUrl(YOUTUBE_URL)
            .addConverterFactory(converterFactory)
            .build()
    }

    single {
        val converterFactory = provideKotlinSerialization()

        Retrofit.Builder()
            .client(get())
            .baseUrl(BASE_URL)
            .addConverterFactory(converterFactory)
            .build()
    }
}

