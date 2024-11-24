package com.example.firstapplication

import FilmDetail
import Movie
import Person
import SerieDetail
import TvShow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {

    //API Request parameters
    val movies = MutableStateFlow<List<Movie>>(listOf())
    val series = MutableStateFlow<List<TvShow>>(listOf())
    val persons = MutableStateFlow<List<Person>>(listOf())
    val filmDetail = MutableStateFlow<FilmDetail?>(null)
    val serieDetail = MutableStateFlow<SerieDetail?>(null)
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val apiKey = "ca096d89c07f759c710e701ad181fd06"
    private val service = Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(TmdbAPI::class.java)


    fun searchMovies(keyWord: String) {
        viewModelScope.launch {
            movies.value = service.getFilmsByKeyWord(apiKey, keyWord).results
        }
    }

    fun trendingFilms() {
        viewModelScope.launch {
            _errorMessage.value = null
            _isLoading.value = true
            try {
                val films = service.getTrendingFilms(apiKey).results
                if (films.isNotEmpty()) {
                    movies.value = films
                } else {
                    _errorMessage.value = "Aucun film trouvé."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erreur de récupération des films : ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
        viewModelScope.launch {
            movies.value = service.getTrendingFilms(apiKey).results
        }
    }

    fun filmDetail(id: String) {
        viewModelScope.launch {
            filmDetail.value = service.getDetailFilm(apiKey = apiKey, filmId = id)
        }
    }

    fun searchSeries(keyWord: String) {
        viewModelScope.launch {
            series.value = service.getSeriesByKeyWord(apiKey, keyWord).results
        }
    }

    fun trendingSeries() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val getSeries = service.getTrendingSeries(apiKey).results

                if (getSeries.isEmpty()) {
                    _errorMessage.value = "Aucune série trouvée."
                } else {
                    series.value = getSeries
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erreur de récupération des séries : ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun serieDetail(id: String) {
        viewModelScope.launch {
            serieDetail.value = service.getDetailSerie(apiKey = apiKey, serieId = id)
        }
    }

    fun searchPersons(keyWord: String) {
        viewModelScope.launch {
            persons.value = service.getPersonByKeyWord(apiKey, keyWord).results
        }
    }

    fun trendingPersons() {
        viewModelScope.launch {
            persons.value = service.getTrendingPersons(apiKey).results
        }
    }
}