package com.example.firstapplication.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.firstapplication.model.Movie

@Entity(tableName = "films")
data class FilmEntity(@ColumnInfo(name = "fiche") val fiche: Movie,  @ColumnInfo(name = "id") @PrimaryKey val id: String)