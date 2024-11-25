package com.example.firstapplication

import javax.inject.Inject


class Repository @Inject constructor(private val tmdbApi: TmdbAPI) {
    private val apiKey = "ca096d89c07f759c710e701ad181fd06"

    suspend fun searchMovies(keyWord: String) = tmdbApi.getFilmsByKeyWord(apiKey, keyWord).results
    suspend fun trendingFilms() = tmdbApi.getTrendingFilms(apiKey = apiKey).results
    suspend fun filmDetail(id: String) = tmdbApi.getDetailFilm(apiKey = apiKey, filmId = id)
    suspend fun searchSeries(keyWord: String) = tmdbApi.getSeriesByKeyWord(apiKey, keyWord).results
    suspend fun trendingSeries() = tmdbApi.getTrendingSeries(apiKey).results
    suspend fun serieDetail(id: String) = tmdbApi.getDetailSerie(apiKey = apiKey, serieId = id)
    suspend fun searchPersons(keyWord: String) = tmdbApi.getPersonByKeyWord(apiKey, keyWord).results
    suspend fun trendingPersons() = tmdbApi.getTrendingPersons(apiKey).results

}