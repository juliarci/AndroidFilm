package com.example.firstapplication

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.firstapplication.model.Movie
import com.example.firstapplication.model.Person
import com.example.firstapplication.model.TvShow
import com.squareup.moshi.Moshi

@ProvidedTypeConverter
class Converters(moshi: Moshi) {
    val filmJsonadapter = moshi.adapter(Movie::class.java)
    val serieJsonadapter = moshi.adapter(TvShow::class.java)
    val actorJsonadapter = moshi.adapter(Person::class.java)

    @TypeConverter
    fun StringToTmdbMovie(value: String): Movie? {
        return filmJsonadapter.fromJson(value)
    }

    @TypeConverter
    fun TmdbMovieToString(film: Movie): String {
        return filmJsonadapter.toJson(film)
    }

    @TypeConverter
    fun StringToTmdbSerie(value: String): TvShow? {
        return serieJsonadapter.fromJson(value)
    }

    @TypeConverter
    fun TmdbSerieToString(serie: TvShow): String {
        return serieJsonadapter.toJson(serie)
    }
    @TypeConverter
    fun StringToTmdbActor(value: String): Person? {
        return actorJsonadapter.fromJson(value)
    }

    @TypeConverter
    fun TmdbActorToString(actor: Person): String {
        return actorJsonadapter.toJson(actor)
    }
}