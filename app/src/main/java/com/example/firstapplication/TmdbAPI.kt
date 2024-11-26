package com.example.firstapplication


import com.example.firstapplication.model.FilmDetail
import com.example.firstapplication.model.PersonResponse
import com.example.firstapplication.model.SerieDetail
import com.example.firstapplication.model.TmdbResult
import com.example.firstapplication.model.TvShowsResponse
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

    @GET("movie/{id}?append_to_response=credits")
    suspend fun getDetailFilm(
        @Path("id") filmId: String,
        @Query("api_key") apiKey: String
    ): FilmDetail

    @GET("tv/{id}?append_to_response=credits")
    suspend fun getDetailSerie(
        @Path("id") serieId: String,
        @Query("api_key") apiKey: String
    ): SerieDetail

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