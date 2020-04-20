package io.github.pshegger.seriesdb.network

import android.content.Context
import com.squareup.moshi.Moshi
import okhttp3.Interceptor

data class RetrofitConfig(
    val baseUrl: String,
    val context: Context,
    val moshi: Moshi,
    val interceptors: List<Interceptor> = emptyList()
)
