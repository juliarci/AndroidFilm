package com.example.firstapplication

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.firstapplication.daos.ActorDao
import com.example.firstapplication.daos.FilmDao
import com.example.firstapplication.daos.SerieDao
import com.example.firstapplication.entities.ActorEntity
import com.example.firstapplication.entities.FilmEntity
import com.example.firstapplication.entities.SerieEntity

@Database(entities = [FilmEntity::class, ActorEntity::class, SerieEntity::class], version = 5)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun filmDao(): FilmDao
    abstract fun serieDao(): SerieDao
    abstract fun actorDao(): ActorDao

}