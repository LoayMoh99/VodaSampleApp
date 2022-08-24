package com.example.loayvodasample.network

import com.example.loayvodasample.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    fun getSearchUsers(@Query("q") query: String) : Call<UserResponse>
}