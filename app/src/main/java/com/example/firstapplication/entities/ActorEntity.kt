package com.example.firstapplication.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.firstapplication.model.Person

@Entity(tableName = "actors")
data class ActorEntity(@ColumnInfo(name = "fiche") val fiche: Person,  @ColumnInfo(name = "id") @PrimaryKey val id: String)