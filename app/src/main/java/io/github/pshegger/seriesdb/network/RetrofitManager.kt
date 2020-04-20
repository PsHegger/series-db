package io.github.pshegger.seriesdb.network

import io.github.pshegger.seriesdb.BuildConfig
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitManager(private val config: RetrofitConfig) {

    companion object {
        private const val CACHE_SIZE = 4L * 1024L * 1024L   // 4 MB cache
    }

    private val httpClientBuilder = OkHttpClient.Builder()
        .cache(Cache(config.context.cacheDir, CACHE_SIZE))

    fun create(): Retrofit {
        val logLevel = if (BuildConfig.DEBUG) Level.BODY else Level.NONE

        config.interceptors.forEach { httpClientBuilder.addInterceptor(it) }
        httpClientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(logLevel))

        return Retrofit.Builder()
            .client(httpClientBuilder.build())
            .baseUrl(config.baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(config.moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}
