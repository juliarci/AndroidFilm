package com.example.firstapplication.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.firstapplication.entities.SerieEntity

@Dao
interface SerieDao {
    @Query("SELECT * FROM series")
    suspend fun getFavSeries(): List<SerieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSerie(film: SerieEntity)

    @Query("DELETE FROM series WHERE id = :id")
    suspend fun deleteSerie(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM series WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean
}