package com.example.loayvodasample.network

import com.example.loayvodasample.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
//import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    //@Headers("Authorization : token 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")
    fun getSearchUsers(@Query("q") query: String) : Call<UserResponse>
}