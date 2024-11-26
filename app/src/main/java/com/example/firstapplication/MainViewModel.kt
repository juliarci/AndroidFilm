package com.example.firstapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstapplication.model.FilmDetail
import com.example.firstapplication.model.Movie
import com.example.firstapplication.model.Person
import com.example.firstapplication.model.SerieDetail
import com.example.firstapplication.model.TvShow
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
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val results = repo.searchMovies(keyWord)
                if (results.isEmpty()) {
                    _errorMessage.value = "Aucun film trouvé."
                }
                movies.value = results
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors de la recherche des films : ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            repo.toggleFavoriteStatus(movie)
            trendingFilms()
        }
    }

    fun trendingFilms() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val films = repo.trendingFilms()
                if (films.isNotEmpty()) {
                    movies.value = films
                } else {
                    _errorMessage.value = "Aucun film tendance trouvé."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erreur de récupération des films tendances : ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun filmDetail(id: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                filmDetail.value = repo.filmDetail(id)
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors de la récupération des détails du film : ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun searchSeries(keyWord: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val results = repo.searchSeries(keyWord)
                if (results.isEmpty()) {
                    _errorMessage.value = "Aucune série trouvée."
                }
                series.value = results
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors de la recherche des séries : ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun trendingSeries() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val getSeries = repo.trendingSeries()
                if (getSeries.isEmpty()) {
                    _errorMessage.value = "Aucune série tendance trouvée."
                }
                series.value = getSeries
            } catch (e: Exception) {
                _errorMessage.value = "Erreur de récupération des séries tendances : ${e.message}"
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
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val results = repo.searchPersons(keyWord)
                if (results.isEmpty()) {
                    _errorMessage.value = "Aucune personne trouvée."
                }
                persons.value = results
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors de la recherche des personnes : ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun trendingPersons() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val results = repo.trendingPersons()
                if (results.isEmpty()) {
                    _errorMessage.value = "Aucune personne tendance trouvée."
                }
                persons.value = results
            } catch (e: Exception) {
                _errorMessage.value = "Erreur lors de la récupération des personnes tendances : ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

}