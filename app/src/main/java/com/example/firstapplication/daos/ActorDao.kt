package com.example.firstapplication.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.firstapplication.entities.ActorEntity

@Dao
interface ActorDao {
    @Query("SELECT * FROM ACTORS")
    suspend fun getFavActors(): List<ActorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActor(film: ActorEntity)

    @Query("DELETE FROM ACTORS WHERE id = :id")
    suspend fun deleteActor(id: String)

    @Query("SELECT EXISTS(SELECT 1 FROM ACTORS WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean
}