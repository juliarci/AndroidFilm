package com.example.firstapplication

import Movie
import Person
import TvShow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {

    //API Request parameters
    val movies = MutableStateFlow<List<Movie>>(listOf())
    val series = MutableStateFlow<List<TvShow>>(listOf())
    val persons = MutableStateFlow<List<Person>>(listOf())

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

    fun trendingFilms(){
        viewModelScope.launch {
            movies.value = service.getTrendingFilms(apiKey).results
        }
    }

    fun searchSeries(keyWord: String) {
        viewModelScope.launch {
            series.value = service.getSeriesByKeyWord(apiKey, keyWord).results
        }
    }

    fun trendingSeries(){
        viewModelScope.launch {
            series.value = service.getTrendingSeries(apiKey).results
        }
    }

    fun searchPersons(keyWord: String){
        viewModelScope.launch {
            persons.value = service.getPersonByKeyWord(apiKey, keyWord).results
        }
    }

    fun trendingPersons(){
        viewModelScope.launch {
            persons.value = service.getTrendingPersons(apiKey).results
        }
    }
}