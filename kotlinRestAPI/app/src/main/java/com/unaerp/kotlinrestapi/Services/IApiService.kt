package com.unaerp.kotlinrestapi.Services

import com.unaerp.kotlinrestapi.Model.RandomFact
import retrofit2.Call
import retrofit2.http.GET

interface IApiService {
    @GET("api/v2/facts/random")
    suspend fun getRandomFact(): RandomFact
}