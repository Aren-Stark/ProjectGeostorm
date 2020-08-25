package com.adwardtheatre.yan


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import retrofit2.http.GET
import java.util.concurrent.TimeUnit
import com.adwardtheatre.yan.Astros
import com.adwardtheatre.yan.ISSLocationNow

object ApiClient {

    fun getClient(): ApiRequest {
        return Retrofit.Builder()
            .baseUrl(ApiEndPoint.API_BASE)
            .client(provideOkhttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(ApiRequest::class.java)
    }

    private fun provideOkhttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.connectTimeout(20, TimeUnit.SECONDS)
        client.readTimeout(30, TimeUnit.SECONDS)
        client.writeTimeout(30, TimeUnit.SECONDS)
        return client.build()
    }


}

object ApiEndPoint {
    const val API_BASE = "http://api.open-notify.org"
    const val API_ASTROS = "$API_BASE/astros.json"
    const val API_ISS_NOW = "$API_BASE/iss-now.json"

}

interface ApiRequest {
    @GET(ApiEndPoint.API_ASTROS)
    fun getAstros(): Deferred<Astros>

    @GET(ApiEndPoint.API_ISS_NOW)
    fun getISSNow(): Deferred<ISSLocationNow>

}