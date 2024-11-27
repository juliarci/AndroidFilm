package com.example.firstapplication

import com.example.firstapplication.daos.FilmDao
import com.example.firstapplication.daos.SerieDao
import com.example.firstapplication.entities.FilmEntity
import com.example.firstapplication.model.Movie
import com.example.firstapplication.model.Person
import com.example.firstapplication.model.TvShow
import javax.inject.Inject


class Repository @Inject constructor(
    private val tmdbApi: TmdbAPI,
    private val filmDao: FilmDao,
    private val serieDao: SerieDao
) {
    private val apiKey = "ca096d89c07f759c710e701ad181fd06"

    suspend fun searchMovies(keyWord: String): List<Movie> {
        val tmdbResult = tmdbApi.getFilmsByKeyWord(apiKey, keyWord)
        val updatedResults = tmdbResult.results.map { film ->
            val isFavorite = filmDao.isFavorite(film.id.toString())
            film.copy(isFav = isFavorite)
        }
        return updatedResults
    }

    suspend fun trendingFilms(): List<Movie>  {
        val tmdbResult = tmdbApi.getTrendingFilms(apiKey)
        val updatedResults = tmdbResult.results.map { film ->
            val isFavorite = filmDao.isFavorite(film.id.toString())
            film.copy(isFav = isFavorite)
        }
        return updatedResults
    }

    suspend fun favoriteMovies(): List<Movie> {
        val favoriteEntities = filmDao.getFavFilms()
        return favoriteEntities.map { it.fiche.copy(isFav = true)
        }
    }

    suspend fun toggleFavoriteStatus(movie: Movie) {
        val isFavorite = filmDao.isFavorite(movie.id.toString())

        if (isFavorite) {
            filmDao.deleteFilm(movie.id.toString())
        } else {
            val filmEntity = FilmEntity(fiche = movie, id = movie.id.toString())
            filmDao.insertFilm(filmEntity)
        }
    }
    suspend fun filmDetail(id: String) = tmdbApi.getDetailFilm(apiKey = apiKey, filmId = id)
    suspend fun searchSeries(keyWord: String) : List<TvShow> {
        val tvShowsResponse = tmdbApi.getSeriesByKeyWord(apiKey, keyWord)
        val updatedResults = tvShowsResponse.results.map { serie ->
            val isFavorite = serieDao.isFavorite(serie.id)
            serie.copy(isFav = isFavorite)
        }
        return updatedResults
    }
    suspend fun trendingSeries(): List<TvShow> {
        val tvShowsResponse = tmdbApi.getTrendingSeries(apiKey)
        val updatedResults = tvShowsResponse.results.map { serie ->
            val isFavorite = serieDao.isFavorite(serie.id)
            serie.copy(isFav = isFavorite)
        }
        return updatedResults
    }
    suspend fun serieDetail(id: String) = tmdbApi.getDetailSerie(apiKey = apiKey, serieId = id)
    suspend fun searchPersons(keyWord: String) : List<Person> {
        val actorsResponse = tmdbApi.getPersonByKeyWord(apiKey, keyWord)
        val updatedResults = actorsResponse.results.map { actor ->
            val isFavorite = serieDao.isFavorite(actor.id)
            actor.copy(isFav = isFavorite)
        }
        return updatedResults
    }
    suspend fun trendingPersons() : List<Person> {
        val actorsResponse = tmdbApi.getTrendingPersons(apiKey)
        val updatedResults = actorsResponse.results.map { actor ->
            val isFavorite = serieDao.isFavorite(actor.id)
            actor.copy(isFav = isFavorite)
        }
        return updatedResults
    }
}