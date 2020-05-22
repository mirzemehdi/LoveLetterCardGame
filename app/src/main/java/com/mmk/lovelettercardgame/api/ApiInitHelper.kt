package com.mmk.lovelettercardgame.api

import com.mmk.lovelettercardgame.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiInitHelper {

   private var retrofit:Retrofit?=null

    private val client: OkHttpClient by lazy {
        val interceptor= HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    val defaultApi :ApiInitHelper by lazy {
         retrofit= Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        this
    }

    val roomsService:ApiMethods.RoomsService by lazy{
        retrofit!!.create(ApiMethods.RoomsService::class.java)
    }

}