package com.example.firstapplication

import FilmDetail
import Movie
import Person
import SerieDetail
import TvShow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: Repository) : ViewModel() {

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

    fun searchMovies(keyWord: String) {
        viewModelScope.launch {
            movies.value = repo.searchMovies(keyWord)
        }
    }

    fun trendingFilms() {
        viewModelScope.launch {
            _errorMessage.value = null
            _isLoading.value = true
            try {
                val films = repo.trendingFilms()
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
            movies.value = repo.trendingFilms()
        }
    }

    fun filmDetail(id: String) {
        viewModelScope.launch {
            filmDetail.value = repo.filmDetail(id)
        }
    }

    fun searchSeries(keyWord: String) {
        viewModelScope.launch {
            series.value = repo.searchSeries(keyWord)
        }
    }

    fun trendingSeries() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val getSeries = repo.trendingSeries()

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
            serieDetail.value = repo.serieDetail(id)
        }
    }

    fun searchPersons(keyWord: String) {
        viewModelScope.launch {
            persons.value = repo.searchPersons(keyWord)
        }
    }

    fun trendingPersons() {
        viewModelScope.launch {
            persons.value = repo.trendingPersons()
        }
    }
}