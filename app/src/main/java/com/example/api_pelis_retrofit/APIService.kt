package com.example.api_pelis_retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("popular?")
    fun getPupularMovies(@Query("api_key") api_key:String): Call<MoviesResponse>
}