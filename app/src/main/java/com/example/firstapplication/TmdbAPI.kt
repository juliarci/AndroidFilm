package com.example.firstapplication

import TmdbResult
import TvShowsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbAPI {
    @GET("search/movie")
    suspend fun getFilmsByKeyWord(
        @Query("api_key") apiKey: String,
        @Query("query") keyWord: String
    ): TmdbResult

    @GET("search/tv")
    suspend fun getSeriesByKeyWord(
        @Query("api_key") apiKey: String,
        @Query("query") keyWord: String
    ): TvShowsResponse
}