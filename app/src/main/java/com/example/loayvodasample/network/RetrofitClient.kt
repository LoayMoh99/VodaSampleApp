package com.example.loayvodasample.network

import com.example.loayvodasample.utilities.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
     private val retrofit = Retrofit.Builder()
         .baseUrl(BASE_URL)
         .addConverterFactory(GsonConverterFactory.create())
         .build()

    val apiInstance = retrofit.create(Api::class.java)

}