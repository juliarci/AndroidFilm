package com.example.firstapplication

import FilmDetail
import Person
import PersonResponse
import TmdbResult
import TvShowsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbAPI {
    @GET("trending/movie/week")
    suspend fun getTrendingFilms(
        @Query("api_key") apiKey: String
    ): TmdbResult

    @GET("search/movie")
    suspend fun getFilmsByKeyWord(
        @Query("api_key") apiKey: String,
        @Query("query") keyWord: String
    ): TmdbResult

    @GET("movie/{id}")
    suspend fun getDetailFilm(
        @Path("id") filmId: String,
        @Query("api_key") apiKey: String
    ): FilmDetail

    @GET("trending/tv/week")
    suspend fun getTrendingSeries(
        @Query("api_key") apiKey: String
    ): TvShowsResponse

    @GET("search/tv")
    suspend fun getSeriesByKeyWord(
        @Query("api_key") apiKey: String,
        @Query("query") keyWord: String
    ): TvShowsResponse

    @GET("trending/person/week")
    suspend fun getTrendingPersons(
        @Query("api_key") apiKey: String
    ): PersonResponse

    @GET("search/person")
    suspend fun getPersonByKeyWord(
        @Query("api_key") apiKey: String,
        @Query("query") keyWord: String
    ): PersonResponse
}