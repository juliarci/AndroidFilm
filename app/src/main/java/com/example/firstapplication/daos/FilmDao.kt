package com.example.firstapplication.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.firstapplication.entities.FilmEntity

@Dao
interface FilmDao {
    @Query("SELECT * FROM films")
    suspend fun getFavFilms(): List<FilmEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFilm(film: FilmEntity)

    @Query("DELETE FROM films WHERE id = :id")
    suspend fun deleteFilm(id: String): Int

    @Query("SELECT EXISTS(SELECT 1 FROM films WHERE id = :id)")
    suspend fun isFavorite(id: String): Boolean
}