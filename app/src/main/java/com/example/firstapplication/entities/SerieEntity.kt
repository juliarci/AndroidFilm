package com.example.firstapplication.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.firstapplication.model.TvShow

@Entity(tableName = "series")
data class SerieEntity(@ColumnInfo(name = "fiche") val fiche: TvShow,  @ColumnInfo(name = "id") @PrimaryKey val id: String)