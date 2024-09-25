package com.example.firstapplication

import Movie
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {
    val movies = MutableStateFlow<List<Movie>>(listOf())
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
}